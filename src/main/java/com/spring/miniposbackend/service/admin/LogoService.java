package com.spring.miniposbackend.service.admin;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.admin.Logo;
import com.spring.miniposbackend.repository.admin.LogoRepository;
import com.spring.miniposbackend.repository.admin.BranchRepository;
import com.spring.miniposbackend.repository.admin.CorporateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogoService {

    @Autowired
    private LogoRepository logoRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private CorporateRepository corporateRepository;

    public Logo show(int logoId) {
        return this.logoRepository.findById(logoId)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"+ logoId));
    }

    public List<Logo> shows(){
        return this.logoRepository.findAll();
    }

    public Logo create(Logo data, int brn_id, int cop_id) {

        Boolean branch = this.branchRepository.existsById(brn_id);

        if (!branch)
            throw new ResourceNotFoundException("The Branch is not found!"+ brn_id);

        Boolean corporate = this.corporateRepository.existsById(cop_id);

        if (!corporate)
            throw new ResourceNotFoundException("The Corporate is not found!"+ cop_id);
        		return this.branchRepository.findById(brn_id).map(post -> {
            data.setBranch(post);
            return this.corporateRepository.findById(cop_id).map(post_cop -> {
                data.setCorporate(post_cop);
                return this.logoRepository.save(data);
            }).orElseThrow(() -> new ResourceNotFoundException("Not Found"));

        }).orElseThrow(() -> new ResourceNotFoundException("Not Found", new Throwable()));
    }

}
