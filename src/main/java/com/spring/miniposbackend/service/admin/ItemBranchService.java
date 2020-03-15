package com.spring.miniposbackend.service.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.miniposbackend.model.admin.ItemBranch;
import com.spring.miniposbackend.repository.admin.ItemBranchRepository;

@Service
public class ItemBranchService {
	
	@Autowired
	private ItemBranchRepository itemBranchRepository;
	
	public List<ItemBranch> showByItemId(Integer itemId, Optional<Boolean> enable){
		if(enable.isPresent()) {
			return itemBranchRepository.findByItemIdWithEnable(itemId, enable.get());
		}else {
			return itemBranchRepository.findByItemId(itemId);
		}
	}
	
	public List<ItemBranch> showByBranchId(Integer branchId, Optional<Boolean> enable){
		if(enable.isPresent()) {
			return itemBranchRepository.findByBranchIdWithEnable(branchId, enable.get());
		}else {
			return itemBranchRepository.findByBranchId(branchId);
		}
	}
	

}
