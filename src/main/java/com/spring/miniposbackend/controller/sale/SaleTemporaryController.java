package com.spring.miniposbackend.controller.sale;

import java.util.List;
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
import com.spring.miniposbackend.model.sale.Invoice;
import com.spring.miniposbackend.modelview.SaleRequest;
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
	public SuccessResponse getBySeatId(@RequestParam Integer seatId, @RequestParam Optional<Boolean> isPrinted,
			@RequestParam Optional<Boolean> cancel) {
		return new SuccessResponse("00", "fetch Sale Tmp by Seat", saleService.showBySeatId(seatId, isPrinted, cancel));
	}

	@GetMapping("by-invoice")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse getByInvoiceId(@RequestParam Long invoiceId, @RequestParam Optional<Boolean> isPrinted,
			@RequestParam Optional<Boolean> cancel) {
		return new SuccessResponse("00", "fetch Sale Tmp by User",
				saleService.showByInvoiceId(invoiceId, isPrinted, cancel));
	}

	@GetMapping("status-seat")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse getStatusSeat() {
		return new SuccessResponse("00", "fetch status seat",
				saleService.showStatusSeatByBranchId(userProfile.getProfile().getBranch().getId()));
	}

	@GetMapping("by-user")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse getByUserId(@RequestParam Optional<Boolean> isPrinted,
			@RequestParam Optional<Boolean> cancel) {
		return new SuccessResponse("00", "fetch Sale Tmp by User",
				saleService.showByUserId(userProfile.getProfile().getUser().getId(), isPrinted, cancel));
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse create(@RequestBody List<SaleRequest> requestItem,
			@RequestParam(name = "invoice-id") Optional<Long> invoiceId,
			@RequestParam(name = "seat-id") Optional<Integer> seatId,
			@RequestParam(name = "user-id") Integer userId) {

		return new SuccessResponse("00", "add SaleTmp", saleService.addItems(requestItem, seatId, invoiceId,userId));
	}

	@DeleteMapping("item/{saleTempId}")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse remove(@PathVariable Long saleTempId,
			@RequestParam(name = "invoice-id") Optional<Long> invoiceId,
			@RequestParam(name = "seat-id") Optional<Integer> seatId,
			@RequestParam(name = "user-id") Integer userId) {
		return new SuccessResponse("00", "remove SaleTmp", saleService.removeItem(saleTempId, seatId, invoiceId,userId));
	}

//	@PatchMapping("qty/{saleTempId}")
//	@PreAuthorize("hasAnyRole('SALE')")
//	public SuccessResponse updateQuantity(@PathVariable Long saleTempId,
//			@RequestParam(value = "quantity") Short quantity,
//			@RequestParam(name = "invoice-id") Optional<Long> invoiceId,
//			@RequestParam(name = "seat-id") Optional<Integer> seatId,
//			@RequestParam(name = "user-id") Integer userId) {
//
//		return new SuccessResponse("00", "update QTY",
//				saleService.setQuantity(saleTempId, quantity, seatId, invoiceId,userId));
//	}

	@PostMapping("move-to-pending")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse moveToPending(@RequestParam(name = "seatId") Optional<Integer> seatId,
			@RequestParam String remark,@RequestParam(name = "user-id") Integer userId) {
		Object obj = saleService.moveToPendingOrder(seatId, remark,userId);
		if(obj instanceof Invoice) {
			return new SuccessResponse("00", "Sale has been move to pending",
					obj);
		}
		return new SuccessResponse("0", "Please update your list",
				obj);
		
	}

	@PostMapping("save-to-pending")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse moveToPendingWithoutSaving(@RequestParam(name = "seatId") Optional<Integer> seatId,
			@RequestParam String remark, @RequestBody List<SaleRequest> requestItems) {
		
			return new SuccessResponse("00", "Sale has been move to pending", saleService.moveToPending(requestItems, seatId, remark));
		

	}

	@PatchMapping("{seatId}")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse printBySeat(@PathVariable Integer seatId) {
		try {
			return new SuccessResponse("00", "do Print", saleService.printBySeat(seatId));
		} catch (Exception e) {
			throw new InternalErrorException("print SaleTmp Failed", "04");
		}
	}

}
