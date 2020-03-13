//package com.spring.miniposbackend.controller.admin;
//
//import com.spring.miniposbackend.model.admin.User;
//import com.spring.miniposbackend.repository.admin.UserRepository;
//import com.spring.miniposbackend.service.admin.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("user")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
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
//
//}
