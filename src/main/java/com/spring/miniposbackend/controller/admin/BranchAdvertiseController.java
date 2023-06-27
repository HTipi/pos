package com.spring.miniposbackend.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.modelview.BranchAdvertiseView;
import com.spring.miniposbackend.service.admin.BranchAdvertiseService;

@RestController
@RequestMapping("branch-advertise")
public class BranchAdvertiseController {

	@Autowired
	private BranchAdvertiseService branchAdvertiseService;
	
	@PostMapping("create")
	public SuccessResponse create(@RequestBody BranchAdvertiseView branchAdvertiseView) throws Exception {
		return new SuccessResponse("00", "Upload Success", branchAdvertiseService.create(branchAdvertiseView));
	}
	
	@PutMapping("update/{id}")
	public SuccessResponse update(@PathVariable int id,@RequestBody BranchAdvertiseView branchAdvertiseView) throws Exception {
		return new SuccessResponse("00", "update Success", branchAdvertiseService.update(id,branchAdvertiseView));
	} 
	
	@GetMapping("show")
	public SuccessResponse get(@RequestParam Optional<Boolean> enable) {
		return new SuccessResponse("00", "Get data Success", branchAdvertiseService.get(enable));
	}
	
	// can not return multi image
	@GetMapping("photo/{branchAdvertiseId}")
	public 	ResponseEntity<byte[]> getImageAsResponseEntity(@PathVariable Long branchAdvertiseId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl(CacheControl.noCache().getHeaderValue());
		headers.setContentType(MediaType.IMAGE_PNG);
		ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(branchAdvertiseService.getFileData(branchAdvertiseId), headers,
					HttpStatus.OK);
		
		return responseEntity;
	}

	
	@PatchMapping("option/{id}")
//	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public SuccessResponse disable(@PathVariable Long id) {
		return new SuccessResponse("00", "Update Item",branchAdvertiseService.disable(id));
	}
	

	@PatchMapping("uploadimage/{id}")
	public SuccessResponse update(@PathVariable int id,@RequestParam MultipartFile file) throws Exception{
		return new SuccessResponse("00","completed updated",branchAdvertiseService.uploadimage(id,file));
	}
	
}
