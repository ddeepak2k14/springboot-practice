package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ErrorInfoDTO;
import com.example.demo.exception.ResourceNotFoundException;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
	
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(NullPointerException.class)
	public ErrorInfoDTO handleSQLException(NullPointerException ex) {
		ErrorInfoDTO error = new ErrorInfoDTO();
		error.setMessage(ex.getLocalizedMessage());
		error.setReason("Null Pointer");
		error.setStatusCode("500");
		return error;
	}
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(ResourceNotFoundException.class)
	public ErrorInfoDTO handleSQLException(ResourceNotFoundException ex) {
		ErrorInfoDTO error = new ErrorInfoDTO();
		error.setMessage(ex.getLocalizedMessage());
		error.setReason("Not Found");
		error.setStatusCode("404");
		return error;
	}

}
