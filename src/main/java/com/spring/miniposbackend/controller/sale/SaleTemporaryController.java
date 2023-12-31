package com.spring.miniposbackend.controller.sale;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
	public SuccessResponse getByUserId(@RequestParam Optional<Boolean> isPrinted,
			@RequestParam Optional<Boolean> cancel) {
		return new SuccessResponse("00", "fetch Sale Tmp by User",
				saleService.showByUserId(userProfile.getProfile().getUser().getId(), isPrinted, cancel));
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse create(@RequestBody List<SaleRequest> requestItem,
			@RequestParam(name = "invoice-id") Optional<Long> invoiceId,
			@RequestParam(name = "seat-id") Optional<Integer> seatId, @RequestParam(name = "user-id") Integer userId,
			@RequestParam(name = "channel-id") Optional<Integer> channelId,
			@RequestParam(name = "customer-id") Optional<Long> customerId) {

		return new SuccessResponse("00", "add SaleTmp",
				saleService.addItems(requestItem, seatId, invoiceId, userId, channelId, customerId));
	}

	@PostMapping("change-seat")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse changeSeat(@RequestParam(name = "seat-id") Integer seatId,
			@RequestParam(name = "new-seat-id") Integer newSeatId) {

		return new SuccessResponse("00", "change seat", saleService.changeSeat(seatId, newSeatId));
	}

	@DeleteMapping("item/{saleTempId}")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse remove(@PathVariable Long saleTempId,
			@RequestParam(name = "invoice-id") Optional<Long> invoiceId,
			@RequestParam(name = "seat-id") Optional<Integer> seatId, @RequestParam(name = "user-id") Integer userId) {
		return new SuccessResponse("00", "remove SaleTmp",
				saleService.removeItem(saleTempId, seatId, invoiceId, userId));
	}

	@PatchMapping("discountAmount/{saleTempId}")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse updateDiscountAmount(@PathVariable Long saleTempId,
			@RequestParam(name = "invoice-id") Optional<Long> invoiceId,
			@RequestParam(name = "seat-id") Optional<Integer> seatId, @RequestParam(name = "user-id") Integer userId,
			@RequestBody Double discountAmount) {
		return new SuccessResponse("00", "remove SaleTmp",
				saleService.updateDiscountAmount(saleTempId, seatId, invoiceId, userId, discountAmount));

	}

	@PatchMapping("discountPercentage/{saleTempId}")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse updateDiscountPercentage(@PathVariable Long saleTempId,
			@RequestParam(name = "invoice-id") Optional<Long> invoiceId,
			@RequestParam(name = "seat-id") Optional<Integer> seatId, @RequestParam(name = "user-id") Integer userId,
			@RequestBody Short discount) {
		return new SuccessResponse("00", "remove SaleTmp",
				saleService.updateDiscountPercentage(saleTempId, seatId, invoiceId, userId, discount));

	}

	@PatchMapping("qty/{saleTempId}")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse updateQty(@PathVariable Long saleTempId,
			@RequestParam(name = "invoice-id") Optional<Long> invoiceId,
			@RequestParam(name = "seat-id") Optional<Integer> seatId, @RequestParam(name = "user-id") Integer userId,
			@RequestBody double qty) {
		return new SuccessResponse("00", "remove SaleTmp",
				saleService.updateQty(saleTempId, seatId, invoiceId, userId, qty));

	}

	@PatchMapping("print")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse printItem(@RequestParam(name = "seatId") Optional<Integer> seatId,
			@RequestParam(name = "invoiceId") Optional<Long> invoiceId,
			@RequestParam(name = "customerId") Optional<Long> customerId) {
		if (seatId.isPresent()) {
			return new SuccessResponse("00", "Printed", saleService.printBySeat(seatId.get(), invoiceId, customerId));
		} else {
			return new SuccessResponse("00", "Printed", saleService.printByUser(invoiceId, customerId));
		}

	}

	@PostMapping("move-to-pending")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse moveToPending(@RequestParam(name = "seatId") Optional<Integer> seatId,
			@RequestParam String remark, @RequestParam(name = "userId") Integer userId,
			@RequestParam(name = "customerId") Optional<Long> customerId) {
		Object obj = saleService.moveToPendingOrder(seatId, remark, userId, customerId);
		if (obj instanceof Invoice) {
			return new SuccessResponse("00", "Sale has been move to pending", obj);
		}
		return new SuccessResponse("0", "Please update your list", obj);

	}

	@PostMapping("save-to-pending")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse moveToPendingWithoutSaving(@RequestParam(name = "seatId") Optional<Integer> seatId,
			@RequestParam String remark, @RequestBody List<SaleRequest> requestItems,
			@RequestParam(name = "userId") Integer userId,
			@RequestParam(name = "channel-id") Optional<Integer> channelId) {
		Object obj = saleService.moveToPending(requestItems, seatId, remark, userId, channelId);
		if (obj instanceof Invoice) {
			return new SuccessResponse("00", "Sale has been move to pending", obj);
		}

		return new SuccessResponse("0", "Please update your list", obj);
	}

	@PatchMapping("updateqr")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse update(@RequestParam(name = "user-id") Integer userId,
			@RequestParam(name = "invoice-id") Optional<Long> invoiceId,
			@RequestParam(name = "seat-id") Optional<Integer> seatId) {
		return new SuccessResponse("00", "add SaleTmp", saleService.update(userId, invoiceId, seatId));
	}

	@GetMapping("by-qrnumber")
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public SuccessResponse getByInvoiceId(@RequestParam UUID qrnumber) {
		return new SuccessResponse("00", "fetch Sale Tmp by User", saleService.showByQrnumber(qrnumber));
	}

}
