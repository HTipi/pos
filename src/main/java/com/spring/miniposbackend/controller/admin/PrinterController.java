package com.spring.miniposbackend.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.modelview.PrinterItemTypeRequest;
import com.spring.miniposbackend.modelview.PrinterRequest;
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
		return new SuccessResponse("00", "fetch printers",
				printerService.showByBranchId(userProfile.getProfile().getBranch().getId()));
	}

	@PutMapping("{printerId}")
	public SuccessResponse updatePrinter(@PathVariable Integer printerId, @RequestBody PrinterRequest printer) {

		return new SuccessResponse("00", "update printers", printerService.updatePrinter(printerId, printer));
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('OWNER','BRANCH')")
	public SuccessResponse createPrinter(@RequestBody PrinterRequest printer) throws Exception {

		return new SuccessResponse("00", "create printers", printerService.createPrinter(printer));
	}

	@PostMapping("uploadPrinterItemType")
	public SuccessResponse uploadprinttype(@RequestParam(value = "printer_id") Integer printer_id,
			@RequestBody PrinterItemTypeRequest printerItemTypeRequest) {
		return new SuccessResponse("00", "upload printertypes successed",
				printerService.uploadprinttype(printerItemTypeRequest, printer_id));
	}
	@DeleteMapping("{printerId}")
	public SuccessResponse deletePrinter(@PathVariable Integer printerId) {

		return new SuccessResponse("00", "update printers", printerService.deletePrinter(printerId));
	}
	
}
