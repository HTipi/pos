package com.spring.miniposbackend.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.spring.miniposbackend.model.admin.Setting;

public interface SettingRepository extends JpaRepository<Setting, Integer>{
	
	
}
