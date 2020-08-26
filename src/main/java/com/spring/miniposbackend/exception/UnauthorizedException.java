package com.spring.miniposbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	private String errorCode;

	public UnauthorizedException(String message) {
		super(message);
	}
	public UnauthorizedException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
	public UnauthorizedException(String message, Throwable cause) {
		super(message, cause);
	
	}
	public String getErrorCode() {
        return errorCode;
    }
}
