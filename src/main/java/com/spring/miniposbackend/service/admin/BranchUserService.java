package com.spring.miniposbackend.service.admin;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.admin.BranchUser;
import com.spring.miniposbackend.repository.admin.BranchRepository;
import com.spring.miniposbackend.repository.admin.BranchUserRepository;
import com.spring.miniposbackend.repository.admin.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchUserService {

    @Autowired
    private BranchUserRepository branchUserRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private UserRepository userRepository;

    public BranchUser show(Long branchUserId) {
        return this.branchUserRepository.findById(branchUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
    }

    public List<BranchUser> shows() {
        return this.branchUserRepository.findAll();
    }

    public BranchUser create(Integer branchId, Integer userId, BranchUser branchUserRequest) {

        Boolean branch = this.branchRepository.existsById(branchId);

        if (!branch)
            throw new ResourceNotFoundException("The Branch is not found!"+ branchId);

        Boolean user = this.userRepository.existsById(userId);

        if (!user)
            throw new ResourceNotFoundException("The User is not found!"+userId);

        return this.branchRepository.findById(branchId)
                .map(branchData -> {

                    return this.userRepository.findById(userId)
                            .map(userData -> {

                                branchUserRequest.setUser(userData);
                                branchUserRequest.setBranch(branchData);
                                return this.branchUserRepository.save(branchUserRequest);

                            }).orElseThrow(() -> new ResourceNotFoundException("Not Found"));

                }).orElseThrow(() -> new ResourceNotFoundException("Not Found"));

    }

    public BranchUser enable(Long branchUserId) {
        return this.branchUserRepository.findById(branchUserId)
                .map(branchUserData -> {

                    branchUserData.setEnable(true);
                    return this.branchUserRepository.save(branchUserData);

                }).orElseThrow(() -> new ResourceNotFoundException("Branch User not found with id " + branchUserId));
    }

    public BranchUser disable(Long branchUserId) {
        return this.branchUserRepository.findById(branchUserId)
                .map(branchUserData -> {

                    branchUserData.setEnable(false);
                    return this.branchUserRepository.save(branchUserData);

                }).orElseThrow(() -> new ResourceNotFoundException("Branch User not found with id " + branchUserId));
    }

}
