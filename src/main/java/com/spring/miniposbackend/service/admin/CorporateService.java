package com.spring.miniposbackend.service.admin;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.Corporate;
import com.spring.miniposbackend.repository.admin.BranchRepository;
import com.spring.miniposbackend.repository.admin.CategoryRepository;
import com.spring.miniposbackend.repository.admin.CorporateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CorporateService {

    @Autowired
    private CorporateRepository corporateRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private BranchService branchService;


    public List<Corporate> shows() {
        return this.corporateRepository.findAll();
    }

    public List<Corporate> showAllActive() {
        return this.corporateRepository.findAllActive();
    }

    public Corporate show(int corporateId) {
        return this.corporateRepository.findById(corporateId)
                .orElseThrow(() -> new ResourceNotFoundException("Coporate not found with id " + corporateId));
    }

    public Corporate create(int categoryId, Corporate corporate) {

        boolean category = this.categoryRepository.existsById(categoryId);

        if (!category)
            throw new ResourceNotFoundException("The Category Id is not found!" + categoryId);

        List<Branch> branches = new ArrayList<>();

        for (Branch b : branches) {

            if (!this.branchRepository.existsById(b.getId()))
                throw new ResourceNotFoundException("The Branch is not found!" + b.getId());

            branches.add(this.branchService.show(b.getId()));

        }

        corporate.setBranches(branches);

        return this.categoryRepository.findById(categoryId).map(post -> {
            corporate.setCategory(post);
            return this.corporateRepository.save(corporate);
        }).orElseThrow(() -> new ResourceNotFoundException("Not Found"));
    }

    public Corporate enable(Integer coporateId) {
        return corporateRepository.findById(coporateId)
                .map(category -> {
                    category.setEnable(true);
                    return corporateRepository.save(category);
                }).orElseThrow(() -> new ResourceNotFoundException("Coporate not found with id " + coporateId));
    }

    public Corporate disable(Integer coporateId) {
        return corporateRepository.findById(coporateId)
                .map(category -> {
                    category.setEnable(false);
                    return corporateRepository.save(category);
                }).orElseThrow(() -> new ResourceNotFoundException("Coporate not found with id " + coporateId));
    }

    public Corporate updateStatus(Integer corporateId, Boolean status) {
        return this.corporateRepository.findById(corporateId)
                .map(corporate -> {
                    corporate.setEnable(status);
                    return this.corporateRepository.save(corporate);
                }).orElseThrow(() -> new ResourceNotFoundException("Coporate not found with id " + corporateId));
    }

}
