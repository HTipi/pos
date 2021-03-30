package com.spring.miniposbackend.controller.sale;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.service.sale.InvoiceService;

@RestController
@RequestMapping("invoice")
public class InvoiceController {

	@Autowired
	private InvoiceService invoiceService;

	@GetMapping("by-branch")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse getByUserId() {
		return new SuccessResponse("00", "fetch Invioce by branch", invoiceService.showByBrandId());
	}
	
	@PatchMapping("{invoiceId}/update-remark")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse updateRemark(@PathVariable Long invoiceId, @RequestParam String remark) {
		return new SuccessResponse("00", "Patch remark", invoiceService.updateRemark(invoiceId, remark));
	}
	
	@DeleteMapping("{invoiceId}")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse remove(@PathVariable Long invoiceId) {
		return new SuccessResponse("00", "Patch remark", invoiceService.delete(invoiceId));
	}
}
