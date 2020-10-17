package com.spring.miniposbackend.modelview.dashboard;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockDetail {
	private Integer stockId;
	private String itemName;
	private String itemNameKh;
	private String stockDesc;
	private Double stockPrice;
	private Integer stockItem;
	private Date valueDate;
	private String branchKh;
	private Long stockBalance;
	private String stockCode;

	public StockDetail() {
		stockId = null;
		itemName = null;
		itemNameKh = null;
		stockItem = null;
		stockDesc = null;
		stockPrice = null;
		valueDate = null;
		branchKh = null;
		stockBalance = null;
		stockCode = null;
		
	
	}

	public StockDetail(Integer stockId, String itemName, String itemNameKh,String stockDesc,Double stockPrice,Integer stockItem,Date valueDate,String branchKh,Long stockBalance,String stockCode) {
		this.stockId = stockId;
		this.itemName = itemName;
		this.itemNameKh = itemNameKh;
		this.stockItem = stockItem;
		this.stockDesc = stockDesc;
		this.stockPrice = stockPrice;
		this.valueDate = valueDate;
		this.branchKh = branchKh;
		this.stockBalance = stockBalance;
		this.stockCode = stockCode;
		
	}
}
