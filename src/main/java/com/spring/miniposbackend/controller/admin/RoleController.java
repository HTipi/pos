package com.spring.miniposbackend.controller.admin;

import com.spring.miniposbackend.model.admin.Role;
import com.spring.miniposbackend.service.admin.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<Role> shows() {
        return this.roleService.shows();
    }

    @GetMapping("active")
    public List<Role> showAllActive() {
        return this.roleService.showAllActive();
    }

    @PostMapping
    public Role create(@RequestBody Role role) {
        return this.roleService.create(role);
    }

    @PutMapping
    public Role update(@RequestParam Integer roleId, @RequestBody Role role) {
        return this.roleService.update(roleId, role);
    }

    @PatchMapping("{roleId}")
    public Role updateStatus(@PathVariable Integer roleId, @RequestBody Boolean status) {
        return this.roleService.updateStatus(roleId, status);
    }
}
