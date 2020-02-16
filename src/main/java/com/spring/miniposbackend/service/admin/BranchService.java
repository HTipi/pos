package com.spring.miniposbackend.service.admin;


import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.BranchUser;
import com.spring.miniposbackend.model.admin.DeliveryContact;
import com.spring.miniposbackend.repository.admin.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private CorporateRepository corporateRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private BranchUserRepository branchUserRepository;
    @Autowired
    private BranchUserService branchUserService;
    @Autowired
    private DeliveryContactRepository deliveryContactRepository;
    @Autowired
    private DeliveryContactService deliveryContactService;


    public Branch create(Integer corporateId, Integer addressId, Branch branchRequest) {

        if (!this.corporateRepository.existsById(corporateId))
            throw new ResourceNotFoundException("The Corporate is not found!" + corporateId);

        if (!this.addressRepository.existsById(addressId))
            throw new ResourceNotFoundException("The Address is not found!" + addressId);

        return this.corporateRepository.findById(corporateId)
                .map(corporate -> {
                    return this.addressRepository.findById(addressId)
                            .map(address -> {
                                branchRequest.setCorporate(corporate);
                                branchRequest.setAddress(address);
                                return this.branchRepository.save(branchRequest);
                            }).orElseThrow(() -> new ResourceNotFoundException("Address not found" + addressId));
                }).orElseThrow(() -> new ResourceNotFoundException("Not Found"));
    }

    public Branch update(Integer branchId ,Integer corporateId, Integer addressId, Branch branchRequest) {

        if (!this.corporateRepository.existsById(corporateId))
            throw new ResourceNotFoundException("The Corporate is not found!" + corporateId);

        if (!this.addressRepository.existsById(addressId))
            throw new ResourceNotFoundException("The Address is not found!" + addressId);

        if(!this.branchRepository.existsById(branchId))
            throw new ResourceNotFoundException("The Branch is not found!" + branchId);

        return this.corporateRepository.findById(corporateId).map(corporateData -> {

            return this.addressRepository.findById(addressId).map(addressData -> {

                return this.branchRepository.findById(branchId)
                        .map(branch -> {

                            branch.setCorporate(corporateData);
                            branch.setAddress(addressData);
                            branch.setName(branchRequest.getName());
                            branch.setNameKh(branchRequest.getNameKh());
                            branch.setTelephone(branchRequest.getTelephone());
                            branch.setMain(branchRequest.isMain());
                            return this.branchRepository.save(branchRequest);

                        }).orElseThrow(() -> new ResourceNotFoundException("Not Found"));

            }).orElseThrow(() -> new ResourceNotFoundException("Not Found"));

        }).orElseThrow(() -> new ResourceNotFoundException("Not Found"));

    }

    @Transactional(readOnly = true)
    public Branch show(int id) {
        return this.branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found" + id));
    }

    @Transactional(readOnly = true)
    public List<Branch> shows() {
        return this.branchRepository.findAll();
    }

    public Branch enable(int branchId) {

        return this.branchRepository.findById(branchId)
                .map(branch -> {
                    branch.setEnable(true);
                    return this.branchRepository.save(branch);
                }).orElseThrow(() -> new ResourceNotFoundException("Branch not found with id " + branchId));

    }

    @Transactional(readOnly = true)
    public List<Branch> showAllActive() {
        return this.branchRepository.findAllActive();
    }

    public Branch disable(int branchId) {

        return this.branchRepository.findById(branchId)
                .map(branch -> {
                    branch.setEnable(false);
                    return this.branchRepository.save(branch);
                }).orElseThrow(() -> new ResourceNotFoundException("Branch not found with id " + branchId));

    }

    @Transactional(readOnly = true)
    public List<Branch> showAllActiveMainBranch(){
        return this.branchRepository.findAllActiveMainBranch();
    }

    public Branch updateStatus(Integer branchId, Boolean status) {

        return this.branchRepository.findById(branchId)
                .map(branch -> {
                    branch.setEnable(status);
                    return this.branchRepository.save(branch);
                }).orElseThrow(() -> new ResourceNotFoundException("Branch does not exist"));

    }

}
