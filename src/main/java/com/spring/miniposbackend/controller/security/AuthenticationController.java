package com.spring.miniposbackend.controller.security;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.naming.AuthenticationException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.miniposbackend.exception.BadRequestException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.model.admin.User;
import com.spring.miniposbackend.model.admin.UserRole;
import com.spring.miniposbackend.model.security.JwtRequest;
import com.spring.miniposbackend.model.security.UserToken;

import com.spring.miniposbackend.modelview.UserResponse;
import com.spring.miniposbackend.modelview.person.LoginView;
import com.spring.miniposbackend.repository.admin.UserRepository;
import com.spring.miniposbackend.repository.security.UserTokenRespository;
import com.spring.miniposbackend.service.admin.UserService;
import com.spring.miniposbackend.service.security.UserTokenService;
import com.spring.miniposbackend.util.ImageUtil;
import com.spring.miniposbackend.util.JwtTokenUtil;

@RestController
@CrossOrigin
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private UserService userService;
	@Autowired
	UserTokenService userTokenService;
	@Autowired
	private ImageUtil imageUtil;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserTokenRespository userTokenRespository;

	@Value("${file.path.image.branch}")
	private String imagePath;

	@Value("${file.path.image.branch-channel}")
	private String imagePathQR;

	@Value("${file.path.image.profile}")
	private String imagePathProfile;
	
	

	private final long expiryInterval = 5L * 60 * 1000; 

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public SuccessResponse token(@RequestBody JwtRequest authenticationRequest) throws AuthenticationException , Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		try {
			if (authenticationRequest.getClientAppName().equalsIgnoreCase("POINT")
					&& authenticationRequest.getUsername().startsWith("0")) {
				User userVerify = userRepository.findByTelephoneWithPassword(authenticationRequest.getUsername())
						.orElseThrow(() -> new ResourceNotFoundException("username and password are incorrect"));
				userService.limitSendOtp(userVerify);		
				Integer generateCodes = userService.generateCode();
				final String Persontoken = jwtTokenUtil.generateToken(authenticationRequest.getUsername());				
				userTokenService.setApiTokenPerson(authenticationRequest.getClientAppName(),
						authenticationRequest.getUsername(), Persontoken);
				if(userVerify.getFirstLogin()==true) {
					return new SuccessResponse("04", "first time login", Persontoken);
				}
				userVerify.setOneTimePasswordCode(generateCodes);
				userVerify.setExpiry(new Date(System.currentTimeMillis() + expiryInterval));
				userRepository.save(userVerify);

			    userService.sms(userVerify.getUsername(),userVerify.getOneTimePasswordCode());
					

				return new SuccessResponse("00", "Get code successfully", "");

			} else {
				final String token = jwtTokenUtil.generateToken(authenticationRequest.getUsername());
				UserToken userToken = userTokenService.setApiToken(authenticationRequest.getClientAppName(),
						authenticationRequest.getUsername(), token);
				User user = userToken.getClientAppUserIdentity().getUser();
				List<UserRole> userRoles = userService.getRoleByUserId(user.getId());
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
				return new SuccessResponse("00", "login Successfully",
						new UserResponse(user, userRoles, token, image, imageQR));
			}

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	@RequestMapping(value = "/verifying", method = RequestMethod.POST)
	public Object otp(@RequestBody LoginView code) throws Exception {

		User user = userRepository.findByOtpCode(code.getCode());
		if (user == null) {
			throw new BadRequestException("wrong code","01");
		}

		try {
			if (user.getOneTimePasswordCode().equals(code.getCode())) {
				if (user.getExpiry().getTime() >= System.currentTimeMillis()) {
					List<UserRole> userRoles = userService.getRoleByUserId(user.getId());
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

	private void authenticate(String username, String password) throws AuthenticationException {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		} catch (DisabledException e) {
			throw new DisabledException("USER_DISABLED", e);
		} catch (LockedException e) {
			throw new LockedException("USER_LOCKED", e);
		} catch (BadCredentialsException e) {

			throw new BadCredentialsException("Login Failed", e);
		}
	}
}