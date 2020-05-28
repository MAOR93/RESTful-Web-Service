package com.maor.shop.controller;

import java.net.URISyntaxException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maor.shop.model.dto.OrderDTO;
import com.maor.shop.model.entity.Order;
import com.maor.shop.model.service.OrderService;

@RestController
public class OrderController {

	@Autowired
	OrderService service;

	@GetMapping("/orders")
	public ResponseEntity<CollectionModel<EntityModel<OrderDTO>>> getAllOrders(
			@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate startTime,
			@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate endTime) {

		return service.getAllOrders(startTime, endTime);
	}

	@GetMapping("/orders/{id}")
	public ResponseEntity<EntityModel<OrderDTO>> getOrderByID(@PathVariable Long id) {
		return service.getOrderById(id);
	}

	@GetMapping("/customers/{id}/orders")
	public ResponseEntity<CollectionModel<EntityModel<OrderDTO>>> getOrdersByCustomer(@PathVariable Long id) {
		return service.getOrdersByCustomer(id);
	}

	@GetMapping("/products/{id}/orders")
	public ResponseEntity<CollectionModel<EntityModel<OrderDTO>>> getOrdersByProduct(@PathVariable Long id){
		return service.getOrdersByProduct(id);
	}
	
	@GetMapping("/products/orders")
	public ResponseEntity<CollectionModel<EntityModel<OrderDTO>>> getOrdersOfProductWithLowerGivenPrice(@RequestParam String price) {
		return service.getOrdersOfProductWithLowerGivenPrice(price);
	}

	@DeleteMapping("/orders/{id}")
	public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
		return service.deleteOrder(id);
	}

	@PostMapping("/orders")
	public ResponseEntity<EntityModel<OrderDTO>> addNewOrder(@RequestBody Order order) {
		return service.addNewOrder(order);
	}

	@PutMapping("/orders/{id}")
	public ResponseEntity<?> updateOrder(@RequestBody Order newOrder, @PathVariable Long id) throws URISyntaxException {
		return service.updateOrder(newOrder, id);
	}
}
