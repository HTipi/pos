//package com.spring.miniposbackend.mutation;
//
//import com.coxautodev.graphql.tools.GraphQLMutationResolver;
//import com.spring.miniposbackend.models.BranchLogo;
//import com.spring.miniposbackend.services.BranchLogoService;
//import com.spring.miniposbackend.services.BranchService;
//
//import org.springframework.stereotype.Component;
//
//@Component
//public class BranchLogoMutation implements GraphQLMutationResolver {
//
//    private final BranchLogoService branchLogoService;
//
//    public BranchLogoMutation(BranchService branchService, BranchLogoService branchLogoService) {
//        this.branchLogoService = branchLogoService;
//    }
//
//    public BranchLogo createBranchLogo(BranchLogo data, int brn_id, int cop_id) {
//        return this.branchLogoService.createBranchLogo(data, brn_id, cop_id);
//    }
//
//    public BranchLogo updateBranchLogo(BranchLogo data, int brn_logo_id, int brn_id, int cop_id) {
//        data.setId(brn_logo_id);
//        return this.branchLogoService.createBranchLogo(data, brn_id, cop_id);
//    }
//}
