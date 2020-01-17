package com.spring.miniposbackend.service.admin;

import com.coxautodev.graphql.tools.ResolverError;
import com.spring.miniposbackend.exception.MessageNotFound;
import com.spring.miniposbackend.exception.NotFoundException;
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

    public Logo show(int id) {
        return this.logoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not Found", id));
    }

    public List<Logo> shows(){
        return this.logoRepository.findAll();
    }

    public Logo create(Logo data, int brn_id, int cop_id) {

        Boolean branch = this.branchRepository.existsById(brn_id);

        if (!branch)
            throw new MessageNotFound("The Branch is not found!", brn_id, "brn_id");

        Boolean corporate = this.corporateRepository.existsById(cop_id);

        if (!corporate)
            throw new MessageNotFound("The Corporate is not found!", cop_id, "cop_id");

        return this.branchRepository.findById(brn_id).map(post -> {

            data.setBranch(post);

            return this.corporateRepository.findById(cop_id).map(post_cop -> {

                data.setCorporate(post_cop);

                return this.logoRepository.save(data);
            }).orElseThrow(() -> new ResolverError("Not Found", new Throwable()));

        }).orElseThrow(() -> new ResolverError("Not Found", new Throwable()));
    }

}
