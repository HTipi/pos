package com.spring.miniposbackend.modelview.dashboard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemTypeSummaryDetail {

	private Integer itemTypeId;
	private String itemTypeName;
	private String itemTypeKh;
	private Double saleAmt;
	private Integer saleItem;

	public ItemTypeSummaryDetail() {
		this.itemTypeId=null;
		this.itemTypeName=null;
		this.itemTypeKh=null;
		this.saleAmt=null;
		this.saleItem=null;
	}
	
	public ItemTypeSummaryDetail(Integer itemTypeId, String itemTypeName, String itemTypeKh,Double saleAmt,Integer saleItem) {
		this.itemTypeId = itemTypeId;
		this.itemTypeName = itemTypeName;
		this.itemTypeKh = itemTypeKh;
		this.saleAmt = saleAmt;
		this.saleItem = saleItem;
	}
}
