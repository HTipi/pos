package com.spring.miniposbackend.service.admin;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.BranchUser;
import com.spring.miniposbackend.repository.admin.BranchRepository;
import com.spring.miniposbackend.repository.admin.BranchUserRepository;
import com.spring.miniposbackend.repository.admin.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BranchUserService {

    @Autowired
    private BranchUserRepository branchUserRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public BranchUser show(Long branchUserId) {
        return this.branchUserRepository.findById(branchUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
    }

    @Transactional(readOnly = true)
    public List<BranchUser> shows() {
        return this.branchUserRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<BranchUser> showAllActive() {
        return this.branchUserRepository.findAllActive();
    }

    @Transactional(readOnly = true)
    public List<BranchUser> showAllActiveByBranchId(Integer branchId){
        return this.branchUserRepository.findAllActiveByBranchId(branchId);
    }

    @Transactional(readOnly = true)
    public List<BranchUser> showAllActiveByUserId(Integer userId){
        return this.branchUserRepository.findAllActiveByUserId(userId);
    }

    public BranchUser create(Integer branchId, Integer userId, BranchUser branchUserRequest) {

             if (!this.branchRepository.existsById(branchId))
            throw new ResourceNotFoundException("The Branch is not found!" + branchId);


        if (!this.userRepository.existsById(userId))
            throw new ResourceNotFoundException("The User is not found!" + userId);

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

    public BranchUser update(Long branchUserId,Integer branchId, Integer userId, BranchUser branchUserRequest) {

        if (!this.branchRepository.existsById(branchId))
            throw new ResourceNotFoundException("The Branch is not found!" + branchId);


        if (!this.userRepository.existsById(userId))
            throw new ResourceNotFoundException("The User is not found!" + userId);

        if (!this.branchUserRepository.existsById(branchUserId))
            throw new ResourceNotFoundException("The Branch User is not found!" + userId);

        return this.branchRepository.findById(branchId)
                .map(branchData -> {

                    return this.userRepository.findById(userId)
                            .map(userData -> {

                                return this.branchUserRepository.findById(branchUserId)
                                        .map(branchUser -> {

                                            branchUser.setUser(userData);
                                            branchUser.setBranch(branchData);
                                            return this.branchUserRepository.save(branchUser);

                                        }).orElseThrow(() -> new ResourceNotFoundException("Not Found"));

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

    public BranchUser updateStatus(Long branchUserId, boolean status) {
        return this.branchUserRepository.findById(branchUserId)
                .map(branchUser -> {
                    branchUser.setEnable(status);
                    return this.branchUserRepository.save(branchUser);
                }).orElseThrow(() -> new ResourceNotFoundException("Branch User not found with id " + branchUserId));
    }

}
