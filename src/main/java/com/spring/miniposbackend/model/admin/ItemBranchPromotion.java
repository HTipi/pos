package com.spring.miniposbackend.model.admin;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

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
	public Promotion getPromotion() {
		return itemBranchPromotionIdentity.getPromotion();
	}

}
