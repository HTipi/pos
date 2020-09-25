package com.spring.miniposbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	private String errorCode;
	
	public UnprocessableEntityException(String message) {
		super(message);
		this.errorCode = "07";
	}
	
	public UnprocessableEntityException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public UnprocessableEntityException(String message, Throwable cause) {
		super(message, cause);
		this.errorCode = "07";
	}
	
	public String getErrorCode() {
        return errorCode;
    }
}
