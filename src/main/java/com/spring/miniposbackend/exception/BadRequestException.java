package com.spring.miniposbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private Integer errorCode;

	public BadRequestException(String message) {
		super(message);
		this.errorCode = 400;
	}

	public BadRequestException(String message, Throwable cause) {
		super(message, cause);
		this.errorCode = 400;
	}

	public Integer getErrorCode() {
		return errorCode;
	}
}
