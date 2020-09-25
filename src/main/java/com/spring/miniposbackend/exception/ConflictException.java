package com.spring.miniposbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private String errorCode;

	public ConflictException(String message) {
		super(message);
		this.errorCode = "05";
	}
	
	public ConflictException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public ConflictException(String message, Throwable cause) {
		super(message, cause);
		this.errorCode = "05";
	}
	
	public String getErrorCode() {
        return errorCode;
    }
}
