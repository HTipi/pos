package com.spring.miniposbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalErrorException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private String errorCode;
	
	public InternalErrorException(String message) {
		super(message);
	}
	public InternalErrorException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
	public InternalErrorException(String message, Throwable cause) {
		super(message, cause);
	}

}
