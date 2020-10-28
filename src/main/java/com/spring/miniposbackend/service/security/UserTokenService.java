package com.spring.miniposbackend.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.security.ClientAppUserIdentity;
import com.spring.miniposbackend.model.security.UserToken;
import com.spring.miniposbackend.repository.admin.UserRepository;
import com.spring.miniposbackend.repository.security.ClientApplicationRepository;
import com.spring.miniposbackend.repository.security.UserTokenRespository;

@Service
public class UserTokenService {

	@Autowired
	private ClientApplicationRepository clientAppRepository;
	@Autowired
	private UserTokenRespository userTokenRepository;
	@Autowired
	private UserRepository userRepository;
	
	public UserToken showByApiToken(String token) {
		return userTokenRepository.findFirstByApiToken(token).orElse(null);
	}
	
	public UserToken setApiToken(String  clientAppName,String username, String token) {
			return clientAppRepository.findFirstByName(clientAppName).map((clientApp)->{
				return userRepository.findFirstByUsername(username).map((user)->{
					UserToken userToken = new UserToken();
					userToken.setClientAppUserIdentity(new ClientAppUserIdentity(clientApp, user));
					userToken.setApiToken(token);
					return userTokenRepository.save(userToken);
				}).orElse(null);
			}).orElseThrow(()-> new ResourceNotFoundException("Client Application does not exist"));
	}
}
