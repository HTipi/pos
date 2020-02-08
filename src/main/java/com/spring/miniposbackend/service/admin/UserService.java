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

    public List<User> showAllActive() {
        return this.userRepository.findAllActive();
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

    public User update(Integer userId, User user) {

        String pwd = user.getPassword();
        String pwd_confirm = user.getConfirmPassword();

        if (!pwd.equals(pwd_confirm))
            throw new BadRequestException("Password not match");

        user.setPassword(webSecurity.passwordEncoder().encode(user.getPassword()));

        try {

            return this.userRepository.findById(userId)
                    .map(userData -> {

                        userData.setFirstName(user.getFirstName());
                        userData.setLastName(user.getLastName());
                        userData.setUsername(user.getUsername());
                        userData.setNameKh(user.getNameKh());
                        userData.setPassword(user.getPassword());
                        userData.setTelephone(user.getTelephone());
                        userData.setDateOfBirth(user.getDateOfBirth());

                        return this.userRepository.save(userData);

                    }).orElseThrow(() -> new ResourceNotFoundException("User not found with id:" + userId));


        } catch (Exception e) {
            throw new InternalErrorException(e.getMessage());
        }

    }

    public User updateStatus(Integer userId, Boolean status) {

        return this.userRepository.findById(userId)
                .map(user -> {
                    user.setEnable(status);
                    return this.userRepository.save(user);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id:" + userId));

    }

}
