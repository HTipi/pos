package com.spring.miniposbackend.controller;


import com.spring.miniposbackend.model.admin.BranchUser;
import com.spring.miniposbackend.service.admin.BranchUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("branchUser")
public class BranchUserController {

    @Autowired
    private BranchUserService branchUserService;

    @GetMapping
    public List<BranchUser> shows() {
        return this.branchUserService.shows();
    }

    @GetMapping("/findBranchUserById")
    public BranchUser show(@RequestParam Long branchUserId) {
        return this.branchUserService.show(branchUserId);
    }

    @PostMapping("/createBranchUser")
    public BranchUser createBranchUser(@RequestBody BranchUser branchUserRequest) {
        return this.branchUserService.create(branchUserRequest.getBranch().getId(),
                branchUserRequest.getUser().getId(),
                branchUserRequest);
    }

    @PostMapping("/updateBranchUser")
    public BranchUser updateBranchUser(@RequestBody BranchUser branchUserRequest) {
        return this.branchUserService.create(branchUserRequest.getBranch().getId(),
                branchUserRequest.getUser().getId(),
                branchUserRequest);
    }

    @GetMapping("/enableBranchUserById")
    public BranchUser enable(@RequestParam Long branchUserId) {
        return this.branchUserService.enable(branchUserId);
    }

    @GetMapping("/disableBranchUserById")
    public BranchUser disable(@RequestParam Long branchUserId) {
        return this.branchUserService.disable(branchUserId);
    }

}
