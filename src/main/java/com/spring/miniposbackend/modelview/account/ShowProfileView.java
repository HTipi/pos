package com.spring.miniposbackend.modelview.account;


import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Setter
@Getter
public class ShowProfileView {
	
	private byte[] image;
	private BigDecimal point;
	private BigDecimal credit;
	private String remark;
	private String name;
	private String sex;

}

