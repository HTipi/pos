//package com.spring.miniposbackend.exception;
//
//import java.util.Date;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//@ControllerAdvice
//@RestController
//public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
//
//	@ExceptionHandler(ResourceNotFoundException.class)
//	public final ResponseEntity<ExceptionResponse> handleException(ResourceNotFoundException ex, WebRequest request){
//		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getErrorCode(),ex.getMessage());
//		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND);
//	}
//	
//	@ExceptionHandler(BadRequestException.class)
//	public final ResponseEntity<ExceptionResponse> handleException(BadRequestException ex, WebRequest request){
//		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getErrorCode(),ex.getMessage());
//		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
//	}
//	@ExceptionHandler(UnauthorizedException.class)
//	public final ResponseEntity<ExceptionResponse> handleException(UnauthorizedException ex, WebRequest request){
//		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getErrorCode(),ex.getMessage());
//		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.UNAUTHORIZED);
//	}
//	
//	@ExceptionHandler(ConflictException.class)
//	public final ResponseEntity<ExceptionResponse> handleException(ConflictException ex, WebRequest request){
//		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getErrorCode(),ex.getMessage());
//		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.CONFLICT);
//	}
//	
//	@ExceptionHandler(UnprocessableEntityException.class)
//	public final ResponseEntity<ExceptionResponse> handleException(UnprocessableEntityException ex, WebRequest request){
//		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getErrorCode(),ex.getMessage());
//		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.UNPROCESSABLE_ENTITY);
//	}
//	
//	@ExceptionHandler(InternalErrorException.class)
//	public final ResponseEntity<ExceptionResponse> handleException(InternalErrorException ex, WebRequest request){
//		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getErrorCode(),ex.getMessage());
//		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//	}
//	
//	
//}
