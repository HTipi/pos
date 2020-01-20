package com.spring.miniposbackend.service.admin;

import com.coxautodev.graphql.tools.ResolverError;
import com.spring.miniposbackend.exception.MessageNotFound;
import com.spring.miniposbackend.exception.NotFoundException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.repository.admin.BranchRepository;
import com.spring.miniposbackend.repository.admin.CorporateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private CorporateRepository corporateRepository;


    public Branch create(Branch data, int cop_id) {

        boolean corporate = this.corporateRepository.existsById(cop_id);

        if (!corporate)
            throw new MessageNotFound("The Corporate is not found!", cop_id, "cop_id");

        return this.corporateRepository.findById(cop_id).map(post -> {

            data.setCorporate(post);

            return this.branchRepository.save(data);
        }).orElseThrow(() -> new ResolverError("Not Found", new Throwable()));

    }

    public Branch update(Branch data, int cop_id) {

        boolean corporate = this.corporateRepository.existsById(cop_id);

        if (!corporate)
            throw new MessageNotFound("The Corporate is not found!", cop_id, "cop_id");

        return this.corporateRepository.findById(cop_id).map(post -> {

            data.setCorporate(post);

            return this.branchRepository.save(data);
        }).orElseThrow(() -> new ResolverError("Not Found", new Throwable()));

    }

    public Branch show(int id) {
        return this.branchRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not Found", id));
    }

    public List<Branch> show() {
        return this.branchRepository.findAll();
    }

    public Branch enable(int branchId) {

        return this.branchRepository.findById(branchId)
                .map(branch -> {
                    branch.setEnable(true);
                    return this.branchRepository.save(branch);
                }).orElseThrow(() -> new ResourceNotFoundException("Branch not found with id " + branchId));

    }

    public Branch disable(int branchId) {

        return this.branchRepository.findById(branchId)
                .map(branch -> {
                    branch.setEnable(false);
                    return this.branchRepository.save(branch);
                }).orElseThrow(() -> new ResourceNotFoundException("Branch not found with id " + branchId));

    }

}
