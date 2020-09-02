package com.spring.miniposbackend.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import com.spring.miniposbackend.model.admin.Setting;

public interface SettingRepository extends JpaRepository<Setting, Integer>{
	
	
}
