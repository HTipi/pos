package com.spring.miniposbackend.service.sale;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.sale.Invoice;
import com.spring.miniposbackend.repository.sale.InvoiceRepository;import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class InvoiceService {

	@Autowired
	private InvoiceRepository invoiceRepository;
	@Autowired
	private UserProfileUtil userProfile;
	
	public List<Invoice> showByBrandId(){
		return invoiceRepository.findByBranchId(userProfile.getProfile().getBranch().getId());
	}
	
	public Invoice updateRemark(Long invoiceId,String remark) {
		return invoiceRepository.findById(invoiceId).map((invoice)->{
			invoice.setRemark(remark);
			return invoiceRepository.save(invoice);
		}).orElseThrow(()->new ResourceNotFoundException("Invoice does not exist"));
	}
}
