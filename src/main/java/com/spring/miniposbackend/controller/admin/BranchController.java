package com.spring.miniposbackend.controller.admin;

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

    @GetMapping("active")
    public List<Branch> showAllActive() {
        return this.branchService.showAllActive();
    }

    @GetMapping("{branchId}")
    public Branch get(@PathVariable Integer branchId) {
        return this.branchService.show(branchId);
    }

    @PostMapping
    public Branch create(@RequestBody Branch branch) {
        return this.branchService.create(branch.getCorporate().getId(), branch.getAddress().getId(), branch);
    }

    @PutMapping
    public Branch update(@RequestBody Branch branch){
        return this.branchService.update(branch.getCorporate().getId(), branch.getAddress().getId(), branch);
    }

    @PatchMapping("{branchId}")
    public Branch updateStatus(@PathVariable Integer branchId, @RequestBody boolean status) {
        return this.branchService.updateStatus(branchId, status);
    }

}
