package com.spring.miniposbackend.controller;

import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.service.admin.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("branch")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @GetMapping
    public List<Branch> shows() {
        return this.branchService.shows();
    }

    @GetMapping("{findBranchById}")
    public Branch show(@RequestParam Integer branchId) {
        return this.branchService.show(branchId);
    }

    @PostMapping("/createBranch")
    public Branch create(@RequestBody Branch branchRequest) {
        return this.branchService.create(branchRequest.getCorporate().getId(),
                branchRequest.getAddress().getId(),
                branchRequest);
    }

    @PostMapping("/updateBranch")
    public Branch update(@RequestBody Branch branchRequest) {
        return this.branchService.update(branchRequest.getCorporate().getId(),
                branchRequest.getAddress().getId(),
                branchRequest);
    }

    @GetMapping("enableBranchById")
    public Branch enable(@RequestParam Integer branchId) {
        return this.branchService.enable(branchId);
    }

    @GetMapping("disableBranchById")
    public Branch disable(@RequestParam Integer branchId) {
        return this.branchService.disable(branchId);
    }

}
