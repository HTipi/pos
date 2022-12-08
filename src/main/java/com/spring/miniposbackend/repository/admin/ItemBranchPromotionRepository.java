package com.spring.miniposbackend.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import com.spring.miniposbackend.model.admin.ItemBranchPromotion;
import com.spring.miniposbackend.model.admin.ItemBranchPromotionIdentity;
public interface ItemBranchPromotionRepository extends JpaRepository<ItemBranchPromotion, ItemBranchPromotionIdentity>{
	
}
