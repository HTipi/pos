package com.spring.miniposbackend.service.customer;

import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.customer.Customer;
import com.spring.miniposbackend.model.customer.CustomerPoint;
import com.spring.miniposbackend.repository.admin.BranchRepository;
import com.spring.miniposbackend.repository.customer.CustomerPointRepository;
import com.spring.miniposbackend.repository.customer.CustomerRepository;
import com.spring.miniposbackend.util.UserProfileUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.miniposbackend.exception.BadRequestException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private BranchRepository branchRepository;
	
	@Autowired
	private CustomerPointRepository customerPointRepository;
	
	@Autowired
	private UserProfileUtil userProfile;

	@Transactional(readOnly = true)
	public List<Customer> shows(final int count) {
		return this.customerRepository.findAll().stream().limit(count).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<Customer> showAll() {
		return this.customerRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Customer show(Long customerId) {
		return customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + customerId));
	}

	public Customer enable(Long id) {
		return customerRepository.findById(id).map(customer -> {
			customer.setEnable(true);
			return this.customerRepository.save(customer);
		}).orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));
	}

	public Customer disable(Long id) {
		return customerRepository.findById(id).map(customer -> {
			customer.setEnable(false);
			return this.customerRepository.save(customer);
		}).orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));
	}
	
	public Customer topupBalance(Long id,Integer point) {
		return customerRepository.findById(id).map(customer -> {
			Integer updatePoint = customer.getPointBalance() + point;
			customer.setPointBalance(updatePoint);
			return this.customerRepository.save(customer);
		}).orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));
	}
	@Transactional
	public Customer create(Customer customer) {

		try {
			Customer newCustomer = new Customer();
			newCustomer.setCorporate(userProfile.getProfile().getCorporate());
			newCustomer.setEnable(true);
			newCustomer.setName(customer.getName());
			newCustomer.setNameKh(customer.getNameKh());
			newCustomer.setPointBranch(customer.isPointBranch());
			newCustomer.setPrimaryPhone(customer.getPrimaryPhone());
			newCustomer.setSecondaryPhone(customer.getSecondaryPhone());
			newCustomer.setSex(customer.getSex());
			
			int point = 0;
			if(customer.isPointBranch()) {
				point  = 0;
				newCustomer.setPointBalance(point);
				CustomerPoint customerPoint = new CustomerPoint();
				Branch branch = branchRepository.findById(customer.getBranchId()).orElseThrow(() -> new ResourceNotFoundException("branch not found"));
				customerPoint.setBranch(branch);
				customerPoint.setCustomer(newCustomer);
				customerPoint.setEnable(true);
				customerPoint.setPointBalance(customer.getPointBalance());
				customerPointRepository.save(customerPoint);
				return newCustomer;
				
				
			}
			else {
				point = customer.getPointBalance();
				newCustomer.setPointBalance(point);
				return customerRepository.save(newCustomer);
			}
	
		
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}

	}
	public Customer update(Long customerId,Customer customer) {

		try {
			Customer newCustomer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
			newCustomer.setName(customer.getName());
			newCustomer.setNameKh(customer.getNameKh());
			newCustomer.setPrimaryPhone(customer.getPrimaryPhone());
			newCustomer.setSecondaryPhone(customer.getSecondaryPhone());
			newCustomer.setSex(customer.getSex());
			return customerRepository.save(newCustomer);
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}

	}

}
