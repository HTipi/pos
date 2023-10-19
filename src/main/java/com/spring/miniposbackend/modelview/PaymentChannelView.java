package com.spring.miniposbackend.modelview;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentChannelView {

	private String paymentChannel;
	private int paymentChannelId;
	private double percentage;
	private boolean show;
	private boolean enable;
	private int sort;
}
