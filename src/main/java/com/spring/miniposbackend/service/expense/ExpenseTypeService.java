package com.spring.miniposbackend.service.expense;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.model.expense.ExpenseType;
import com.spring.miniposbackend.repository.admin.UserRepository;
import com.spring.miniposbackend.repository.expense.ExpenseTypeRepository;
import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class ExpenseTypeService {

	@Autowired
	private ExpenseTypeRepository expenseTypeRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserProfileUtil userProfile;

	@Transactional(readOnly = true)
	public List<ExpenseType> showByBranchId(Integer branchId, boolean enable) {

		return expenseTypeRepository.findByBranchIdWithEnable(branchId, enable);

	}

	@Transactional
	public ExpenseType create(ExpenseType requestItem, Integer userId) {
		return userRepository.findById(userId).map((user) -> {
			String name = requestItem.getName();
			String nameKh = requestItem.getNameKh();
			String code = requestItem.getCode();
			ExpenseType expenseType = new ExpenseType();
			expenseType.setBranch(user.getBranch());
			expenseType.setUser(user);
			expenseType.setName(name);
			expenseType.setNameKh(nameKh);
			expenseType.setCode(code);
			expenseType.setEnable(true);
			return expenseTypeRepository.save(expenseType);
		}).orElseThrow(() -> new ResourceNotFoundException("user does not exist"));

	}

	@Transactional
	public ExpenseType update(Integer expenseTypeId, ExpenseType type, Integer userId) {
		return expenseTypeRepository.findById(expenseTypeId).map((expenseType) -> {
			return userRepository.findById(userId).map((user) -> {
				if (userProfile.getProfile().getBranch().getId() != user.getBranch().getId()) {
					throw new UnauthorizedException("ExpenseType is unauthorized");
				}
				expenseType.setBranch(user.getBranch());
				expenseType.setUser(user);
				expenseType.setName(type.getName());
				expenseType.setNameKh(type.getNameKh());
				expenseType.setCode(type.getCode());
				return expenseTypeRepository.save(expenseType);
			}).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
		}).orElseThrow(() -> new ResourceNotFoundException("ExpenseType does not exist"));

	}
	@Transactional
	public ExpenseType delete(Integer expenseTypeId, Integer userId) {
		return expenseTypeRepository.findById(expenseTypeId).map((expenseType) -> {
			return userRepository.findById(userId).map((user) -> {
				if (userProfile.getProfile().getBranch().getId() != user.getBranch().getId()) {
					throw new UnauthorizedException("ExpenseType is unauthorized");
				}
				expenseType.setBranch(user.getBranch());
				expenseType.setUser(user);
				expenseType.setEnable(false);
				return expenseTypeRepository.save(expenseType);
			}).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
		}).orElseThrow(() -> new ResourceNotFoundException("ExpenseType does not exist"));

	}
}
