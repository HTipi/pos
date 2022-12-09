package com.spring.miniposbackend.model.admin;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class ItemBranchPromotionIdentity implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "item_branch_id", nullable = false)
	@JsonIgnore
	private ItemBranch itemBranch;

	@ManyToOne
	@JoinColumn(name = "branch_promotion_id", nullable = false)
	@JsonIgnore
	private BranchPromotion branchPromotion;

	public ItemBranchPromotionIdentity() {
		itemBranch = null;
		branchPromotion = null;
	}

	public ItemBranchPromotionIdentity(ItemBranch itemBranch, BranchPromotion branchPromotion) {
		this.itemBranch = itemBranch;
		this.branchPromotion = branchPromotion;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ItemBranchPromotionIdentity)) return false;
        ItemBranchPromotionIdentity that = (ItemBranchPromotionIdentity) obj;
        return Objects.equals(getItemBranch(), that.getItemBranch()) &&
                Objects.equals(getBranchPromotion(), that.getBranchPromotion());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getItemBranch(), getBranchPromotion());
    }
}
