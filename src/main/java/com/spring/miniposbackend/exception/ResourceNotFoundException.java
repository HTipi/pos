package com.spring.miniposbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private String errorCode;
	
	public ResourceNotFoundException(String message) {
        super(message);
        this.errorCode = "404";
    }
	
	public ResourceNotFoundException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "404";
    }
    
    public String getErrorCode() {
        return errorCode;
    }
}