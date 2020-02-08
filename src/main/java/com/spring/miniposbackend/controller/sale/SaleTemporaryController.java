package com.spring.miniposbackend.controller.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.miniposbackend.model.sale.SaleTemporary;
import com.spring.miniposbackend.service.sale.SaleTemporaryService;

@RestController
@RequestMapping("sale-temp")
public class SaleTemporaryController {

	@Autowired
	private SaleTemporaryService saleService;
	
	@PostMapping
	public SaleTemporary create(@RequestParam Long seatId, @RequestParam Long itemId, @RequestParam Short quantity) {
		return saleService.addItem(seatId, itemId, quantity);
	}
}
