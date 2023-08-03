package com.spring.miniposbackend.modelview.dashboard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemTypeSummaryChart {

	private Integer itemTypeId;
	private String itemTypeName;
	private String itemTypeKh;
	private Double saleAmt;
	private Double disAmt;
	private Integer saleItem;
	private Double serviceCharge;

	public ItemTypeSummaryChart() {
		this.itemTypeId=null;
		this.itemTypeName=null;
		this.itemTypeKh=null;
		this.saleAmt=null;
		this.disAmt= null;
		this.saleItem=null;
		this.serviceCharge=null;
	}
	
	public ItemTypeSummaryChart(Integer itemTypeId, String itemTypeName, String itemTypeKh,Double saleAmt,Double disAmt,Integer saleItem,Double serviceCharge) {
		this.itemTypeId = itemTypeId;
		this.itemTypeName = itemTypeName;
		this.itemTypeKh = itemTypeKh;
		this.saleAmt = saleAmt;
		this.disAmt = disAmt;
		this.saleItem = saleItem;
		this.serviceCharge = serviceCharge;
	}
}
