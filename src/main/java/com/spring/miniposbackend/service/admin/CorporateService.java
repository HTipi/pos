//package com.spring.miniposbackend.service.admin;
//
//import com.spring.miniposbackend.exception.ResourceNotFoundException;
//import com.spring.miniposbackend.model.admin.Branch;
//import com.spring.miniposbackend.model.admin.Corporate;
//import com.spring.miniposbackend.repository.admin.BranchRepository;
//import com.spring.miniposbackend.repository.admin.CategoryRepository;
//import com.spring.miniposbackend.repository.admin.CorporateRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class CorporateService {
//
//    @Autowired
//    private CorporateRepository corporateRepository;
//    @Autowired
//    private CategoryRepository categoryRepository;
//    @Autowired
//    private BranchRepository branchRepository;
//    @Autowired
//    private BranchService branchService;
//
//    @Transactional(readOnly = true)
//    public List<Corporate> shows() {
//        return this.corporateRepository.findAll();
//    }
//
//    @Transactional(readOnly = true)
//    public List<Corporate> showAllActive() {
//        return this.corporateRepository.findAllActive();
//    }
//
//    @Transactional(readOnly = true)
//    public List<Corporate> showAllActiveByCategoryId(Integer categoryId){
//        return this.corporateRepository.findAllActiveByCategoryId(categoryId);
//    }
//
//    @Transactional(readOnly = true)
//    public Corporate show(int corporateId) {
//        return this.corporateRepository.findById(corporateId)
//                .orElseThrow(() -> new ResourceNotFoundException("Coporate not found with id " + corporateId));
//    }
//
//    public Corporate create(int categoryId, Corporate corporate) {
//
//        if (!this.categoryRepository.existsById(categoryId))
//            throw new ResourceNotFoundException("The Category Id is not found!" + categoryId);
//
//        return this.categoryRepository.findById(categoryId).map(post -> {
//            corporate.setCategory(post);
//            return this.corporateRepository.save(corporate);
//        }).orElseThrow(() -> new ResourceNotFoundException("Not Found"));
//
//    }
//
//    public  Corporate update(Integer corporateId, Integer categoryId, Corporate corporateRequest) {
//
//        if (!this.categoryRepository.existsById(categoryId))
//            throw new ResourceNotFoundException("The Category Id is not found!" + categoryId);
//
//        if (!this.corporateRepository.existsById(corporateId))
//            throw new ResourceNotFoundException("The Corporate Id is not found!" + corporateId);
//
//        return this.categoryRepository.findById(categoryId)
//                .map(category -> {
//
//                    return this.corporateRepository.findById(corporateId)
//                            .map(corporate -> {
//
//                                corporate.setCategory(category);
//                                corporate.setNameKh(corporateRequest.getNameKh());
//                                corporate.setName(corporateRequest.getName());
//
//                                return this.corporateRepository.save(corporate);
//
//                            }).orElseThrow(() -> new ResourceNotFoundException("Not Found"));
//
//                }).orElseThrow(() -> new ResourceNotFoundException("Not Found"));
//
//    }
//
//    public Corporate enable(Integer coporateId) {
//        return corporateRepository.findById(coporateId)
//                .map(category -> {
//                    category.setEnable(true);
//                    return corporateRepository.save(category);
//                }).orElseThrow(() -> new ResourceNotFoundException("Coporate not found with id " + coporateId));
//    }
//
//    public Corporate disable(Integer coporateId) {
//        return corporateRepository.findById(coporateId)
//                .map(category -> {
//                    category.setEnable(false);
//                    return corporateRepository.save(category);
//                }).orElseThrow(() -> new ResourceNotFoundException("Coporate not found with id " + coporateId));
//    }
//
//    public Corporate updateStatus(Integer corporateId, Boolean status) {
//        return this.corporateRepository.findById(corporateId)
//                .map(corporate -> {
//                    corporate.setEnable(status);
//                    return this.corporateRepository.save(corporate);
//                }).orElseThrow(() -> new ResourceNotFoundException("Coporate not found with id " + corporateId));
//    }
//
//}
