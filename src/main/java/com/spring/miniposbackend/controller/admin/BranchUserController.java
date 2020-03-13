//package com.spring.miniposbackend.controller.admin;
//
//
//import com.spring.miniposbackend.model.admin.Branch;
//import com.spring.miniposbackend.model.admin.BranchUser;
//import com.spring.miniposbackend.service.admin.BranchUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("branchUser")
//public class BranchUserController {
//
//    @Autowired
//    private BranchUserService branchUserService;
//
//    @GetMapping
//    public List<BranchUser> shows() {
//        return this.branchUserService.shows();
//    }
//
//    @GetMapping("active")
//    public List<BranchUser> showAllActive() {
//        return this.branchUserService.showAllActive();
//    }
//
//    @GetMapping("by-branchId")
//    public List<BranchUser> showAllActiveByBranchId(@RequestParam Integer branchId){
//        return this.branchUserService.showAllActiveByBranchId(branchId);
//    }
//
//    @GetMapping("by-userId")
//    public List<BranchUser> showAllActiveByUserId(@RequestParam Integer userId){
//        return this.branchUserService.showAllActiveByUserId(userId);
//    }
//
//    @GetMapping("{branchUserId}")
//    public BranchUser show(@PathVariable Long branchUserId) {
//        return this.branchUserService.show(branchUserId);
//    }
//
//    @PostMapping
//    public BranchUser createBranchUser(@RequestParam Integer branchId, @RequestParam Integer userId,@RequestBody BranchUser branchUserRequest) {
//        return this.branchUserService.create(branchId, userId, branchUserRequest);
//    }
//
//    @PutMapping
//    public BranchUser updateBranchUser(@RequestParam Long branchUserId, @RequestParam Integer branchId, @RequestParam Integer userId ,@RequestBody BranchUser branchUserRequest) {
//        return this.branchUserService.update(branchUserId, branchId, userId, branchUserRequest);
//    }
//
//    @PatchMapping("{branchUserId}")
//    public BranchUser updateStatus(@PathVariable Long branchUserId, @RequestBody Boolean status) {
//        return this.branchUserService.updateStatus(branchUserId, status);
//    }
//
//}
