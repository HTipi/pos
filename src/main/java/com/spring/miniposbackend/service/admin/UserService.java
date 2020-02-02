package com.spring.miniposbackend.service.admin;

import com.coxautodev.graphql.tools.ResolverError;
import com.spring.miniposbackend.exception.NotFoundException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.admin.User;
import com.spring.miniposbackend.repository.admin.UserRepository;
import com.spring.miniposbackend.util.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WebSecurityConfig webSecurity;

    public List<User> shows() {
        return userRepository.findAll();
    }

    public User show(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id:" + userId));
    }

    public User create(User user) {
        String pwd = user.getPassword();
        String pwd_confirm = user.getConfirmPassword();

        if (!pwd.equals(pwd_confirm))
            throw new NotFoundException("Password not match", 0);

        user.setPassword(webSecurity.passwordEncoder().encode(user.getPassword()));

        try {
            return this.userRepository.save(user);
        } catch (Exception e) {
            throw new NotFoundException(e.getMessage(), 0);
        }


    }

}
