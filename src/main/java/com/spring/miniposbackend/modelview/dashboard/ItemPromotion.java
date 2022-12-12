package com.spring.miniposbackend.modelview.dashboard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPromotion {
	private Integer branchPromotionId;
	private String promotionCode;
	private Short promotionDiscount;
	private String promotionName;

	public ItemPromotion() {
		this.branchPromotionId = null;
		this.promotionCode = null;
		this.promotionDiscount = null;
		this.promotionName = null;
	}

	public ItemPromotion(Integer branchPromotionId, String promotionCode, Short promotionDiscount, String  promotionName) {
		this.branchPromotionId = null;
		this.promotionCode = null;
		this.promotionDiscount = null;
		this.promotionName = null;
	}
}
