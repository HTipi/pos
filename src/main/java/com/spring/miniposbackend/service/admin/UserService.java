package com.spring.miniposbackend.service.admin;

import com.spring.miniposbackend.exception.BadRequestException;
import com.spring.miniposbackend.exception.InternalErrorException;
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
            throw new BadRequestException("Password not match");

        user.setPassword(webSecurity.passwordEncoder().encode(user.getPassword()));

        try {
            return this.userRepository.save(user);
        } catch (Exception e) {
            throw new InternalErrorException(e.getMessage());
        }


    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public User getUser(int id) {

        return this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Found"+id));
    }
}
