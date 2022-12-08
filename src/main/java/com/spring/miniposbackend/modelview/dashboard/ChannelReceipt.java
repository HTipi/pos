package com.spring.miniposbackend.modelview.dashboard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelReceipt {

	private String channel;
	private Integer receipt;
	private Double saleAmt;
	private Double disAmt;

	public ChannelReceipt() {
		this.channel=null;
		this.receipt=null;
		this.saleAmt=null;
		this.disAmt= null;
	}
	
	public ChannelReceipt(String channel, Integer receipt,Double saleAmt,Double disAmt) {
		this.channel = channel;
		this.receipt = receipt;
		this.saleAmt = saleAmt;
		this.disAmt = disAmt;
	}
}
