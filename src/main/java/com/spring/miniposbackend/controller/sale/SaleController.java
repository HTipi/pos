package com.spring.miniposbackend.controller.sale;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.spring.miniposbackend.modelview.SaleTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.model.sale.Sale;
import com.spring.miniposbackend.service.sale.SaleService;
import com.spring.miniposbackend.util.UserProfileUtil;

@RestController
@RequestMapping("sale")
public class SaleController {

	@Autowired
	private SaleService saleService;
	
	@Autowired
	private UserProfileUtil userProfile;

	@GetMapping("by-user")
	public SuccessResponse getByUserId(@RequestParam Optional<Long> saleId) {
		return new SuccessResponse("00", "fetch Sale by User", saleService.showSaleTranByUser(userProfile.getProfile().getUser().getId(), saleId));
	}

	@GetMapping("summary/by-user")
	public SuccessResponse getSaleByUserId(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> date) {
		return new SuccessResponse("00", "fetch Sale Sum By User", saleService.showSaleByUser(userProfile.getProfile().getUser().getId(),date));
	}

	@GetMapping("by-branch")
	public SuccessResponse getByBranchId(@RequestParam Integer branchId) {
		return new SuccessResponse("00", "fetch Sale by Branch", saleService.showSaleByBranch(branchId));
	}

	@PostMapping
	public SuccessResponse create(@RequestParam Integer seatId) {
		return new SuccessResponse("00", "make Payment", saleService.create(seatId, userProfile.getProfile().getBranch().getId(), userProfile.getProfile().getUser().getId()));
	}

	@PatchMapping("reverse/{saleId}")
	public SuccessResponse reverseSale(@PathVariable Long saleId) {
		return new SuccessResponse("00", "reverse Sale", saleService.reverseSale(saleId));
	}

}
