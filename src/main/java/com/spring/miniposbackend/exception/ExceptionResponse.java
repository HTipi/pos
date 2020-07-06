package com.spring.miniposbackend.exception;

import java.util.Date;

import lombok.Getter;

@Getter
public class ExceptionResponse  {

	private Date timestamp;
	private Short code;
	private String message;
	private String path;
	
	public ExceptionResponse(Date timestamp, Short code, String message) {
		this.timestamp = timestamp;
		this.code = code;
		this.message = message;
	}
	
}
