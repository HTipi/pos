package com.spring.miniposbackend.service.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.sale.Receipt;
import com.spring.miniposbackend.repository.sale.ReceiptRepository;
import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class ReceiptService {

	@Autowired
	private ReceiptRepository receiptRepository;
	@Autowired
	private UserProfileUtil userProfile;

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
	
	

	public Long resetNumber() {
		return receiptRepository.findFirstByBranchIdOrderByIdDesc(userProfile.getProfile().getBranch().getId()).map(receipt -> {
			receipt.setReceiptNumber(Long.valueOf(0));
			receipt.setBillNumber(Long.valueOf(0));
			return receiptRepository.save(receipt).getReceiptNumber();
		}).orElseThrow(() -> new ResourceNotFoundException("Branch does not exist"));
	}
	
}
