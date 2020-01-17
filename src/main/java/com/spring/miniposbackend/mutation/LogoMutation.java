package com.spring.miniposbackend.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.spring.miniposbackend.model.admin.Logo;
import com.spring.miniposbackend.service.admin.BranchService;
import com.spring.miniposbackend.service.admin.LogoService;
import org.springframework.stereotype.Component;

@Component
public class LogoMutation implements GraphQLMutationResolver {

    private final LogoService branchLogoService;

    public LogoMutation(BranchService branchService, LogoService branchLogoService) {
        this.branchLogoService = branchLogoService;
    }

    public Logo createBranchLogo(Logo data, int brn_id, int cop_id) {
        return this.branchLogoService.create(data, brn_id, cop_id);
    }

    public Logo updateBranchLogo(Logo data, int brn_logo_id, int brn_id, int cop_id) {
        data.setId(brn_logo_id);
        return this.branchLogoService.create(data, brn_id, cop_id);
    }
}
