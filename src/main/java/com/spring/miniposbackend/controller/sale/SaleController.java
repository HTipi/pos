package com.spring.miniposbackend.controller.sale;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.spring.miniposbackend.exception.InternalErrorException;
import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.repository.admin.BranchSettingRepository;
import com.spring.miniposbackend.service.sale.SaleService;
import com.spring.miniposbackend.util.UserProfileUtil;

@RestController
@RequestMapping("sale")
public class SaleController {

	@Autowired
	private SaleService saleService;
	@Autowired
	private UserProfileUtil userProfile;
	@Autowired
	private BranchSettingRepository branchSettingRepository;

	@GetMapping("by-user")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse getByUserId(@RequestParam Optional<Long> saleId) {
		return new SuccessResponse("00", "fetch Sale by User",
				saleService.showSaleTranByUser(userProfile.getProfile().getUser().getId(), saleId));
	}

	@GetMapping("summary/by-user")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse getSaleByUserId(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> date) {
		return new SuccessResponse("00", "fetch Sale Sum By User",
				saleService.showSaleByUser(userProfile.getProfile().getUser().getId(), date));
	}

	@GetMapping("by-branch")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse getByBranchId(@RequestParam Integer branchId) {
		return new SuccessResponse("00", "fetch Sale by Branch", saleService.showSaleByBranch(branchId));
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse create(@RequestParam Optional<Integer> seatId, @RequestParam boolean OBU) {
		String val = "false";
		if (OBU)
			val = "true";
		String setting = branchSettingRepository.findByOBU(userProfile.getProfile().getBranch().getId()).orElse("");
		if (val == setting)
			return new SuccessResponse("00", "make Payment", saleService.create(seatId.get(),
					userProfile.getProfile().getBranch().getId(), userProfile.getProfile().getUser().getId(), OBU));
		else
			throw new InternalErrorException("Setting was updated, Please restart!", "12");
	}

	@PatchMapping("reverse/{saleId}")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse reverseSale(@PathVariable Long saleId) {
		return new SuccessResponse("00", "reverse Sale", saleService.reverseSale(saleId));
	}

}
