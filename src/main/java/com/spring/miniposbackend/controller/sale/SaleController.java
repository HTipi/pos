package com.spring.miniposbackend.controller.sale;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.model.sale.SaleDetail;
import com.spring.miniposbackend.modelview.SpitBillItems;
import com.spring.miniposbackend.modelview.sale.SalePaymentRequest;
import com.spring.miniposbackend.modelview.sale.TransactionSalePointView;
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
	
	@GetMapping("summary/by-user-range")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse getSaleRangeByUserId(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Optional<Date> date,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Optional<Date> end,
			@RequestParam boolean byUser,@RequestParam Optional<Integer> paymentId) {
		return new SuccessResponse("00", "fetch Sale Sum By User", saleService.showSaleRangeByUser(date, byUser,paymentId,end));
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
			@RequestBody Optional<SpitBillItems> spitBillItems,@RequestParam Optional<Long> personId,@RequestParam Optional<Integer> transactionTypeId,@RequestParam Optional<Double> discountPercentage) throws IOException {
		boolean check = cancel.isPresent() ? cancel.get() : false;
	
		List list = saleService.create(invoiceId, seatId,channelId, discount, cashIn, change, currencyId,userId,check,remark,serviceCharge,spitBillItems,customerId,vat,personId,transactionTypeId,discountPercentage);
		Object obj = list.get(0);
		if(obj instanceof SaleDetail)
		{
			return new SuccessResponse("00", "Update list", list);
		}
		return new SuccessResponse("0", "make Payment", list);
	}
	@PostMapping("topup-point")
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public SuccessResponse topUpPoint(@RequestBody TransactionSalePointView request) throws IOException {
		return new SuccessResponse("00","Top-Up Point",saleService.topupPoint(request));
	}
	@GetMapping("sale-verify")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse getSaleQr(@RequestParam UUID qr) {
		return new SuccessResponse("00", "verify qr payment", saleService.checkQrSale(qr));
	}
	@PatchMapping("reverse/{saleId}")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse reverseSale(@PathVariable Long saleId) {
		return new SuccessResponse("00", "reverse Sale", saleService.reverseSale(saleId));
	}
	@PostMapping("byQR")
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public SuccessResponse createByQR(@RequestBody SalePaymentRequest request,@RequestParam UUID qr) throws IOException,ParseException {
		List list;
		//PACKAGE
		if(request.getTransactionTypeId() == 7)
		{
			list = saleService.packagesByQr(request,qr);
		}
		else
		list = saleService.createByQr(request,qr);
		Object obj = list.get(0);
		if (obj instanceof SaleDetail) {
			return new SuccessResponse("00", "Update list", list);
		}
		return new SuccessResponse("0", "make Payment", list);
	}
	


}
