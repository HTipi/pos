package com.spring.miniposbackend.query.SocialBranch;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.Corporate;
import com.spring.miniposbackend.model.admin.Social;
import com.spring.miniposbackend.model.admin.SocialBranch;
import com.spring.miniposbackend.service.admin.BranchService;
import com.spring.miniposbackend.service.admin.CorporateService;
import com.spring.miniposbackend.service.admin.SocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SocialBranchResolver implements GraphQLResolver<SocialBranch> {

    @Autowired
    private SocialService socialService;

    @Autowired
    private BranchService branchService;

    @Autowired
    private CorporateService corporateService;


    public Social social(SocialBranch socialBranch) {
        return this.socialService.show(socialBranch.getSocial().getId());
    }

    public Branch branch(SocialBranch socialBranch) {
        return this.branchService.show(socialBranch.getBranch().getId());
    }

    public Corporate corporate(SocialBranch socialBranch) {
        return this.corporateService.show(socialBranch.getCorporate().getId());
    }

}
