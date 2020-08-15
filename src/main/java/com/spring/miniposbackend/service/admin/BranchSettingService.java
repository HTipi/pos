package com.spring.miniposbackend.service.admin;


import com.spring.miniposbackend.model.admin.BranchSetting;
import com.spring.miniposbackend.repository.admin.BranchSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchSettingService {

    @Autowired
    private BranchSettingRepository settingBranchRepository;

    public List<BranchSetting> showByBranchId(Integer branchId,boolean enable) {
        return settingBranchRepository.findByBranchId(branchId,enable);
    }
}
