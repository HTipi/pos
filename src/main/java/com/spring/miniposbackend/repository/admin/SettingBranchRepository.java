package com.spring.miniposbackend.repository.admin;


import com.spring.miniposbackend.model.admin.Setting;
import com.spring.miniposbackend.model.admin.SettingBranch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SettingBranchRepository extends JpaRepository<SettingBranch, Integer>{

    List<SettingBranch> findByBranchId(Integer branchId);

}
