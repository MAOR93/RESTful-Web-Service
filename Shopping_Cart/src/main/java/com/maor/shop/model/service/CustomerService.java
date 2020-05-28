package com.maor.shop.model.service;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import com.maor.shop.model.dto.CustomerDTO;
import com.maor.shop.model.entity.Customer;
import com.maor.shop.model.vo.Weather;

public interface CustomerService {

	public ResponseEntity<CollectionModel<EntityModel<CustomerDTO>>> getAllCustomers();

	public ResponseEntity<EntityModel<CustomerDTO>> getCustomerById(Long id);

	public ResponseEntity<Weather> getCustomerWeather(Long id);

	public ResponseEntity<?> deleteCustomer(Long id);

	public ResponseEntity<EntityModel<CustomerDTO>> addNewCustomer(Customer customer);

	public ResponseEntity<?> updateCustomer(Customer newCustomer, Long id);

}
