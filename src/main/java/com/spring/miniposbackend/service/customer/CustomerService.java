package com.spring.miniposbackend.service.customer;
import com.spring.miniposbackend.model.admin.Sex;
import com.spring.miniposbackend.model.customer.Customer;
import com.spring.miniposbackend.modelview.CustomerRequest;
import com.spring.miniposbackend.repository.admin.BranchRepository;
import com.spring.miniposbackend.repository.admin.SexRepository;
import com.spring.miniposbackend.repository.customer.CustomerRepository;
import com.spring.miniposbackend.util.UserProfileUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.miniposbackend.exception.BadRequestException;
import com.spring.miniposbackend.exception.ConflictException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private SexRepository sexRepository;
	
	@Autowired
	private BranchRepository branchRepository;
	
	
	@Autowired
	private UserProfileUtil userProfile;

	@Transactional(readOnly = true)
	public List<Customer> shows() {
		return this.customerRepository.findAll().stream().limit(20).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public List<Customer> showsByQuery(String query) {
		return this.customerRepository.findByCustomerQuery(query,userProfile.getProfile().getBranch().getId()).stream().limit(20).collect(Collectors.toList());
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
	public Customer create(CustomerRequest customer) {

		try {
			Sex sex = sexRepository.findById(customer.getSexId()).orElseThrow(() -> new ResourceNotFoundException("Sex not found"));
			Customer newCustomer = new Customer();
			newCustomer.setEnable(true);
			newCustomer.setName(customer.getName());
			newCustomer.setNameKh(customer.getNameKh());
			newCustomer.setPrimaryPhone(customer.getPrimaryPhone());
			newCustomer.setSecondaryPhone(customer.getSecondaryPhone());
			newCustomer.setSex(sex);
			newCustomer.setDiscount(customer.getDiscount());
			newCustomer.setBranch(userProfile.getProfile().getBranch());
			newCustomer.setPointBranch(true);
			newCustomer.setPointBalance(0);
				return customerRepository.save(newCustomer);
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new BadRequestException(e.getMessage());
		}

	}
	public Customer update(Long customerId,CustomerRequest customer) {

		try {
			Customer newCustomer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
			if(userProfile.getProfile().getBranch().getId() != newCustomer.getBranch().getId())
			{
				throw new ConflictException("Customer diff branches", "09");
			}
			Sex sex = sexRepository.findById(customer.getSexId()).orElseThrow(() -> new ResourceNotFoundException("Sex not found"));
			newCustomer.setName(customer.getName());
			newCustomer.setNameKh(customer.getNameKh());
			newCustomer.setPrimaryPhone(customer.getPrimaryPhone());
			newCustomer.setSecondaryPhone(customer.getSecondaryPhone());
			newCustomer.setSex(sex);
			newCustomer.setDiscount(customer.getDiscount());
			return customerRepository.save(newCustomer);
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}

	}

}
