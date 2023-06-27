package com.spring.miniposbackend.service.admin;

import com.spring.miniposbackend.controller.security.AuthenticationController;
import com.spring.miniposbackend.exception.BadRequestException;
import com.spring.miniposbackend.exception.ConflictException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.model.admin.User;
import com.spring.miniposbackend.model.admin.UserRole;
import com.spring.miniposbackend.model.security.UserToken;
import com.spring.miniposbackend.modelview.UserResponse;
import com.spring.miniposbackend.repository.admin.BranchRepository;
import com.spring.miniposbackend.repository.admin.UserRepository;
import com.spring.miniposbackend.repository.admin.UserRoleRepository;
import com.spring.miniposbackend.repository.security.UserTokenRespository;
import com.spring.miniposbackend.util.ImageUtil;
import com.spring.miniposbackend.util.JwtTokenUtil;
import com.spring.miniposbackend.util.UserProfileUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;
	@Autowired
	private ImageUtil imageUtil;
	@Autowired
	private UserTokenRespository userTokenRespository;
	@Autowired
	private UserProfileUtil userProfile;
	@Autowired
	@Value("${file.path.image.branch-channel}")
	private String imagePathQR;

	@Value("${file.path.image.profile}")
	private String imagePathProfile;

	@Value("${file.path.image.branch}")
	private String imagePath;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	private final long expiryInterval = 5L * 60 * 1000;

	public User showByUsername(String username) {
		Optional<User> user = userRepository.findFirstByUsername(username);
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new ResourceNotFoundException("User not found");
		}
	}

	public User resetPassword(String username, String currentPassword, String newPassword) {
		return userRepository.findFirstByUsername(username).map(user -> {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			if (encoder.matches(currentPassword, user.getPassword())) {
				user.setPassword(newPassword);
				user.setApiToken(null);
				user.setPassword(encoder.encode(newPassword));
				return userRepository.save(user);
			} else {
				throw new ConflictException("Passowrd mismatched");
			}

		}).orElseThrow(() -> new ResourceNotFoundException("User not found"));
	}

	public User setApiToken(String username, String token) {
		return userRepository.findFirstByUsername(username).map(user -> {
			user.setApiToken(token);
			return userRepository.save(user);
		}).orElse(null);
	}

	public User showByApiToken(String token) {
		return userRepository.findFirstByApiToken(token).orElse(null);
	}

	public List<UserRole> getRoleByUserId(Integer userId) {
		return userRoleRepository.findByUserRoleIdentityUserId(userId, true);
	}

	public Integer generateCode() {
		Random random = new Random();
		String oneTimePassword = new String();
		int randomNumber = random.nextInt(900000) + 100000;
		oneTimePassword = String.format("%06d", randomNumber);
		return Integer.parseInt(oneTimePassword);
	}

	@Transactional
	public String resetPasswordCustomer(String newPassword) throws Exception {

		User user = userProfile.getProfile().getUser();
		limitSendOtp(user);
		Integer generateCodes = generateCode();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPasswordReset(encoder.encode(newPassword));
		user.setOneTimePasswordCode(generateCodes);
		user.setExpiry(new Date(System.currentTimeMillis() + expiryInterval));
		userRepository.save(user);
		sms(user.getUsername(), user.getOneTimePasswordCode());
		return "";
	}

	public void sms(String primaryphone, int generateCodes) {
		try {
			User user = userRepository.findByprimaryphone(primaryphone);
			String to = "+855" + user.getUsername().replaceFirst("0", "");
			String from = "HORPAO";
			String message = "Otp code: " + generateCodes;
			HttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("https://sms.khmerload.com/horpao/send");
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
			httpPost.setHeader(HttpHeaders.AUTHORIZATION,
					"Bearer " + "a2fa6d7817d79a50620195cd5aebc74e5d1c5bbc534c9d7cf16938bfd53a95a5");
			StringEntity params;
			params = new StringEntity("{\"to\":\"" + to + "\",\"from\":\"" + from
					+ "\",\"message\":\"" + message + "\"}");
			httpPost.setEntity(params);
			httpclient.execute(httpPost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void limitSendOtp(User user) throws Exception {
		try {
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String yearmonthday = dateFormat.format(date);
			int i = Integer.parseInt(yearmonthday);
			if (i > user.getResetDate()) {
				user.setTimeCount(1);
				user.setResetDate(Integer.parseInt(yearmonthday));
			} else {
				if (user.getTimeCount().intValue() <= 2) {
					user.setTimeCount(user.getTimeCount() + 1);
				} else {
					throw new BadRequestException("Can't send anymore wait until tommorrow", "05");
				}
			}
		} catch (BadRequestException e) {
			throw new BadRequestException(e.getMessage(), e.getErrorCode());
		}
	}

	public Object verifyOtp(int code) throws Exception {
		try {
			User user = userRepository.findByOtpCode(code);
			if (user == null) {
				throw new BadRequestException("Enter your code", "03");
			}
			if (user.getOneTimePasswordCode().equals(code)) {
				if (user.getExpiry().getTime() >= System.currentTimeMillis()) {
					user.setPassword(user.getPasswordReset());
					userRepository.save(user);
					return "{}";
				} else {
					throw new BadRequestException("Your code has expired", "02");
				}
			} else {
				throw new BadRequestException("wrong code", "01");
			}

		} catch (BadRequestException e) {
			throw new BadRequestException(e.getMessage(), e.getErrorCode());
		}
	}
	public String resetPasswordCustomerFirstLogin(String newPassword) throws Exception {
		User user = userProfile.getProfile().getUser();
			limitSendOtp(user);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Integer generateCodes = null;
		if (user!=null) {
			user.setPasswordReset(encoder.encode(newPassword));
			generateCodes = generateCode();
			user.setOneTimePasswordCode(generateCodes);
			user.setExpiry(new Date(System.currentTimeMillis() + expiryInterval));
			userRepository.save(user);	
			sms(user.getUsername(),user.getOneTimePasswordCode());
		}
		return "";
	}
	
	public Object otpFirstLogin(int code) throws Exception {

		User user = userRepository.findByOtpCode(code);
		if (user == null) {
			throw new BadRequestException("wrong code","01");
		}
		try {
			if (user.getOneTimePasswordCode().equals(code)) {
				if (user.getExpiry().getTime() >= System.currentTimeMillis()) {
					List<UserRole> userRoles = getRoleByUserId(user.getId());
					user.setPassword(user.getPasswordReset());
					user.setFirstLogin(false);
					userRepository.save(user);
					
					String fileLocation = imagePath + "/" + user.getBranch().getLogo();

					byte[] image;
					try {
						image = imageUtil.getImage(fileLocation);
					} catch (IOException e) {
						image = null;
					}
					String fileLocationQR = imagePathQR + "/" + user.getBranch().getQr();
					byte[] imageQR;
					try {
						imageQR = imageUtil.getImage(fileLocationQR);
					} catch (IOException e) {
						imageQR = null;
					}
					
					String fileLocationProfile = imagePathProfile + "/" + user.getPerson().getImage();
					byte[] profile;
					try {
						profile = imageUtil.getImage(fileLocationProfile);
					} catch (IOException e) {
						profile = null;
					}   
					UserToken usertoken = userTokenRespository.findByUserId(user.getId(), "POINT");
					
					return new SuccessResponse("00", "login Successfully",
							new UserResponse(user, userRoles, usertoken.getApiToken(), image, imageQR, user.getPerson(),profile));

				} else {
					throw new BadRequestException("Your code has expired","02");
				}
			} else {
				throw new BadRequestException("wrong code","01");
			}

		} catch (BadRequestException e) {
			throw new BadRequestException(e.getMessage(),e.getErrorCode());
		}
	}
}
