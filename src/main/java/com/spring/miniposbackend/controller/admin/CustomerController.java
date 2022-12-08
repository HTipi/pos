package com.spring.miniposbackend.controller.admin;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.PatchMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.model.admin.ItemType;
import com.spring.miniposbackend.modelview.CustomerRequest;
import com.spring.miniposbackend.modelview.ImageRequest;
import com.spring.miniposbackend.modelview.ImageResponse;
import com.spring.miniposbackend.service.admin.ItemTypeService;
import com.spring.miniposbackend.service.customer.CustomerService;
import com.spring.miniposbackend.util.UserProfileUtil;

@RestController
@RequestMapping("customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private UserProfileUtil userProfile;

	@GetMapping()
	public SuccessResponse getCustomers(@RequestParam("query") String query) { // will get from user

		return new SuccessResponse("00", "fetch customer",
				customerService.showsByQuery(query));
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse create(@RequestBody CustomerRequest customer) {
		return new SuccessResponse("00", "Create Customer",
				customerService.create(customer));
	}
	
	@PutMapping("{customerId}")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse update(@PathVariable Long customerId, @RequestBody CustomerRequest customer) {
		return new SuccessResponse("00", "Update Customer", customerService.update(customerId, customer));
	}
}
