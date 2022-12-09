package com.spring.miniposbackend.service.admin;

import com.spring.miniposbackend.model.admin.ItemBranchPromotion;
import com.spring.miniposbackend.repository.admin.ItemBranchPromotionRepository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemBranchPromotionService {

    @Autowired
    private ItemBranchPromotionRepository itemBranchPromotionRepository;
  
	
	public List<ItemBranchPromotion> showAllPromotionByBranch(Integer branchId) {
		return itemBranchPromotionRepository.findByBranchId(branchId);
	}
	public List<ItemBranchPromotion> showAllPromotionById(Long itemBranchId) {
		return itemBranchPromotionRepository.findByItemBranchId(itemBranchId);
	}
}
