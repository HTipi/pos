package com.spring.miniposbackend.query.Branch;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.Corporate;
import com.spring.miniposbackend.service.admin.CorporateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BranchResolver implements GraphQLResolver<Branch> {

    @Autowired
    private CorporateService corporateService;

    public Corporate corporate(Branch branch) {
        return this.corporateService.show(branch.getCorporate().getId());
    }

}
