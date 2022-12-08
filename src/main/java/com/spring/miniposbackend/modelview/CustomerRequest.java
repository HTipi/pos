package com.spring.miniposbackend.modelview;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CustomerRequest {
	
	private String name;
	private String nameKh;
	int sexId;
	short discount;
	private String primaryPhone;
	private String secondaryPhone;

}
