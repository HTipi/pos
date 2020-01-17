package com.spring.miniposbackend.query.BranchLogo;

import com.coxautodev.graphql.tools.GraphQLResolver;


import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.Corporate;
import com.spring.miniposbackend.model.admin.Logo;
import com.spring.miniposbackend.service.admin.BranchService;
import com.spring.miniposbackend.service.admin.CorporateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogoResolver implements GraphQLResolver<Logo> {

    @Autowired
    private BranchService branchService;

    @Autowired
    private CorporateService corporateService;


    public Branch branch(Logo logo) {
        return this.branchService.show(logo.getId());
    }

    public Corporate corporate(Logo logo) {
        return this.corporateService.show(logo.getId());
    }

}
