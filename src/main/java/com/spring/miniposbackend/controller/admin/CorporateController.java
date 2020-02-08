package com.spring.miniposbackend.controller.admin;

import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.Corporate;
import com.spring.miniposbackend.service.admin.BranchService;
import com.spring.miniposbackend.service.admin.CategoryService;
import com.spring.miniposbackend.service.admin.CorporateService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.util.List;

@RestController
@RequestMapping("corporate")
public class CorporateController {

    @Autowired
    private CorporateService corporateService;
    @Autowired
    private BranchService branchService;

    @GetMapping
    public List<Corporate> shows() {
        return this.corporateService.shows();
    }

    @GetMapping("active")
    public List<Corporate> showAllActive(){
        return this.corporateService.showAllActive();
    }

    @GetMapping("{corporateId}")
    public Corporate show(@PathVariable int corporateId) {
        return this.corporateService.show(corporateId);
    }

    @PostMapping
    public Corporate create(@RequestBody Corporate corporateInput) {

        for (Branch branch : corporateInput.getBranches()) {
            branch = this.branchService.show(branch.getId());
        }

        return corporateInput;
    }

    @PatchMapping("{corporateId}")
    public Corporate updateStatus(@PathVariable Integer corporateId, @RequestBody Boolean status){
        return this.corporateService.updateStatus(corporateId,status);
    }

}
