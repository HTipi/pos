package com.spring.miniposbackend.service.expense;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.User;
import com.spring.miniposbackend.model.expense.ExpenseType;
import com.spring.miniposbackend.repository.admin.BranchRepository;
import com.spring.miniposbackend.repository.admin.UserRepository;
import com.spring.miniposbackend.repository.expense.ExpenseTypeRepository;


@Service
public class ExpenseTypeService {

	@Autowired
	private ExpenseTypeRepository expenseTypeRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BranchRepository branchRepository;

	
	@Transactional(readOnly = true)
	public List<ExpenseType> showByBranchId(Integer branchId,boolean enable){
		
		return expenseTypeRepository.findByBranchIdWithEnable(branchId, enable);
		
	}
	
	public ExpenseType create(Map<String, Integer> requestItem,Integer branchId,Integer userId) {
		
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Record does not exist"));
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Record does not exist"));
        String name = requestItem.get("name").toString();
        String nameKh = requestItem.get("namekh").toString();
        String code = requestItem.get("code").toString();
		ExpenseType expenseType = new ExpenseType();
		expenseType.setBranch(branch);
		expenseType.setUser(user);
		expenseType.setName(name);
		expenseType.setNameKh(nameKh);
		expenseType.setCode(code);
		return expenseTypeRepository.save(expenseType);
	}
}
