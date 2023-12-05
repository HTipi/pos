package com.spring.miniposbackend.controller.sale;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.service.sale.ReceiptService;

@RestController
@RequestMapping("receipt")
public class ReceiptController {


	@Autowired
	private ReceiptService receiptService;
	
	@PatchMapping("reset")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse updateRemark() {
		return new SuccessResponse("00", "Patch remark", receiptService.resetNumber());
	}
	
}
