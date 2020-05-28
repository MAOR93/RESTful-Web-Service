package com.maor.shop.controller;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.maor.shop.model.dto.CustomerDTO;
import com.maor.shop.model.entity.Customer;
import com.maor.shop.model.service.CustomerService;
import com.maor.shop.model.vo.Weather;

@RestController
public class CustomerController {

	@Autowired
	CustomerService service;

	@GetMapping("/customers")
	public ResponseEntity<CollectionModel<EntityModel<CustomerDTO>>> getAllCustomers() {
		return service.getAllCustomers();
	}

	@GetMapping("/customers/{id}")
	public ResponseEntity<EntityModel<CustomerDTO>> getCustomerByID(@PathVariable Long id) {
		return service.getCustomerById(id);
	}

	@GetMapping("weather/customers/{id}")
	public ResponseEntity<Weather> getCustomerWeather(@PathVariable Long id) {
		return service.getCustomerWeather(id);
	}

	@DeleteMapping("/customers/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
		return service.deleteCustomer(id);
	}

	@PostMapping("/customers")
	public ResponseEntity<EntityModel<CustomerDTO>> addNewCustomer(@RequestBody Customer customer) {
		return service.addNewCustomer(customer);
	}

	@PutMapping("/customers/{id}")
	public ResponseEntity<?> updateCustomer(@RequestBody Customer newCustomer, @PathVariable Long id)
			throws URISyntaxException {
		return service.updateCustomer(newCustomer, id);
	}

}
