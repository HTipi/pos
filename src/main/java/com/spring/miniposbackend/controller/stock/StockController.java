package com.spring.miniposbackend.controller.stock;

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

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.model.stock.Stock;
import com.spring.miniposbackend.service.stock.StockService;

@RestController
@RequestMapping("stock")
public class StockController {

	@Autowired
	private StockService stockService;

	@GetMapping("branch/{branchId}/stock-in/{stockIn}")
	public SuccessResponse show(@PathVariable Integer branchId, @PathVariable Boolean stockIn) {
		return new SuccessResponse("00", "fetch Stock", stockService.showByBranchId(branchId, Optional.of(stockIn), Optional.of(false)));
	}

	@PostMapping("branch/{branchId}")
	public SuccessResponse create(@PathVariable Integer branchId, @RequestBody Stock stock) {
		return new SuccessResponse("00", "create Stock", stockService.create(branchId, stock));
	}

	@PatchMapping("{stockId}/description-update")
	public SuccessResponse updateDescription(@PathVariable Long stockId,
			@RequestParam(name = "description") String description) {
		return new SuccessResponse("00", "delete Stock", stockService.updateDescription(stockId, description));
	}

	@DeleteMapping("{stockId}")
	public SuccessResponse delete(@PathVariable Long stockId) {
		return new SuccessResponse("00", "delete Stock", stockService.delete(stockId));
	}
}
