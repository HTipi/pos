package com.spring.miniposbackend.modelview.account;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreditView {

	private String name;
	private BigDecimal price;
	private short point;
}