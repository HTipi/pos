package com.spring.miniposbackend.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.model.admin.Printer;
import com.spring.miniposbackend.repository.admin.PrinterRepository;
import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class PrinterService {

	@Autowired
	private PrinterRepository printerRepository;
	@Autowired
	private UserProfileUtil userProfile;

	public List<Printer> showByBranchId(int branchId) {
		return printerRepository.findByBranchId(branchId);
	}
	public Printer updatePrinter(int printerId,Printer printer) {
		return printerRepository.findById(printerId).map(printers -> {
			if (userProfile.getProfile().getBranch().getId() != printers.getBranch().getId()) {
				throw new UnauthorizedException("branch is unauthorized");
			}
			printers.setId(printer.getId());
			printers.setPaymentPrinter(printers.isPaymentPrinter());
			printers.setSeparatePrinter(printer.isSeparatePrinter());
			return printerRepository.save(printers);
		}).orElseThrow(() -> new ResourceNotFoundException("printer does not exist"));
	}

}
