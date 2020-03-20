package com.spring.miniposbackend.service.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.admin.ItemBranch;
import com.spring.miniposbackend.repository.admin.BranchRepository;
import com.spring.miniposbackend.repository.admin.ItemBranchRepository;

@Service
public class ItemBranchService {
	
	@Autowired
	private ItemBranchRepository itemBranchRepository;
	@Autowired
	private BranchRepository branchRepository;
	
	public List<ItemBranch> showByItemId(Integer itemId, Optional<Boolean> enable){
		if(enable.isPresent()) {
			return itemBranchRepository.findByItemIdWithEnable(itemId, enable.get());
		}else {
			return itemBranchRepository.findByItemId(itemId);
		}
	}
	
	public List<ItemBranch> showByBranchId(Integer branchId, Optional<Boolean> enable){
		return branchRepository.findById(branchId)
				.map(branch -> {
					if(enable.isPresent()) {
						return itemBranchRepository.findByBranchIdWithEnable(branchId, enable.get());
					}else {
						return itemBranchRepository.findByBranchId(branchId);
					}
				})
				.orElseThrow(()-> new ResourceNotFoundException("Branch does not exist"));
	}
	

}
