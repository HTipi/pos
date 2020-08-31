package com.spring.miniposbackend.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SuccessResponse  {

	private String code;
	private String message;
	private Object data;
		
	public SuccessResponse(String code, String message,Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
}