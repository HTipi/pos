package com.spring.miniposbackend.controller.sale;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@GetMapping("by-seat")
	public List<SaleTemporary> getBySeatId(@RequestParam Integer seatId, @RequestParam Optional<Boolean> isPrinted,@RequestParam Optional<Boolean> cancel){
		return saleService.showBySeatId(seatId, isPrinted,cancel);
	}

	@GetMapping("by-user")
	public List<SaleTemporary> getByUserId(@RequestParam Integer userId, @RequestParam Optional<Boolean> isPrinted,@RequestParam Optional<Boolean> cancel){
		return saleService.showByUserId(userId, isPrinted,cancel);
	}
	
	@PostMapping
	public List<SaleTemporary> create(@RequestBody List<Map<String, Integer>> requestItem) {
		return saleService.addItem(requestItem);
	}
	
	@DeleteMapping("item/{saleTempId}")
	public SaleTemporary remove(@PathVariable Long saleTempId){
		return saleService.removeItem(saleTempId);
	}

	@DeleteMapping("{seatId}")
	public List<SaleTemporary> removeBySeat(@PathVariable Integer seatId){
		return saleService.removeItemBySeat(seatId);
	}
	
	@PatchMapping("qty/{saleTempId}")
	public SaleTemporary updateQuantity(@PathVariable Long saleTempId, @RequestParam(value = "quantity") Short quantity) {
		return saleService.setQuantity(saleTempId, quantity);
	}
	@PatchMapping("{seatId}")
	public boolean printBySeat(@PathVariable Integer seatId) {
		try {
			return saleService.printBySeat(seatId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
}
