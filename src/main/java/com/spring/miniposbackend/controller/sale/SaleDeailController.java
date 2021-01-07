package com.spring.miniposbackend.controller.sale;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.service.sale.SaleDetailService;
import com.spring.miniposbackend.util.UserProfileUtil;

@RestController
@RequestMapping("sale-detail")
public class SaleDeailController {

	@Autowired
	private SaleDetailService saleDetailService;
	@Autowired
	private UserProfileUtil userProfile;

	@GetMapping("by-item")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse showTransactionSummary(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to) {
		return new SuccessResponse("00", "fetch transaction",
				saleDetailService.getTransactionSummary(userProfile.getProfile().getBranch().getId(), from, to));
	}
}
