package com.spring.miniposbackend.controller.admin;


import com.spring.miniposbackend.model.admin.UserRole;
import com.spring.miniposbackend.service.admin.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("userRole")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @GetMapping
    public List<UserRole> shows(){
        return this.userRoleService.shows();
    }

    @GetMapping("active")
    public List<UserRole> showAllActive(){
        return this.userRoleService.showAllActive();
    }

    @PostMapping
    public UserRole create(@RequestBody UserRole userRole){
        return this.userRoleService.create(userRole.getUser().getId(), userRole.getRole().getId(), userRole);
    }

    @PutMapping("{userRoleId}")
    public UserRole update(@PathVariable Long userRoleId, @RequestBody UserRole userRole){
        return this.userRoleService.update(userRoleId,userRole.getUser().getId(), userRole.getRole().getId(), userRole);
    }

    @PatchMapping("{userRoleId}")
    public UserRole updateStatus(@PathVariable Long userRoleId, @RequestBody Boolean status){
        return this.userRoleService.updateStatus(userRoleId, status);
    }
}
