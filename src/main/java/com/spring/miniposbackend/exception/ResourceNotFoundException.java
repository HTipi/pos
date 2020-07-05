package com.spring.miniposbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private Integer errorCode;
	
	public ResourceNotFoundException(String message) {
        super(message);
        this.errorCode = 404;
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = 404;
    }
    
    public Integer getErrorCode() {
        return errorCode;
    }
}