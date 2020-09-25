package com.spring.miniposbackend.controller.admin;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.modelview.UserResponse;
import com.spring.miniposbackend.service.admin.UserService;
import com.spring.miniposbackend.util.UserProfileUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired 
    private UserProfileUtil userProfile;
    
    @GetMapping("me")
    @PreAuthorize("hasAnyRole('OWNER','SALE')")
    public SuccessResponse getProfile() {
    	return new SuccessResponse("00", "fetch ME",new UserResponse(userService.showByUsername(userProfile.getProfile().getUsername()),userService.getRoleByUserId(userProfile.getProfile().getUser().getId()), null));
    }

//    @GetMapping
//    public List<User> shows() {
//        return this.userService.shows();
//    }
//
//    @GetMapping("active")
//    public List<User> showAllActive() {
//        return this.userService.showAllActive();
//    }
//
//    @PostMapping
//    public User create(@RequestBody User user) {
//        return this.userService.create(user);
//    }
//
//    @PutMapping
//    public User update(@RequestParam Integer userId, @RequestBody User user) {
//        return this.userService.update(userId, user);
//    }
//
//    @PatchMapping("{userId}")
//    public User updateStatus(@PathVariable Integer userId, @RequestBody Boolean status) {
//        return this.userService.updateStatus(userId, status);
//    }

}
