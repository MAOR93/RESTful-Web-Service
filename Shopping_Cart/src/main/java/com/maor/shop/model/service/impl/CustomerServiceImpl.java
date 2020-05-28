package com.maor.shop.model.service.impl;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.maor.shop.controller.CustomerController;
import com.maor.shop.model.dao.CustomerDAO;
import com.maor.shop.model.dao.OrderDAO;
import com.maor.shop.model.dto.CustomerDTO;
import com.maor.shop.model.entity.Customer;
import com.maor.shop.model.exceptionhandling.CustomerNotFoundException;
import com.maor.shop.model.factory.CustomerDTOAssembler;
import com.maor.shop.model.service.CustomerService;
import com.maor.shop.model.service.mapper.ModelMapper;
import com.maor.shop.model.vo.Weather;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerDAO customerRepository;
	@Autowired
	OrderDAO orderRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private CustomerDTOAssembler customerDTOAssembler;
	@Autowired
	private RestTemplate restTemplate;

	public ResponseEntity<CollectionModel<EntityModel<CustomerDTO>>> getAllCustomers() {
		List<CustomerDTO> customers = modelMapper.convertCustomersToDTOs(customerRepository.findAll());
		return assemblerListOfCustomersDTO(customers);
	}

	public ResponseEntity<EntityModel<CustomerDTO>> getCustomerById(Long id) {
		CustomerDTO customerDTO = new CustomerDTO(
				customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id)));
		return assemblerCustomerDTO(customerDTO);
	}

	public ResponseEntity<Weather> getCustomerWeather(Long id) {
		Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
		String url = "http://api.openweathermap.org/data/2.5/weather?q=" + customer.getCountry() + ","
				+ customer.getCity() + "&appid=1c7aa06ddf230556d1e4f49f87a0f6fd";
		Weather weather = restTemplate.getForObject(url, Weather.class);
		return new ResponseEntity<Weather>(weather, HttpStatus.OK);
	}

	public ResponseEntity<?> deleteCustomer(Long id) {
		customerRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	public ResponseEntity<EntityModel<CustomerDTO>> addNewCustomer(Customer customer) {
		CustomerDTO customerDTO = new CustomerDTO(customerRepository.save(customer));
		return assemblerCustomerDTO(customerDTO);
	}

	public ResponseEntity<?> updateCustomer(Customer newCustomer, Long id) {
		Customer customer = customerRepository.findById(id).map(new Function<Customer, Customer>() {
			@Override
			public Customer apply(Customer customer) {
				customer.setAddress(newCustomer.getAddress());
				customer.setEmail(newCustomer.getEmail());
				customer.setName(customer.getName());
				return customerRepository.save(customer);
			}
		}).orElseGet(() -> {
			newCustomer.setId(id);
			return customerRepository.save(newCustomer);
		});

		CustomerDTO customerDTO = new CustomerDTO(customer);
		return assemblerCustomerDTO(customerDTO);
	}

	private ResponseEntity<CollectionModel<EntityModel<CustomerDTO>>> assemblerListOfCustomersDTO(
			List<CustomerDTO> customersDTO) {

		List<EntityModel<CustomerDTO>> customersEntityModel = new ArrayList<>();
		for (CustomerDTO customerDTO : customersDTO) {
			EntityModel<CustomerDTO> customerEntityModel = customerDTOAssembler.toModel(customerDTO);
			customersEntityModel.add(customerEntityModel);
		}
		return ResponseEntity.ok(new CollectionModel<>(customersEntityModel,

				linkTo(methodOn(CustomerController.class).getAllCustomers()).withSelfRel()));
	}

	private ResponseEntity<EntityModel<CustomerDTO>> assemblerCustomerDTO(CustomerDTO customerDTO) {
		return ResponseEntity.ok(customerDTOAssembler.toModel(customerDTO));
	}

}
