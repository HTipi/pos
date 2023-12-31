package com.spring.miniposbackend.controller.stock;

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

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.model.stock.Stock;
import com.spring.miniposbackend.repository.stock.StockEntryRepository;
import com.spring.miniposbackend.service.stock.StockService;
import com.spring.miniposbackend.util.UserProfileUtil;

@RestController
@RequestMapping("stock")
public class StockController {

	@Autowired
	private StockService stockService;
	@Autowired
	private UserProfileUtil userProfile;
	@Autowired
	private StockEntryRepository stockEntryRepository;

	@GetMapping("stock-in")
	@PreAuthorize("hasAnyRole('OWNER','BRANCH')")
	public SuccessResponse getStockIn() {
		return new SuccessResponse("00", "fetch Stock",
				stockService.showStockInByBranchId(userProfile.getProfile().getBranch().getId(), Optional.of(false)));
	}

	@GetMapping("stock-out")
	@PreAuthorize("hasAnyRole('OWNER')")
	public SuccessResponse getStockOut() {
		return new SuccessResponse("00", "fetch Stock", stockService
				.showStockOutByCorporateId(userProfile.getProfile().getCorporate().getId(), Optional.of(false)));
	}

	@GetMapping("stock-dispose")
	@PreAuthorize("hasAnyRole('OWNER','BRANCH')")
	public SuccessResponse getStockDispose() {
		return new SuccessResponse("00", "fetch Stock", stockService
				.showStockDisposeByCorporateId(userProfile.getProfile().getCorporate().getId(), Optional.of(false)));
	}

	@PostMapping("branch/{branchId}/stock-type/{stockType}")
	@PreAuthorize("hasAnyRole('OWNER','BRANCH')")
	public SuccessResponse create(@PathVariable Integer branchId, @PathVariable String stockType,
			@RequestBody Stock stock) {
		return new SuccessResponse("00", "create Stock", stockService.create(branchId, stockType, stock));
	}

	@PatchMapping("{stockId}/description-update")
	@PreAuthorize("hasAnyRole('OWNER','BRANCH')")
	public SuccessResponse updateDescription(@PathVariable Long stockId,
			@RequestParam(name = "description") String description) {
		return new SuccessResponse("00", "delete Stock", stockService.updateDescription(stockId, description));
	}

	@DeleteMapping("{stockId}")
	@PreAuthorize("hasAnyRole('OWNER','BRANCH')")
	public SuccessResponse delete(@PathVariable Long stockId) {
		return new SuccessResponse("00", "delete Stock", stockService.delete(stockId));
	}
}
