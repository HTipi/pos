package com.spring.miniposbackend.service.expense;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.miniposbackend.exception.BadRequestException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.model.expense.Expense;
import com.spring.miniposbackend.model.expense.ExpenseType;
import com.spring.miniposbackend.repository.admin.UserRepository;
import com.spring.miniposbackend.repository.expense.ExpenseRepository;
import com.spring.miniposbackend.repository.expense.ExpenseTypeRepository;
import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepository;

	@Autowired
	private ExpenseTypeRepository expenseTypeRepository;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserProfileUtil userProfile;

	public static Date getFirstDateOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	public static Date getLastDateOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	@Transactional(readOnly = true)
	public List<Expense> showByBranchIdAndMonthly(Integer branchId, @DateTimeFormat(pattern = "yyyy-MM-dd") Date valueDate,Optional<Boolean> isReversed) {
		Date startDate = getFirstDateOfMonth(valueDate);
		Date endDate = getLastDateOfMonth(valueDate);
		boolean reverse = false;
		if(isReversed.isPresent())
		{
			reverse = isReversed.get();
		}
		return expenseRepository.findByBranchIdWithDate(branchId, startDate, endDate,reverse);
	}

	@Transactional(readOnly = true)
	public List<Expense> showByBranchIdAndDate(Integer branchId,@DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,@DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,Optional<Boolean> isReversed) {
		if(startDate.compareTo(endDate) > 0) {
			throw new BadRequestException("StartDate is greater than EndDate","03");
		}
		boolean reverse = false;
		if(isReversed.isPresent())
		{
			reverse = isReversed.get();
		}
		return expenseRepository.findByBranchIdWithDate(branchId, startDate, endDate,reverse);
	}

	@Transactional
	public Expense create(Expense requestItem, Integer userId, Integer expenseTypeId) {
		return userRepository.findById(userId).map((user) -> {
			return expenseTypeRepository.findById(expenseTypeId).map((expenseType) -> {
				Expense expense = new Expense();
				expense.setBranch(user.getBranch());
				expense.setUser(user);
				expense.setRemark(requestItem.getRemark());
				expense.setValueDate(requestItem.getValueDate());
				expense.setExpenseType(expenseType);
				expense.setExpenseAmt(requestItem.getExpenseAmt());
				return expenseRepository.save(expense);
			}).orElseThrow(() -> new ResourceNotFoundException("ExpenseType does not exist","01"));

		}).orElseThrow(() -> new ResourceNotFoundException("User does not exist","01"));

	}

	@Transactional
	public Expense reverse(Integer expenseId, Integer userId) {
		return expenseRepository.findById(expenseId).map((expense) -> {
			return userRepository.findById(userId).map((user) -> {
				if (userProfile.getProfile().getBranch().getId() != user.getBranch().getId()) {
					throw new UnauthorizedException("Expense is unauthorized","02");
				}
				expense.setBranch(user.getBranch());
				expense.setUser(user);
				expense.setReverseDate(new Date());
				expense.setReverse(true);
				return expenseRepository.save(expense);
			}).orElseThrow(() -> new ResourceNotFoundException("User does not exist","01"));
		}).orElseThrow(() -> new ResourceNotFoundException("Expense does not exist","01"));

	}

}
