package com.spring.miniposbackend.controller.sale;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.spring.miniposbackend.modelview.SaleTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import com.spring.miniposbackend.model.sale.Sale;
import com.spring.miniposbackend.service.sale.SaleService;

@RestController
@RequestMapping("sale")
public class SaleController {

	@Autowired
	private SaleService saleService;

	@GetMapping("by-user")
	public List<SaleTransaction> getByUserId(@RequestParam Integer userId, @RequestParam Optional<Long> saleId) {
		return saleService.showSaleTranByUser(userId, saleId);
	}

	@GetMapping("summary/by-user")
	public List<Sale> getSaleByUserId(@RequestParam Integer userId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> date) {
		
		return saleService.showSaleByUser(userId,date);
	}

	@GetMapping("by-branch")
	public List<Sale> getByBranchId(@RequestParam Integer branchId) {
		return saleService.showSaleByBranch(branchId);
	}

	@PostMapping
	public Sale create(@RequestParam Integer seatId, @RequestParam Integer branchId, @RequestParam Integer userId) {
		return saleService.create(seatId, branchId, userId);
	}

	@PatchMapping("reverse/{saleId}")
	public Sale reverseSale(@PathVariable Long saleId) {

		return saleService.reverseSale(saleId);
	}

}
