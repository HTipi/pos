package com.spring.miniposbackend.service.admin;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.miniposbackend.exception.BadRequestException;
import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.model.admin.BranchPromotion;
import com.spring.miniposbackend.modelview.BranchPromotionView;
import com.spring.miniposbackend.repository.admin.BranchPromotionRepository;
import com.spring.miniposbackend.repository.admin.BranchRepository;
import com.spring.miniposbackend.repository.admin.ItemBranchPromotionRepository;
import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class BranchPromotionService {


	@Autowired
	private UserProfileUtil userProfile;
	@Autowired
	private BranchPromotionRepository branchPromotionRepository;
    @Autowired
    private ItemBranchPromotionRepository itemBranchPromotionRepository;
    
	@Transactional(readOnly=true)
	public List<BranchPromotion> showBranchPromotion() {
		return branchPromotionRepository.findByBranchId(userProfile.getProfile().getBranch().getId());
		 
	}
	
	@Transactional
	public BranchPromotion createBranchPromotion(BranchPromotionView branchProView) {
		BranchPromotion branchPromotion = new BranchPromotion();
		branchPromotion.setBranch(userProfile.getProfile().getBranch());
		branchPromotion.setCode(branchProView.getCode());
		branchPromotion.setDiscount(branchProView.getDiscount());
		branchPromotion.setName(branchProView.getName());
		branchPromotion.setNameKh(branchProView.getNameKh());
		branchPromotion.setEnable(branchProView.isEnable());
		branchPromotion.setStartDate(new Date());
		branchPromotion.setEndDate(new Date());
		branchPromotionRepository.save(branchPromotion);
		
		return branchPromotion;
	}
	
	@Transactional
	public BranchPromotion updateBranchPromotion(BranchPromotionView branchProView, Integer branchPromotionId) {
		return branchPromotionRepository.findById(branchPromotionId).map((branchPromotion) -> {
			if(userProfile.getProfile().getBranch().getId() != branchPromotion.getBranch().getId()) {
				throw new UnauthorizedException("branch is Unauthorized!");
			}
			branchPromotion.setCode(branchProView.getCode());
			branchPromotion.setDiscount(branchProView.getDiscount());
			branchPromotion.setName(branchProView.getName());
			branchPromotion.setNameKh(branchProView.getNameKh());
			branchPromotion.setEnable(branchProView.isEnable());
			branchPromotion.setStartDate(new Date());
			branchPromotion.setEndDate(new Date());
			branchPromotionRepository.save(branchPromotion);
			return branchPromotion;
		}).orElseThrow(()-> new BadRequestException("this branch Promotion does not exit","01"));
	}
	
	@Transactional
	public BranchPromotion deleteBranchPromotion(Integer branchPromotionId) {
		return branchPromotionRepository.findById(branchPromotionId).map((branchPromotion) -> {
			if(userProfile.getProfile().getBranch().getId() != branchPromotion.getBranch().getId()) {
				throw new UnauthorizedException("branch is Unauthorized!");
			}
			itemBranchPromotionRepository.deleteByBranchPromotion(branchPromotionId);
			branchPromotion.setEnable(false);
			return branchPromotion;
		}).orElseThrow(()-> new BadRequestException("this branch Promotion does not exit","01"));
	}
}
