package com.spring.miniposbackend.service.admin;

import com.coxautodev.graphql.tools.ResolverError;
import com.spring.miniposbackend.exception.MessageNotFound;
import com.spring.miniposbackend.exception.NotFoundException;
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
                .orElseThrow(() -> new NotFoundException("Not Found", Integer.valueOf(branchUserId.toString())));
    }

    public List<BranchUser> shows() {
        return this.branchUserRepository.findAll();
    }

    public BranchUser create(Integer branchId, Integer userId, BranchUser branchUserRequest) {

        Boolean branch = this.branchRepository.existsById(branchId);

        if (!branch)
            throw new MessageNotFound("The Branch is not found!", branchId, "branchId");

        Boolean user = this.userRepository.existsById(userId);

        if (!user)
            throw new MessageNotFound("The User is not found!", userId, "userId");

        return this.branchRepository.findById(branchId)
                .map(branchData -> {

                    return this.userRepository.findById(userId)
                            .map(userData -> {

                                branchUserRequest.setUser(userData);
                                branchUserRequest.setBranch(branchData);
                                return this.branchUserRepository.save(branchUserRequest);

                            }).orElseThrow(() -> new ResolverError("Not Found", new Throwable()));

                }).orElseThrow(() -> new ResolverError("Not Found", new Throwable()));

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
