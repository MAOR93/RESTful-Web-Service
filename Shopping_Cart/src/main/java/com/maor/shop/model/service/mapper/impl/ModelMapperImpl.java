package com.maor.shop.model.service.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.maor.shop.model.dto.CustomerDTO;
import com.maor.shop.model.dto.OrderDTO;
import com.maor.shop.model.dto.ProductDTO;
import com.maor.shop.model.entity.Customer;
import com.maor.shop.model.entity.Order;
import com.maor.shop.model.entity.Product;
import com.maor.shop.model.service.mapper.ModelMapper;

@Service
public class ModelMapperImpl implements ModelMapper {

	public List<CustomerDTO> convertCustomersToDTOs(List<Customer> customers) {
		List<CustomerDTO> customersDTO = new ArrayList<CustomerDTO>();

		for (Customer customer : customers) {
			customersDTO.add(new CustomerDTO(customer));
		}

		return customersDTO;
	}

	public List<ProductDTO> convertProductsToDTOs(List<Product> products) {
		List<ProductDTO> productsDTO = new ArrayList<ProductDTO>();

		for (Product product : products) {
			productsDTO.add(new ProductDTO(product));
		}

		return productsDTO;
	}

	public List<OrderDTO> convertOrdersToDTOs(List<Order> orders) {
		List<OrderDTO> ordersDTO = new ArrayList<OrderDTO>();

		for (Order order : orders) {
			ordersDTO.add(new OrderDTO(order));
		}

		return ordersDTO;
	}

}
