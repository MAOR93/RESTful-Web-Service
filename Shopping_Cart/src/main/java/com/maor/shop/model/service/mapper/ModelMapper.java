package com.maor.shop.model.service.mapper;

import java.util.List;

import com.maor.shop.model.dto.CustomerDTO;
import com.maor.shop.model.dto.OrderDTO;
import com.maor.shop.model.dto.ProductDTO;
import com.maor.shop.model.entity.Customer;
import com.maor.shop.model.entity.Order;
import com.maor.shop.model.entity.Product;

public interface ModelMapper {
	public List<CustomerDTO> convertCustomersToDTOs(List<Customer> customers);

	public List<ProductDTO> convertProductsToDTOs(List<Product> products);

	public List<OrderDTO> convertOrdersToDTOs(List<Order> orders);
}
