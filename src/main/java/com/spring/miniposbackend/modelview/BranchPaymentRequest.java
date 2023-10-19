package com.spring.miniposbackend.modelview;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BranchPaymentRequest {
	
	private Integer paymentChannelId;
	private double percentage;
	private Boolean show;
}
