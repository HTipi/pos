package com.spring.miniposbackend.service.sale;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.User;
import com.spring.miniposbackend.model.sale.Sale;
import com.spring.miniposbackend.repository.admin.BranchRepository;
import com.spring.miniposbackend.repository.admin.UserRepository;
import com.spring.miniposbackend.repository.sale.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository saleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BranchRepository branchRepository;
	
	@Transactional(readOnly = true)
	public List<Sale> shows(){
		return saleRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Sale show(Long saleId) {
		return saleRepository.findById(saleId)
        		.orElseThrow(() -> new ResourceNotFoundException("Sale is not exist"+saleId));
	}
	
	public Sale create(Integer branchId,Integer userId,Sale sale) {
		
		Branch branch = branchRepository.findById(branchId)
				.orElseThrow(() -> new ResourceNotFoundException("Branch is not exist"+branchId));
		User user = userRepository.findById(userId)			
				.orElseThrow(() -> new ResourceNotFoundException("User is not exist"+userId));
		sale.setValueDate(new Date());
		sale.setReceiptNumber("001"); // to be consider
		sale.setBranch(branch);
		sale.setUser(user);
		return saleRepository.save(sale);
	}
	
	public Sale cancel(Long saleId) {
		return saleRepository.findById(saleId)
				.map(sale->{
					sale.setReverse(true);
					sale.setReverseDate(new Date());
					return saleRepository.save(sale);
				}).orElseThrow(() -> new ResourceNotFoundException("Sale not found with id " + saleId));
	} 
}
