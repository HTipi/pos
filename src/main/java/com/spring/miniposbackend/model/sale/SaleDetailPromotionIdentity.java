package com.spring.miniposbackend.model.sale;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.admin.BranchPromotion;
import com.spring.miniposbackend.model.admin.ItemBranchPromotion;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class SaleDetailPromotionIdentity implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "sale_detail_id", nullable = false)
	@JsonIgnore
	private SaleDetail saleDetail;

	@ManyToOne
	@JoinColumn(name = "branch_promotion_id", nullable = false)
	@JsonIgnore
	private BranchPromotion branchPromotion;

	public SaleDetailPromotionIdentity() {
		saleDetail = null;
		branchPromotion = null;
	}

	public SaleDetailPromotionIdentity(SaleDetail saleDetail, BranchPromotion branchPromotion) {
		this.saleDetail = saleDetail;
		this.branchPromotion = branchPromotion;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof SaleDetailPromotionIdentity)) return false;
        SaleDetailPromotionIdentity that = (SaleDetailPromotionIdentity) obj;
        return Objects.equals(getSaleDetail(), that.getSaleDetail()) &&
                Objects.equals(getBranchPromotion(), that.getBranchPromotion());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getSaleDetail(), getBranchPromotion());
    }
}
