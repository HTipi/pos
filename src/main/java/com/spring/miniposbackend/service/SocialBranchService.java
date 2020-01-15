package com.spring.miniposbackend.service;
//package com.spring.miniposbackend.services;
//
//import com.coxautodev.graphql.tools.ResolverError;
//import com.spring.miniposbackend.exception.MessageNotFound;
//import com.spring.miniposbackend.exception.NotFoundException;
//import com.spring.miniposbackend.models.SocialBranch;
//import com.spring.miniposbackend.repositories.BranchRepository;
//import com.spring.miniposbackend.repositories.CorporateRepository;
//import com.spring.miniposbackend.repositories.SocialBranchRepository;
//import com.spring.miniposbackend.repositories.SocialRepository;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class SocialBranchService {
//
//    @Autowired
//    private SocialBranchRepository socialBranchRepository;
//
//    @Autowired
//    private SocialRepository socialRepository;
//
//    @Autowired
//    private BranchRepository branchRepository;
//
//    @Autowired
//    private CorporateRepository corporateRepository;
//
//    public SocialBranch getSocialBranch(int id) {
//        return this.socialBranchRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException("Not Found", id));
//    }
//
//    public SocialBranch createSocialBranch(SocialBranch data, int soc_id, int brn_id, int cop_id) {
//
//        Boolean social = this.socialRepository.existsById(soc_id);
//
//        if (!social)
//            throw new MessageNotFound("The Social is not found!", soc_id, "soc_id");
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
//
//        return this.socialRepository.findById(soc_id).map(post -> {
//
//            data.setSocial(post);
//
//            return this.branchRepository.findById(brn_id).map(post_brn -> {
//
//                data.setBranch(post_brn);
//
//                return this.corporateRepository.findById(cop_id).map(post_cop -> {
//
//                    data.setCorporate(post_cop);
//
//                    return this.socialBranchRepository.save(data);
//
//                }).orElseThrow(() -> new ResolverError("Not Found", new Throwable()));
//
//            }).orElseThrow(() -> new ResolverError("Not Found", new Throwable()));
//
//        }).orElseThrow(() -> new ResolverError("Not Found", new Throwable()));
//
//    }
//
//}
