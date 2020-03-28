package com.spring.miniposbackend.controller.sale;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.miniposbackend.model.sale.Sale;
import com.spring.miniposbackend.model.sale.SaleTemporary;
import com.spring.miniposbackend.service.sale.SaleService;

@RestController
@RequestMapping("sale")
public class SaleController {
	
	
	@Autowired
	private SaleService saleService;
	
	@GetMapping("by-user")
	public List<Sale> getByUserId(@RequestParam Integer userId){
		return saleService.showSaleByUser(userId);
	}
	
	@GetMapping("by-branch")
	public List<Sale> getByBranchId(@RequestParam Integer branchId){
		return saleService.showSaleByBranch(branchId);
	}
	
	@PostMapping
	public Sale create(@RequestParam Integer seatId, @RequestParam Integer branchId,@RequestParam Integer userId) {
		return saleService.create(seatId,branchId,userId);
	}
	
	
	
	
	

}
