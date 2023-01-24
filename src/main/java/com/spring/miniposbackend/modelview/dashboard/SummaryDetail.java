package com.spring.miniposbackend.modelview.dashboard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SummaryDetail {
	private Integer id;
	private String name;
	private Integer receipt;
	private Double total;
	private Double disAmt;

	public SummaryDetail() {
		this.id = null;
		this.name = "";
		this.receipt = null;
		this.total = null;
		this.disAmt = null;
	}

	public SummaryDetail(Integer id, String name,Integer receipt, Double total,Double disAmt) {
		this.id = id;
		this.name = name;
		this.receipt = receipt;
		this.total = total;
		this.disAmt = disAmt;
	}
}
