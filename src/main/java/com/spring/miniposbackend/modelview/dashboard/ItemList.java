package com.spring.miniposbackend.modelview.dashboard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemList {
	private Long itemId;
	private String itemName;
	private String itemNameKh;
	private Double saleAmt;
	private Double disAmt;
	private Integer saleItem;
	private Double total;

	public ItemList() {
		itemId = null;
		itemName = null;
		itemNameKh = null;
		saleAmt = null;
		disAmt = null;
		saleItem = null;
		total = null;
	}

	public ItemList(Long itemId, String itemName, String itemNamekh, Double saleAmt,Double disAmt, Integer saleItem,Double total) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemNameKh = itemNamekh;
		this.saleAmt = saleAmt;
		this.disAmt = disAmt;
		this.saleItem = saleItem;
		this.total = total;
	}
}
