package com.spring.miniposbackend.repository.admin;


import com.spring.miniposbackend.model.admin.BranchSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BranchSettingRepository extends JpaRepository<BranchSetting, Integer>{

	@Query(value = "select bs from BranchSetting bs where bs.branch.id = ?1 and bs.setting.enable = ?2")
    List<BranchSetting> findByBranchId(Integer branchId,boolean enable);

}
