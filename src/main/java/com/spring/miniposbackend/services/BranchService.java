package com.spring.miniposbackend.services;

import com.coxautodev.graphql.tools.ResolverError;
import com.spring.miniposbackend.models.Branch;
import com.spring.miniposbackend.repositories.BranchRepository;
import com.spring.miniposbackend.repositories.CorporateRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private CorporateRepository corporateRepository;

    public List<Branch> shows(){
    	return branchRepository.findAll();
    }
    
    public Branch show(int branchId) {
    	return branchRepository.findById(branchId)
    			.orElseThrow(() -> new ResolverError("Not found", new Throwable()));
    }
    
    public Branch create(int coporate_id, Branch branch) {
        return corporateRepository.findById(coporate_id).map(coporate -> {
        	branch.setCorporate(coporate);
            return branchRepository.save(branch);
        }).orElseThrow(() -> new ResolverError("Not Found", new Throwable()));

    }

//    public Branch updateBranch(Branch data, int cop_id) {
//
//        boolean corporate = corporateRepository.existsById(cop_id);
//
//        if (!corporate)
//            throw new MessageNotFound("The Corporate is not found!", cop_id, "cop_id");
//
//        return this.corporateRepository.findById(cop_id).map(post -> {
//
//            data.setCorporate(post);
//            data.setBrn_update_at(new Date());
//
//            return this.branchRepository.save(data);
//        }).orElseThrow(() -> new ResolverError("Not Found", new Throwable()));
//
//    }

//    public Branch getBranch(int id) {
//        return this.branchRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException("Not Found", id));
//    }
}
