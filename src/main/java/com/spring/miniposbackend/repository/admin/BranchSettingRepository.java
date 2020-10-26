package com.spring.miniposbackend.repository.admin;


import com.spring.miniposbackend.model.admin.BranchSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BranchSettingRepository extends JpaRepository<BranchSetting, Integer>{

	List<BranchSetting> findByBranchId(Integer branchId);
	@Query(value = "select bs from BranchSetting bs where bs.branch.id = ?1 and bs.setting.enable = ?2")
    List<BranchSetting> findByBranchId(Integer branchId,boolean enable);
	
	Optional<BranchSetting> findFirstByBranchIdAndId(Integer branchId, Integer settingId);

	
	@Query(value = "select bs.settingValue from BranchSetting bs where bs.branch.id = ?1 and bs.setting.code='OBU'")
    Optional<String> findByOBU(Integer branchId);
}
