//package com.spring.miniposbackend.service.sale;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.spring.miniposbackend.exception.NotFoundException;
//import com.spring.miniposbackend.model.admin.Branch;
//import com.spring.miniposbackend.model.admin.User;
//import com.spring.miniposbackend.model.sale.Sale;
//import com.spring.miniposbackend.repository.admin.BranchRepository;
//import com.spring.miniposbackend.repository.admin.UserRepository;
//import com.spring.miniposbackend.repository.sale.SaleRepository;
//
//@Service
//public class SaleService {
//
//	@Autowired
//	private SaleRepository saleRepository;
//	
//	@Autowired
//	private UserRepository userRepository;
//	
//	@Autowired
//	private BranchRepository branchRepository;
//	
//	@Transactional(readOnly = true)
//	public List<Sale> shows(){
//		return saleRepository.findAll();
//	}
//	
//	@Transactional(readOnly = true)
//	public Sale show(Long saleId) {
//		return saleRepository.findById(saleId)
//        		.orElseThrow(() -> new NotFoundException("Sale is not exist",saleId));
//	}
//	
//	public Sale create(Integer branchId,Integer userId,Sale sale) {
//		
//		Branch branch = branchRepository.findById(branchId)
//				.orElseThrow(() -> new NotFoundException("Branch is not exist",branchId));
//		User user = userRepository.findById(userId)			
//				.orElseThrow(() -> new NotFoundException("User is not exist",userId));
//		sale.setBranch(branch);
//		sale.setUser(user);
//		return saleRepository.save(sale);
//	}
//}
