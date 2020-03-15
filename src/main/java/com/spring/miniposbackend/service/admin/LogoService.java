//package com.spring.miniposbackend.service.admin;
//
//import com.spring.miniposbackend.exception.ResourceNotFoundException;
//import com.spring.miniposbackend.model.admin.Logo;
//import com.spring.miniposbackend.repository.admin.LogoRepository;
//import com.spring.miniposbackend.repository.admin.BranchRepository;
//import com.spring.miniposbackend.repository.admin.CorporateRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//public class LogoService {
//
//    @Autowired
//    private LogoRepository logoRepository;
//    @Autowired
//    private BranchRepository branchRepository;
//    @Autowired
//    private CorporateRepository corporateRepository;
//
//    @Transactional(readOnly = true)
//    public Logo show(int logoId) {
//        return this.logoRepository.findById(logoId)
//                .orElseThrow(() -> new ResourceNotFoundException("Not Found" + logoId));
//    }
//
//    @Transactional(readOnly = true)
//    public List<Logo> shows() {
//        return this.logoRepository.findAll();
//    }
//
//    @Transactional(readOnly = true)
//    public List<Logo> getLogoByCorporateId(Integer corporateId){
//        return this.logoRepository.getLogoByCorporateId(corporateId);
//    }
//
//    @Transactional(readOnly = true)
//    public List<Logo> getLogoByBranchId(Integer branchId){
//        return this.logoRepository.getLogoByBranchId(branchId);
//    }
//
//    @Transactional(readOnly = true)
//    public List<Logo> getLogoByCorporateId_BranchId(Integer corporateId, Integer branchId){
//        return this.logoRepository.getLogoByCorporateId_BranchId(corporateId, branchId);
//    }
//
//    public Logo create(int cop_id, int brn_id, Logo data) {
//
//        if (!this.branchRepository.existsById(brn_id))
//            throw new ResourceNotFoundException("The Branch is not found!" + brn_id);
//
//        if (!this.corporateRepository.existsById(cop_id))
//            throw new ResourceNotFoundException("The Corporate is not found!" + cop_id);
//
//
//        return this.branchRepository.findById(brn_id).map(post -> {
//            data.setBranch(post);
//            return this.corporateRepository.findById(cop_id).map(post_cop -> {
//                data.setCorporate(post_cop);
//                return this.logoRepository.save(data);
//            }).orElseThrow(() -> new ResourceNotFoundException("Not Found"));
//
//        }).orElseThrow(() -> new ResourceNotFoundException("Not Found", new Throwable()));
//
//    }
//
//    public Logo update(Integer logoId, int cop_id, int brn_id, Logo data) {
//
//        if (!this.branchRepository.existsById(brn_id))
//            throw new ResourceNotFoundException("The Branch is not found!" + brn_id);
//
//        if (!this.corporateRepository.existsById(cop_id))
//            throw new ResourceNotFoundException("The Corporate is not found!" + cop_id);
//
//        if (!this.logoRepository.existsById(logoId))
//            throw new ResourceNotFoundException("The Logo is not found!" + logoId);
//
//        return this.logoRepository.findById(logoId)
//                .map(logo -> {
//
//                    return this.corporateRepository.findById(cop_id)
//                            .map(corporate -> {
//
//                                return this.branchRepository.findById(brn_id)
//                                        .map(branch -> {
//
//                                            logo.setCorporate(corporate);
//                                            logo.setBranch(branch);
//                                            logo.setImage(data.getImage());
//                                            return this.logoRepository.save(logo);
//
//                                        }).orElseThrow(() -> new ResourceNotFoundException("Not Found", new Throwable()));
//
//                            }).orElseThrow(() -> new ResourceNotFoundException("Not Found", new Throwable()));
//
//                }).orElseThrow(() -> new ResourceNotFoundException("Not Found", new Throwable()));
//
//    }
//
//    public Logo delete(Integer logoId) {
//        return this.logoRepository.findById(logoId)
//                .map(logo -> {
//                    this.logoRepository.deleteById(logoId);
//                    return logo;
//                }).orElseThrow(() -> new ResourceNotFoundException("Not Found", new Throwable()));
//    }
//
//}
