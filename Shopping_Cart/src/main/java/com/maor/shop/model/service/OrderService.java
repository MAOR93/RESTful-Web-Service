package com.maor.shop.model.service;

import java.time.LocalDate;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import com.maor.shop.model.dto.OrderDTO;
import com.maor.shop.model.entity.Order;

public interface OrderService {

	public ResponseEntity<CollectionModel<EntityModel<OrderDTO>>> getAllOrders(LocalDate startTime, LocalDate endTime);

	public ResponseEntity<EntityModel<OrderDTO>> getOrderById(Long id);

	public ResponseEntity<CollectionModel<EntityModel<OrderDTO>>> getOrdersByCustomer(Long id);

	public ResponseEntity<CollectionModel<EntityModel<OrderDTO>>> getOrdersByProduct(Long id);
	
	public ResponseEntity<CollectionModel<EntityModel<OrderDTO>>> getOrdersOfProductWithLowerGivenPrice(String limitPrice);

	public ResponseEntity<?> deleteOrder(Long id);

	public ResponseEntity<EntityModel<OrderDTO>> addNewOrder(Order order);

	public ResponseEntity<?> updateOrder(Order newOrder, Long id);

}
