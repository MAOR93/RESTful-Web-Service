package com.maor.shop.model.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class OrderNotFoundException extends RuntimeException {
	@ControllerAdvice
	static class OrderNotFoundHandler {
		@ResponseBody // this advice is rendered straight into the response body.
		@ExceptionHandler(OrderNotFoundException.class)
		// configures the advice to only respond if an ProductNotFoundException
		@ResponseStatus(HttpStatus.NOT_FOUND)
		String OrderNotFoundHandler(OrderNotFoundException ex) {
			return ex.getMessage();
		}
	}

	public OrderNotFoundException(Long id) {
		super("Order " + id + " was not found");
	}
}