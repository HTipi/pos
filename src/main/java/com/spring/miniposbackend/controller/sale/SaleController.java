package com.spring.miniposbackend.controller.sale;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.model.sale.SaleDetail;
import com.spring.miniposbackend.modelview.SpitBillItems;
import com.spring.miniposbackend.service.sale.SaleService;
import com.spring.miniposbackend.util.UserProfileUtil;

@RestController
@RequestMapping("sale")
public class SaleController {

	@Autowired
	private SaleService saleService;
	@Autowired
	private UserProfileUtil userProfile;

	@GetMapping("by-user")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse getByUserId(@RequestParam Optional<Long> saleId) {
		return new SuccessResponse("00", "fetch Sale by User",
				saleService.showSaleTranByUser(userProfile.getProfile().getUser().getId(), saleId));
	}

	@GetMapping("summary/by-user")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse getSaleByUserId(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> date,
			@RequestParam boolean byUser,@RequestParam Optional<Integer> paymentId) {
		return new SuccessResponse("00", "fetch Sale Sum By User", saleService.showSaleByUser(date, byUser,paymentId));
	}

	@GetMapping("by-branch")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse getByBranchId(@RequestParam Integer branchId) {
		return new SuccessResponse("00", "fetch Sale by Branch", saleService.showSaleByBranch(branchId));
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse create(@RequestParam Optional<Long> invoiceId, @RequestParam Optional<Integer> seatId, @RequestParam Optional<Integer> channelId,
			@RequestParam Double discount, @RequestParam Double cashIn, @RequestParam Double change,
			@RequestParam Integer currencyId,@RequestParam Integer userId,@RequestParam Optional<Boolean> cancel,@RequestParam Optional<String> remark,@RequestParam Optional<Double> serviceCharge,
			@RequestParam Optional<Long> customerId,@RequestParam Optional<Double> vat,
			@RequestBody Optional<SpitBillItems> spitBillItems) {
		boolean check = cancel.isPresent() ? cancel.get() : false;
		List list = saleService.create(invoiceId, seatId,channelId, discount, cashIn, change, currencyId,userId,check,remark,serviceCharge,spitBillItems,customerId,vat);
		Object obj = list.get(0);
		if(obj instanceof SaleDetail)
		{
			return new SuccessResponse("00", "Update list", list);
		}
		return new SuccessResponse("0", "make Payment", list);
	}
	@PatchMapping("reverse/{saleId}")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse reverseSale(@PathVariable Long saleId) {
		return new SuccessResponse("00", "reverse Sale", saleService.reverseSale(saleId));
	}

}
