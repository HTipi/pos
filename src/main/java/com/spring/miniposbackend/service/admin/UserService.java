package com.spring.miniposbackend.service.admin;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.admin.User;
import com.spring.miniposbackend.repository.admin.UserRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    public User showByUsername(String username) {
    	Optional<User> user = userRepository.findFirstByUsername(username);
    	if(user.isPresent()) {
    		return user.get();
    	}else {
    		throw new ResourceNotFoundException("User not found");
    	}
    }
    
    public User setApiToken(String username, String token) {
    	return userRepository.findFirstByUsername(username)
    			.map(user -> {
    				user.setApiToken(token);
    				return userRepository.save(user);
    			})
				.orElse(null);
    }
    
    public User showByApiToken(String token) {
    	return userRepository.findFirstByApiToken(token)
    			.orElse(null);
    }
}
