package com.spring.miniposbackend.modelview;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BranchView {

	private String name;
	private String nameKh;
	private String address;
	private boolean enable;
	private boolean main;
	private String telephone;
	private BigDecimal pointExchange;
	private BigDecimal rewardExchange;
}
