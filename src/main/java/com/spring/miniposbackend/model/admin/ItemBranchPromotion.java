package com.spring.miniposbackend.model.admin;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Item_branch_promotions")
@Getter
@Setter
public class ItemBranchPromotion {

	@EmbeddedId
	@JsonIgnore
	private ItemBranchPromotionIdentity itemBranchPromotionIdentity;

	@JsonIgnore
	public ItemBranch getItemBranch() {
		return itemBranchPromotionIdentity.getItemBranch();
	}

	@JsonIgnore
	public BranchPromotion getBranchPromotion() {
		return itemBranchPromotionIdentity.getBranchPromotion();
	}

	public Integer getBranchPromotionId() {
		return itemBranchPromotionIdentity.getBranchPromotion().getId();
	}

	public String getPromotionCode() {
		return itemBranchPromotionIdentity.getBranchPromotion().getCode();
	}

	public Short getPromotionDiscount() {
		return itemBranchPromotionIdentity.getBranchPromotion().getDiscount();
	}

	public String getPromotionName() {
		return itemBranchPromotionIdentity.getBranchPromotion().getName();
	}

	public String getNameKh() {
		return itemBranchPromotionIdentity.getBranchPromotion().getNameKh();
	}

	public boolean getEnable() {
		return itemBranchPromotionIdentity.getBranchPromotion().isEnable();
	}

//	@JsonIgnore
//	public ItemType getItemType() {
//		return itemBranchPromotionIdentity.getItemType();
//	}

	public Long getItemBranchId() {
		return itemBranchPromotionIdentity.getItemBranch().getId();
	}

//	public Integer getItemTypeId() {
//		return itemBranchPromotionIdentity.getItemType().getId();
//	}

}