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
	

}
