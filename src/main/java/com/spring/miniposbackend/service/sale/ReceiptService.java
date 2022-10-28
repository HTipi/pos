package com.spring.miniposbackend.service.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.sale.Receipt;
import com.spring.miniposbackend.repository.sale.ReceiptRepository;

@Service
public class ReceiptService {

	@Autowired
	private ReceiptRepository receiptRepository;

	public Receipt showByBranchId(Integer branchId) {
		return receiptRepository.findFirstByBranchIdOrderByIdDesc(branchId)
				.orElseThrow(() -> new ResourceNotFoundException("Branch does not exist"));
	}

	@Transactional(readOnly = true)
	public Long getReceiptNumberByBranchId(Integer branchId) {
		return receiptRepository.findFirstByBranchIdOrderByIdDesc(branchId).map(receipt -> {
			receipt.setReceiptNumber(receipt.getReceiptNumber() + 1);
			return receiptRepository.save(receipt).getReceiptNumber();
		}).orElseThrow(() -> new ResourceNotFoundException("Branch does not exist"));
	}
	
	public Long getBillNumberByBranchId(Integer branchId) {
		return receiptRepository.findBillByBranchId(branchId).map(receipt -> {
			receipt.setBillNumber(receipt.getBillNumber() + 1);
			return receiptRepository.save(receipt).getBillNumber();
		}).orElseThrow(() -> new ResourceNotFoundException("Branch does not exist"));
	}
	
	
	@Transactional
	public Long resetNumber(Integer branchId) {
		return receiptRepository.findFirstByBranchIdOrderByIdDesc(branchId).map(receipt -> {
			receipt.setReceiptNumber(Long.valueOf(0));
			return receiptRepository.save(receipt).getReceiptNumber();
		}).orElseThrow(() -> new ResourceNotFoundException("Branch does not exist"));
	}
}
