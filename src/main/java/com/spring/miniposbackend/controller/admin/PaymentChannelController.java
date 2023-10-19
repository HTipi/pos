package com.spring.miniposbackend.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.service.admin.PaymentChannelService;

@RestController
@RequestMapping("payment-channel")
public class PaymentChannelController {
	
	@Autowired
	private PaymentChannelService paymentChannelService;
	
	@GetMapping()
	@PreAuthorize("hasAnyRole('OWNER','BRANCH')")
	public SuccessResponse showAll() {
			return new SuccessResponse("00", "Payment Channel Retrieve",
					paymentChannelService.showAll());
	}
	
	@GetMapping("show/{paymentChannelId}")
	@PreAuthorize("hasAnyRole('OWNER','BRANCH','SALE')")
	public SuccessResponse showById(@PathVariable Integer paymentChannelId) {
			return new SuccessResponse("00", "Payment Channel Retrieve",
					paymentChannelService.showById(paymentChannelId));
	}
	
	@GetMapping("showallchannel")
	@PreAuthorize("hasAnyRole('OWNER','BRANCH','SALE')")
	public SuccessResponse showById() {
			return new SuccessResponse("00", "Payment Channel Retrieve",
					paymentChannelService.showAllChannel());
	}
	
	@PostMapping("insert")
	@PreAuthorize("hasAnyRole('OWNER','BRANCH','SALE')")
	public SuccessResponse create(@RequestParam String name, @RequestParam String nameKh) {
			return new SuccessResponse("00", "Payment Channel Retrieve",
					paymentChannelService.create(name,nameKh));
	}
	
	@DeleteMapping("delete/{paymentChannelId}")
	@PreAuthorize("hasAnyRole('OWNER','BRANCH','SALE')")
	public void delete(@PathVariable Integer paymentChannelId) {
		paymentChannelService.delete(paymentChannelId);
	}
}
