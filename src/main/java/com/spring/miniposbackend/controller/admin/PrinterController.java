package com.spring.miniposbackend.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.model.admin.Printer;
import com.spring.miniposbackend.service.admin.PrinterService;
import com.spring.miniposbackend.util.UserProfileUtil;

@RestController
@RequestMapping("printers")
public class PrinterController {

	@Autowired
	private PrinterService printerService;
	@Autowired
	private UserProfileUtil userProfile;

	@GetMapping("by-branch")
	public SuccessResponse getAll() {
		return new SuccessResponse("00", "fetch printers", printerService.showByBranchId(userProfile.getProfile().getBranch().getId()));
	}
	@PutMapping("{printerId}")
	public SuccessResponse updatePrinter(@PathVariable Integer printerId,@RequestBody Printer printer) {
		
		return new SuccessResponse("00", "update printers", printerService.updatePrinter(printerId,printer));
	}
}
