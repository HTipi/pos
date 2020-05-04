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
import com.spring.miniposbackend.util.UserProfileUtil;

@RestController
@RequestMapping("sale-temp")
public class SaleTemporaryController {

	@Autowired
	private SaleTemporaryService saleService;
	@Autowired
	private UserProfileUtil userProfile;
	
	@GetMapping("by-seat")
	public List<SaleTemporary> getBySeatId(@RequestParam Integer seatId, @RequestParam Optional<Boolean> isPrinted,@RequestParam Optional<Boolean> cancel){
		return saleService.showBySeatId(seatId, isPrinted,cancel);
	}

	@GetMapping("by-user")
	public List<SaleTemporary> getByUserId(@RequestParam Optional<Boolean> isPrinted,@RequestParam Optional<Boolean> cancel){
		return saleService.showByUserId(userProfile.getProfile().getUser().getId(), isPrinted,cancel);
	}
	
	@PostMapping
	public List<SaleTemporary> create(@RequestBody List<Map<String, Integer>> requestItem) {
		return saleService.addItem(requestItem);
	}
	
	@DeleteMapping("item/{saleTempId}")
	public SaleTemporary remove(@PathVariable Long saleTempId){
		return saleService.removeItem(saleTempId);
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
			e.printStackTrace();
			return false;
		}
		
	}
	
}
