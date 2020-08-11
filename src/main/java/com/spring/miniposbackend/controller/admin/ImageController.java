package com.spring.miniposbackend.controller.admin;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.model.admin.Image;
import com.spring.miniposbackend.service.admin.ImageService;

@RestController
@RequestMapping("image")
public class ImageController {

	@Autowired
	private ImageService imageService;
	
	@GetMapping
	public SuccessResponse getImageList(@RequestParam("type") String type, @RequestParam("page") int page, @RequestParam("length") int length) {
		return new SuccessResponse("00", "fetch Images", imageService.getImages(type, page, length));
	}

	@PostMapping
	public Image create(@RequestParam("type") String type, @RequestParam("imageFile") MultipartFile file) {
		return imageService.create(type, file);
	}
	
	@PutMapping("{imageId}")
	public Image update(@PathVariable UUID imageId,@RequestParam("imageFile") MultipartFile file) {
		return imageService.update(imageId, file);
	}
}
