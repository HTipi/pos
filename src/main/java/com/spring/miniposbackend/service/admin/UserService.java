package com.spring.miniposbackend.service.admin;

import com.spring.miniposbackend.exception.ConflictException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.admin.User;
import com.spring.miniposbackend.model.admin.UserRole;
import com.spring.miniposbackend.repository.admin.UserRepository;
import com.spring.miniposbackend.repository.admin.UserRoleRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
	private UserRoleRepository userRoleRepository;
    
    public User showByUsername(String username) {
    	Optional<User> user = userRepository.findFirstByUsername(username);
    	if(user.isPresent()) {
    		return user.get();
    	}else {
    		throw new ResourceNotFoundException("User not found");
    	}
    }
    
    public User resetPassword(String username,String currentPassword, String newPassword) {
    	return userRepository.findFirstByUsername(username).map(user->{
    		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    		if(encoder.matches(currentPassword, user.getPassword())) {
    			user.setPassword(newPassword);
    			user.setApiToken(null);
    			user.setPassword(encoder.encode(newPassword));
    			return userRepository.save(user);
    		}else{
    			throw new ConflictException("Passowrd mismatched");
    		}
    		
    	}).orElseThrow(()-> new ResourceNotFoundException("User not found"));
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
    
    public List<UserRole> getRoleByUserId(Integer userId){
    	return userRoleRepository.findByUserRoleIdentityUserId(userId,true);
    }
}
