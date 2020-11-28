package com.spring.miniposbackend.controller.admin;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.service.admin.ImageService;

@RestController
@RequestMapping("image")
public class ImageController {

	@Autowired
	private ImageService imageService;
	
	@PostMapping("{imageId}/upload")
	public SuccessResponse uploadImage(@PathVariable UUID imageId, @RequestParam("imageFile") MultipartFile file) {
		return new SuccessResponse("00", "Upload Success", imageService.uploadimage(imageId, file));
	}
	
	@GetMapping("get-content/{imageId}")
	public ResponseEntity<byte[]> getImageAsResponseEntity(@PathVariable UUID imageId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl(CacheControl.noCache().getHeaderValue());
		headers.add("Content-Type", "image/svg+xml");
		ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(imageService.getImage(imageId), headers,
				HttpStatus.OK);
		return responseEntity;
	} 

	@GetMapping
	@PreAuthorize("hasAnyRole('OWNER')")
	public SuccessResponse getImageList(@RequestParam("type") String type, @RequestParam("page") int page,
			@RequestParam("length") int length) {
		return new SuccessResponse("00", "fetch Images", imageService.getImages(type, page, length));
	}
	
	@GetMapping("by-category")
	@PreAuthorize("hasAnyRole('OWNER')")
	public SuccessResponse getImageByCategory(@RequestParam("category-id") Integer categoryId, @RequestParam("page") int page,
			@RequestParam("length") int length) {
		return new SuccessResponse("00", "fetch Images", imageService.showByCategory(categoryId, page, length));
	}
//
//	@PostMapping
//	@PreAuthorize("hasAnyRole('OWNER')")
//	public Image create(@RequestParam("category") Integer imageTypeId, @RequestParam("type") String type,
//			@RequestParam("imageFile") MultipartFile file) {
//		return imageService.create(imageTypeId, type, file);
//	}
//
//	@PutMapping("{imageId}")
//	@PreAuthorize("hasAnyRole('OWNER')")
//	public Image update(@PathVariable UUID imageId, @RequestParam("imageFile") MultipartFile file) {
//		return imageService.update(imageId, file);
//	}
}
