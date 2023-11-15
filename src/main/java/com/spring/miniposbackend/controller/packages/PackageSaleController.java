package com.spring.miniposbackend.controller.packages;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.modelview.person.CheckPrimaryPhone;
import com.spring.miniposbackend.modelview.person.PersonNormalUpdateView;
import com.spring.miniposbackend.modelview.person.PersonPrimaryPhoneView;
import com.spring.miniposbackend.modelview.person.PersonRequest;
import com.spring.miniposbackend.service.packages.PackageSaleService;
import com.spring.miniposbackend.service.person.PersonService;


@RestController
@RequestMapping("package-sale")
public class PackageSaleController {

	@Autowired
	private PackageSaleService packageSaleService;
	
	
	@GetMapping("by-package")
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public SuccessResponse showPackageRangeSummary(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to,@RequestParam long accountId) {

		return new SuccessResponse("00", "fetch transaction",
				packageSaleService.getPackageSaleByDate(from, to,accountId));
	}
}
