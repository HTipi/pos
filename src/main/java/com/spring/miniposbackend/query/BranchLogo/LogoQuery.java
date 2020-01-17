package com.spring.miniposbackend.query.BranchLogo;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.spring.miniposbackend.model.admin.Logo;
import com.spring.miniposbackend.service.admin.LogoService;
import org.springframework.stereotype.Component;

@Component
public class LogoQuery implements GraphQLQueryResolver {

    private final LogoService branchLogoService;

    public LogoQuery(LogoService branchLogoService) {
        this.branchLogoService = branchLogoService;
    }

    public Logo getBranchLogo(int id) {
        return this.branchLogoService.show(id);
    }

}
