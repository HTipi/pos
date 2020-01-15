package com.spring.miniposbackend.service.admin;
//package com.spring.miniposbackend.services;
//
//import com.coxautodev.graphql.tools.ResolverError;
//import com.spring.miniposbackend.exception.MessageNotFound;
//import com.spring.miniposbackend.exception.NotFoundException;
//import com.spring.miniposbackend.models.BranchLogo;
//import com.spring.miniposbackend.repositories.BranchLogoRepository;
//import com.spring.miniposbackend.repositories.BranchRepository;
//import com.spring.miniposbackend.repositories.CorporateRepository;
//import com.twilio.rest.serverless.v1.service.environment.Log;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class BranchLogoService {
//
//    @Autowired
//    private BranchLogoRepository branchLogoRepository;
//    @Autowired
//    private BranchRepository branchRepository;
//    @Autowired
//    private CorporateRepository corporateRepository;
//
//    public Log getBranchLogo(int id) {
//        return this.branchLogoRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException("Not Found", id));
//    }
//
//    public BranchLogo createBranchLogo(BranchLogo data, int brn_id, int cop_id) {
//
//        Boolean branch = this.branchRepository.existsById(brn_id);
//
//        if (!branch)
//            throw new MessageNotFound("The Branch is not found!", brn_id, "brn_id");
//
//        Boolean corporate = this.corporateRepository.existsById(cop_id);
//
//        if (!corporate)
//            throw new MessageNotFound("The Corporate is not found!", cop_id, "cop_id");
//
//        return this.branchRepository.findById(brn_id).map(post -> {
//
//            data.setBranch(post);
//
//            return this.corporateRepository.findById(cop_id).map(post_cop -> {
//
//                data.setCorporate(post_cop);
//
//                return this.branchLogoRepository.save(data);
//            }).orElseThrow(() -> new ResolverError("Not Found", new Throwable()));
//
//        }).orElseThrow(() -> new ResolverError("Not Found", new Throwable()));
//    }
//}
