package com.spring.miniposbackend.controller.sale;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.spring.miniposbackend.modelview.SaleTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

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
	public List<SaleTransaction> getByUserId(@RequestParam Optional<Long> saleId) {
		return saleService.showSaleTranByUser(userProfile.getProfile().getUser().getId(), saleId);
	}

	@GetMapping("summary/by-user")
	public List<Sale> getSaleByUserId(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> date) {
		return saleService.showSaleByUser(userProfile.getProfile().getUser().getId(),date);
	}

	@GetMapping("by-branch")
	public List<Sale> getByBranchId(@RequestParam Integer branchId) {
		return saleService.showSaleByBranch(branchId);
	}

	@PostMapping
	public Sale create(@RequestParam Integer seatId) {
		return saleService.create(seatId, userProfile.getProfile().getBranch().getId(), userProfile.getProfile().getUser().getId());
	}

	@PatchMapping("reverse/{saleId}")
	public Sale reverseSale(@PathVariable Long saleId) {

		return saleService.reverseSale(saleId);
	}

}
