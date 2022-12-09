package com.spring.miniposbackend.repository.admin;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.spring.miniposbackend.model.admin.ItemBranchPromotion;
import com.spring.miniposbackend.model.admin.ItemBranchPromotionIdentity;
public interface ItemBranchPromotionRepository extends JpaRepository<ItemBranchPromotion, ItemBranchPromotionIdentity>{
	
	@Query(value = "select a from ItemBranchPromotion a where a.itemBranchPromotionIdentity.itemBranch.branch.id=?1")
	List<ItemBranchPromotion> findByBranchId(Integer branchId);
	
	@Query(value = "select a from ItemBranchPromotion a where a.itemBranchPromotionIdentity.itemBranch.id=?1")
	List<ItemBranchPromotion> findByItemBranchId(Long itemBranchId);
	
	
}
