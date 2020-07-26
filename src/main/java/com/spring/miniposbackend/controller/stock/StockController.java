package com.spring.miniposbackend.controller.stock;


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

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.model.stock.Stock;
import com.spring.miniposbackend.service.stock.StockService;
import com.spring.miniposbackend.util.UserProfileUtil;
 
@RestController
@RequestMapping("stock")
public class StockController {

	@Autowired
	private StockService stockService;
	@Autowired
	private UserProfileUtil userProfile;

	@PostMapping
	public SuccessResponse create(@RequestBody Stock stock) {
		return new SuccessResponse("00", "create Stock",stockService.create(userProfile.getProfile().getBranch().getId(), stock));
	}

	
	@GetMapping
	public SuccessResponse show() {
		return new SuccessResponse("00", "fetch Stock",
				stockService.showByBranchId(userProfile.getProfile().getBranch().getId()));

	}

	@PatchMapping("{stockId}/description-update")
	public Stock updateDescription(@PathVariable Long stockId, @RequestParam(name = "description") String description) {
		return stockService.updateDescription(stockId, description);
	}

	@DeleteMapping("{stockId}")
	public Stock delete(@PathVariable Long stockId) {
		return stockService.delete(stockId);
	}
}
