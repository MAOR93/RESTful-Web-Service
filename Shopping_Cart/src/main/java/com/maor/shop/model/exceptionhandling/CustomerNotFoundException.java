package com.maor.shop.model.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CustomerNotFoundException extends RuntimeException {
	@ControllerAdvice
	static class CustomerNotFoundHandler {
		@ResponseBody // this advice is rendered straight into the response body.
		@ExceptionHandler(CustomerNotFoundException.class)
		// configures the advice to only respond if an ProductNotFoundException
		@ResponseStatus(HttpStatus.NOT_FOUND)
		String CustomerNotFoundHandler(CustomerNotFoundException ex) {
			return ex.getMessage();
		}
	}

	public CustomerNotFoundException(Long id) {
		super("Customer " + id + " was not found");
	}
}