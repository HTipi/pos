package com.spring.miniposbackend.service.admin;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.miniposbackend.exception.BadRequestException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.model.admin.ItemType;
import com.spring.miniposbackend.model.admin.Printer;
import com.spring.miniposbackend.model.admin.PrinterItemType;
import com.spring.miniposbackend.modelview.PrinterItemTypeRequest;
import com.spring.miniposbackend.modelview.PrinterRequest;
import com.spring.miniposbackend.repository.admin.ItemTypeRepository;
import com.spring.miniposbackend.repository.admin.PrinterItemTypeRepository;
import com.spring.miniposbackend.repository.admin.PrinterRepository;
import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class PrinterService {

	@Autowired
	private PrinterRepository printerRepository;
	@Autowired
	private UserProfileUtil userProfile;
	@Autowired
	private ItemTypeRepository itemTypeRepository;
	@Autowired
	private PrinterItemTypeRepository printerItemTypeRepository;

	public List<Printer> showByBranchId(int branchId) {
		return printerRepository.findByBranchId(branchId);
	}

	public Printer updatePrinter(int printerId, PrinterRequest printer) {
		return printerRepository.findById(printerId).map(printers -> {
			if (userProfile.getProfile().getBranch().getId() != printers.getBranch().getId()) {
				throw new UnauthorizedException("branch is unauthorized");
			}
			printer.setName(printer.getName());
			printers.setCode(printer.getCode());
			printers.setIp(printer.getIp());
			printers.setPaymentPrinter(printer.isPaymentPrinter());
			printers.setSeparatePrinter(printer.isSeparatePrinter());
			return printerRepository.save(printers);
		}).orElseThrow(() -> new ResourceNotFoundException("printer does not exist"));
	}

	public Printer createPrinter(PrinterRequest printer) throws Exception {

		try {
			Printer newPrinter = new Printer();
			newPrinter.setBranch(userProfile.getProfile().getBranch());
			newPrinter.setCode(printer.getCode());
			newPrinter.setIp(printer.getIp());
			newPrinter.setName(printer.getName());
			newPrinter.setEnable(true);
			newPrinter.setSeparatePrinter(printer.isSeparatePrinter());
			newPrinter.setPaymentPrinter(printer.isPaymentPrinter());
			return printerRepository.save(newPrinter);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}

	}

	@Transactional
	public List<PrinterItemType> uploadprinttype(PrinterItemTypeRequest printerItemTypeRequest, Integer printer_id) {
		try {
			List<PrinterItemType> list = new ArrayList<>();
			Printer printer = printerRepository.findById(printer_id)
					.orElseThrow(() -> new ResourceNotFoundException("Printer_id does not exist"));

			printerItemTypeRepository.deleteByPrinterId(printer_id);

			for (int i = 0; i < printerItemTypeRequest.getItemTypeids().size(); i++) {
				PrinterItemType printertypeadd = new PrinterItemType();
				ItemType itemtype = itemTypeRepository.findById(printerItemTypeRequest.getItemTypeids().get(i))
						.orElseThrow(() -> new ResourceNotFoundException("item_id does not exist"));
				printertypeadd.setPrinter(printer);
				printertypeadd.setItemType(itemtype);
				printerItemTypeRepository.save(printertypeadd);
				list.add(printertypeadd);
			}

			return list;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new BadRequestException(e.getMessage(), "not success!");
			// TODO: handle exception
		}
	}

	@Transactional
	public Printer deletePrinter(Integer printerId) {
		Printer printer = printerRepository.findById(printerId)
				.orElseThrow(() -> new ResourceNotFoundException("Printer_id does not exist"));
		printerRepository.deleteByPrinterId(printerId);
		return printer;
	}

}
