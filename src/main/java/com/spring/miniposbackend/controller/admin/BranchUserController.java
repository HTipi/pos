package com.spring.miniposbackend.controller.admin;


import com.spring.miniposbackend.model.admin.Branch;
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

    @GetMapping("active")
    public List<BranchUser> showAllActive() {
        return this.branchUserService.showAllActive();
    }

    @GetMapping("{branchUserId}")
    public BranchUser show(@PathVariable Long branchUserId) {
        return this.branchUserService.show(branchUserId);
    }

    @PostMapping
    public BranchUser createBranchUser(@RequestBody BranchUser branchUserRequest) {
        return this.branchUserService.create(branchUserRequest.getBranch().getId(),
                branchUserRequest.getUser().getId(),
                branchUserRequest);
    }

    @PutMapping
    public BranchUser updateBranchUser(@RequestBody BranchUser branchUserRequest) {
        return this.branchUserService.create(branchUserRequest.getBranch().getId(),
                branchUserRequest.getUser().getId(),
                branchUserRequest);
    }

    @PatchMapping("{branchUserId}")
    public BranchUser updateStatus(@PathVariable Long branchUserId, @RequestBody Boolean status) {
        return this.branchUserService.updateStatus(branchUserId, status);
    }

//    @GetMapping("/enableBranchUserById")
//    public BranchUser enable(@RequestParam Long branchUserId) {
//        return this.branchUserService.enable(branchUserId);
//    }
//
//    @GetMapping("/disableBranchUserById")
//    public BranchUser disable(@RequestParam Long branchUserId) {
//        return this.branchUserService.disable(branchUserId);
//    }

}
