package com.spring.miniposbackend.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.miniposbackend.model.admin.Sex;
import com.spring.miniposbackend.repository.admin.SexRepository;

@Service
public class SexService {

	@Autowired
	private SexRepository sexRepository;

	public List<Sex> showAllSex() {
		return sexRepository.findAll();
	}

}
