package com.spring.miniposbackend.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.service.admin.ImageTypeService;

@RestController
@RequestMapping("image-type")
public class ImageTypeController {

	@Autowired
	private ImageTypeService imageTypeService;

	@GetMapping
	@PreAuthorize("hasAnyRole('OWNER')")
	public SuccessResponse getImageType() {
		return new SuccessResponse("00", "Retrieve Success", imageTypeService.showAll());
	}

}
