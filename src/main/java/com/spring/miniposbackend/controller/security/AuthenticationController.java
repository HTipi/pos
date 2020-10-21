package com.spring.miniposbackend.controller.security;

import java.io.IOException;
import java.util.List;

import javax.naming.AuthenticationException;

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

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.model.admin.User;
import com.spring.miniposbackend.model.admin.UserRole;
import com.spring.miniposbackend.model.security.JwtRequest;
import com.spring.miniposbackend.modelview.UserResponse;
import com.spring.miniposbackend.service.admin.UserService;
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
	private ImageUtil imageUtil;
	
	@Value("${file.path.image.branch}")
	private String imagePath;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public SuccessResponse createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws AuthenticationException {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final String token = jwtTokenUtil.generateToken(authenticationRequest.getUsername());
		User user = userService.setApiToken(authenticationRequest.getUsername(), token);
		List<UserRole> userRoles = userService.getRoleByUserId(user.getId());
		String fileLocation = String.format("%s/"+imagePath, System.getProperty("catalina.base"))+ "/"
				+ user.getBranch().getLogo();
		byte[] image;
		try {
			image = imageUtil.getImage(fileLocation);
		} catch (IOException e) {
			image = null;
		}
		return new SuccessResponse("00", "login Successfully",new UserResponse(user,userRoles,image));
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