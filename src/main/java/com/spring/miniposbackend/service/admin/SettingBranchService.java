package com.spring.miniposbackend.service.admin;


import com.spring.miniposbackend.model.admin.SettingBranch;
import com.spring.miniposbackend.repository.admin.SettingBranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingBranchService {

    @Autowired
    private SettingBranchRepository settingBranchRepository;

    public List<SettingBranch> showByBranchId(Integer branchId) {
        return settingBranchRepository.findByBranchId(branchId);
    }
}
