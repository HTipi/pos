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

//    @Value("${twilio.phone}")
//    private String phone;

    public List<User> shows(Integer userId) {
        return userRepository.findAll();
    }

    public User show(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id:" + userId));
    }

    public User create(User user) {
        user.setPassword(webSecurity.passwordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User createUser(User user) {
        String pwd = user.getPassword();
        String pwd_confirm = user.getConfirmPassword();

        if (!pwd.equals(pwd_confirm))
            throw new BadRequestException("Password not match");

//        boolean role = this.corporateRoleRepository.existsById(role_id);
//        if (!role)
//            throw new NotFoundException("The Role not found", role_id);
//        boolean unique_username = this.userRepository.existsByUser_name(user.getUser_name(), true);
//        if (unique_username)
//            throw new NotFoundException("The UserName is already taken", 0);
//
//        boolean unique_phone = this.userRepository.existsByUser_tel(user.getUser_tel(), true);
//        if (unique_phone)
//            throw new NotFoundException("The Phone number is already taken", 0);


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
