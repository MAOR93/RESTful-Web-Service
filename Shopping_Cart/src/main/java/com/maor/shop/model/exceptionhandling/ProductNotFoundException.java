package com.maor.shop.model.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ProductNotFoundException extends RuntimeException {
	@ControllerAdvice
	static class ProductNotFoundHandler {
		@ResponseBody // this advice is rendered straight into the response body.
		@ExceptionHandler(ProductNotFoundException.class)
		// configures the advice to only respond if an ProductNotFoundException
		@ResponseStatus(HttpStatus.NOT_FOUND)
		String ProductNotFoundHandler(ProductNotFoundException ex) {
			return ex.getMessage();
		}
	}

	public ProductNotFoundException(Long id) {
		super("Product " + id + " was not found");
	}
}