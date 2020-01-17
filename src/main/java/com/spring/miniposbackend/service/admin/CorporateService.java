package com.spring.miniposbackend.service.admin;

import com.coxautodev.graphql.tools.ResolverError;
import com.spring.miniposbackend.exception.CorporateNotFound;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.admin.Corporate;
import com.spring.miniposbackend.repository.admin.CategoryRepository;
import com.spring.miniposbackend.repository.admin.CorporateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorporateService {

    @Autowired
    private CorporateRepository corporateRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    public List<Corporate> shows() {
        return this.corporateRepository.findAll();
    }

    public Corporate show(int corporateId) {
        return this.corporateRepository.findById(corporateId)
                .orElseThrow(() -> new ResourceNotFoundException("Coporate not found with id " + corporateId));
    }

    public Corporate create(int categoryId, Corporate corporate) {

        boolean category = this.categoryRepository.existsById(categoryId);

        if (!category)
            throw new CorporateNotFound("The Category not found", categoryId);

        return this.categoryRepository.findById(categoryId).map(post -> {
            corporate.setCategory(post);
            return this.corporateRepository.save(corporate);
        }).orElseThrow(() -> new ResolverError("Not Found", new Throwable()));
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

}
