package com.spring.miniposbackend.controller.sale;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.miniposbackend.exception.InternalErrorException;
import com.spring.miniposbackend.model.SuccessResponse;
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
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse getBySeatId(@RequestParam Integer seatId, @RequestParam Optional<Boolean> isPrinted,@RequestParam Optional<Boolean> cancel){
		return new SuccessResponse("00", "fetch Sale Tmp by Seat", saleService.showBySeatId(seatId, isPrinted,cancel));
	}

	@GetMapping("by-user")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse getByUserId(@RequestParam Optional<Boolean> isPrinted,@RequestParam Optional<Boolean> cancel){
		
		return new SuccessResponse("00", "fetch Sale Tmp by User",saleService.showByUserId(userProfile.getProfile().getUser().getId(), isPrinted,cancel));
	}
	
	@PostMapping
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse create(@RequestBody List<Map<String, Integer>> requestItem) {
		return new SuccessResponse("00", "add SaleTmp", saleService.addItem(requestItem));
	}
	
	@DeleteMapping("item/{saleTempId}")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse remove(@PathVariable Long saleTempId,@RequestParam(value = "seatId") Integer seatId){
		return new SuccessResponse("00", "remove SaleTmp", saleService.removeItem(saleTempId,seatId));
	}
	
	
	@PatchMapping("qty/{saleTempId}")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse updateQuantity(@PathVariable Long saleTempId, @RequestParam(value = "quantity") Short quantity,@RequestParam(value = "seatId") Integer seatId) {
		return new SuccessResponse("00", "update QTY", saleService.setQuantity(saleTempId, quantity,seatId));
	}
	@PatchMapping("{seatId}")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse printBySeat(@PathVariable Integer seatId) {
		try {
			return new SuccessResponse("00", "do Print",saleService.printBySeat(seatId));
		} catch (Exception e) {
			e.printStackTrace();
			throw new InternalErrorException("prin SaleTmp Failed","04");
		}
		
	}
	
}
