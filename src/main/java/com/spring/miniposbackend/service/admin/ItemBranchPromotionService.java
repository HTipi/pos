package com.spring.miniposbackend.service.admin;

import com.spring.miniposbackend.exception.BadRequestException;
import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.model.admin.BranchPromotion;
import com.spring.miniposbackend.model.admin.ItemBranch;
import com.spring.miniposbackend.model.admin.ItemBranchPromotion;
import com.spring.miniposbackend.model.admin.ItemBranchPromotionIdentity;
import com.spring.miniposbackend.modelview.ListItemBranchhId;
import com.spring.miniposbackend.repository.admin.BranchPromotionRepository;
import com.spring.miniposbackend.repository.admin.ItemBranchPromotionRepository;
import com.spring.miniposbackend.repository.admin.ItemBranchRepository;
import com.spring.miniposbackend.util.UserProfileUtil;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemBranchPromotionService {

    @Autowired
    private ItemBranchPromotionRepository itemBranchPromotionRepository;
    @Autowired
    private BranchPromotionRepository branchPromotionRepository;
	@Autowired
	private UserProfileUtil userProfile;
	
	 @Autowired
	    private ItemBranchRepository itemBranchRepository;
  
	
	public List<ItemBranchPromotion> showAllPromotionByBranch(Integer branchId) {
		return itemBranchPromotionRepository.findByBranchId(branchId);
	}
	public List<ItemBranchPromotion> showAllPromotionByBranchId(Integer promotionBranchId) {
		return itemBranchPromotionRepository.findByPromotionBranchId(promotionBranchId);
	}
	public List<ItemBranchPromotion> showAllPromotionById(Long itemBranchId) {
		return itemBranchPromotionRepository.findByItemBranchId(itemBranchId);
	}
	@Transactional
	 public List<ItemBranchPromotion> createItemBranchPromotion(ListItemBranchhId itemBranchId, Integer branchPromotionId) {
	  ItemBranchPromotion itemBranchPro = null;
	  List<ItemBranchPromotion> list = new ArrayList<>();
	  BranchPromotion branchPromotion = branchPromotionRepository.findById(branchPromotionId).orElseThrow(() -> new BadRequestException("this BranchPromotion does not exit","01"));
	  itemBranchPromotionRepository.deleteByBranchPromotion(branchPromotionId);
	  for(int i=0; i<itemBranchId.getItemBranchId().size(); i++) {
	    ItemBranch itemBranch = itemBranchRepository.findById(itemBranchId.getItemBranchId().get(i)).orElseThrow(() -> new BadRequestException("this Item does not exit","02"));
	    if(userProfile.getProfile().getBranch().getId() != itemBranch.getBranch().getId()) {
	     throw new UnauthorizedException("Branch Unauthorized!","03");
	    }
	    itemBranchPro  = new ItemBranchPromotion();
	    ItemBranchPromotionIdentity itemBranchIdentity = new ItemBranchPromotionIdentity();
	    itemBranchIdentity.setBranchPromotion(branchPromotion);
	    itemBranchIdentity.setItemBranch(itemBranch);
	    //itemBranchIdentity.setItemType(itemBranch.getItem().getItemType());
	    itemBranchPro.setItemBranchPromotionIdentity(itemBranchIdentity);
	    list.add(itemBranchPro);
	    itemBranchPromotionRepository.save(itemBranchPro);
	  }
	   
	  return list;
	 }
	 
	 @Transactional
	 public List<ItemBranchPromotion> updateItemBranchPromotion(ListItemBranchhId itemBranchId, Integer branchPromotionId) {
	  ItemBranchPromotion itemBranchPro = null;
	  List<ItemBranchPromotion> list = new ArrayList<>();
	  BranchPromotion branchPromotion = branchPromotionRepository.findById(branchPromotionId)
	    .orElseThrow(() -> new BadRequestException("this BranchPromotion does not exit","01"));
	  //itemBranchPromotionRepository.deleteByBranchPromotion(branchPromotion.getId());
	  for(int i=0; i<itemBranchId.getItemBranchId().size(); i++) {
	    ItemBranch itemBranch = itemBranchRepository.findById(itemBranchId.getItemBranchId().get(i))
	      .orElseThrow(() -> new BadRequestException("this Item does not exit","02"));
	    if(userProfile.getProfile().getBranch().getId() != itemBranch.getBranch().getId()) {
	     throw new UnauthorizedException("Branch Unauthorized!","03");
	    }
	    itemBranchPro  = new ItemBranchPromotion();
	    ItemBranchPromotionIdentity itemBranchIdentity = new ItemBranchPromotionIdentity();
	    itemBranchIdentity.setBranchPromotion(branchPromotion);
	    itemBranchIdentity.setItemBranch(itemBranch);
	    //itemBranchIdentity.setItemType(itemBranch.getItem().getItemType());
	    itemBranchPro.setItemBranchPromotionIdentity(itemBranchIdentity);
	    list.add(itemBranchPro);
	    itemBranchPromotionRepository.save(itemBranchPro);
	  }
	   
	  return list;
	 }
}
