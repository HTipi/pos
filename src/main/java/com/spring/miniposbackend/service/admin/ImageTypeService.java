package com.spring.miniposbackend.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.miniposbackend.model.admin.ImageType;
import com.spring.miniposbackend.repository.admin.ImageTypeRepository;

@Service
public class ImageTypeService {

	@Autowired
	private ImageTypeRepository imageTypeRepository;
	
	public List<ImageType> showAll(){
		return imageTypeRepository.findAll();
	}
}
