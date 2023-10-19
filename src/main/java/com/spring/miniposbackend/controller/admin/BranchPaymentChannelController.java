package com.spring.miniposbackend.controller.admin;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.modelview.BranchPaymentRequest;
import com.spring.miniposbackend.modelview.PaymentChannelView;
import com.spring.miniposbackend.service.admin.BranchPaymentChannelService;
import com.spring.miniposbackend.util.UserProfileUtil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@CrossOrigin
@RequestMapping("branch-payment")
public class BranchPaymentChannelController {

	@Autowired
	private BranchPaymentChannelService branchPaymentChannelService;

	@Autowired
	private UserProfileUtil userProfile;


	@GetMapping()
	@PreAuthorize("hasAnyRole('OWNER','BRANCH')")
	public SuccessResponse getByCorporateId() {

			return new SuccessResponse("00", "Branch Payment Retrieve",
					branchPaymentChannelService.showAllChannels(userProfile.getProfile().getBranch().getId()));
	}
	@PostMapping("insert")
	@PreAuthorize("hasAnyRole('OWNER','BRANCH','SALE')")
	public SuccessResponse create(@RequestBody BranchPaymentRequest branchPaymentRequest) {
			return new SuccessResponse("00", "Insert payment succssesful",
					branchPaymentChannelService.create(branchPaymentRequest));
	}
	
	@PutMapping("update/{paymentChannelId}")
	@PreAuthorize("hasAnyRole('OWNER','BRANCH','SALE')")
	public SuccessResponse update(@PathVariable Integer paymentChannelId,@RequestParam double percentage,@RequestParam Boolean show) {
			return new SuccessResponse("00", "Branch Payment Retrieve",
					branchPaymentChannelService.update(paymentChannelId,percentage,show));
	}
	
	@PostMapping("create")
	@PreAuthorize("hasAnyRole('OWNER','BRANCH','SALE')")
	public SuccessResponse deleteAndCreate(@RequestBody List<PaymentChannelView> paymentChannelView) {
			return new SuccessResponse("00", "Update Payment Success",
					branchPaymentChannelService.deleteAndCreate(paymentChannelView));
	}
	
	@PostMapping("uploadQr/{branchId}")
	@PreAuthorize("hasAnyRole('OWNER','BRANCH','SALE')")
	public SuccessResponse uploadQr(@PathVariable int branchId ,@RequestParam("imageFile") MultipartFile file,@RequestParam int paymentchannelId) {
			return new SuccessResponse("00", "Upload Qr Successful",
					branchPaymentChannelService.uploadQr(branchId ,file, paymentchannelId));
	}
	
	@GetMapping("photo/{paymentChannelId}")
	 public ResponseEntity<byte[]> getImageAsResponseEntity(@PathVariable Integer paymentChannelId) {
	  HttpHeaders headers = new HttpHeaders();
	  headers.setCacheControl(CacheControl.noCache().getHeaderValue());
	  headers.setContentType(MediaType.IMAGE_PNG);
	  ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(branchPaymentChannelService.getFileData(paymentChannelId), headers,
	    HttpStatus.OK);
	  return responseEntity;
	 }
}
