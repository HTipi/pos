package com.spring.miniposbackend.controller.stock;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.model.stock.StockEntry;
import com.spring.miniposbackend.modelview.StockEntryRequest;
import com.spring.miniposbackend.service.stock.StockEntryService;

@RestController
@RequestMapping("stock-entry")
public class StockEntryController {
	
	@Autowired
	private StockEntryService stockEntryService;
	
	@PostMapping("{stockId}")
	public SuccessResponse create(@PathVariable Long stockId,@RequestBody List<StockEntryRequest> stockEntries){
		return new SuccessResponse("00", "create StockEntry", stockEntryService.create(stockId, stockEntries));
	}
	
	@PutMapping("{stockEntryId}")
	public StockEntry update(@PathVariable Long stockEntryId,@RequestBody StockEntryRequest stockEntry){
		return stockEntryService.update(stockEntryId, stockEntry);
	}
	
	@DeleteMapping("{stockEntryId}")
	public StockEntry delete(@PathVariable Long stockEntryId) {
		return stockEntryService.delete(stockEntryId);
	}
	
	@GetMapping("get-by-stock-id/{stockId}")
	SuccessResponse getByStockId(@PathVariable Long stockId,@RequestParam Optional<Boolean> posted){
		return new SuccessResponse("00", "fetch StockEntry", stockEntryService.showByStockId(stockId,posted));
	}
	
}
