package com.maor.shop.model.service.impl;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.maor.shop.controller.OrderController;
import com.maor.shop.model.dao.CustomerDAO;
import com.maor.shop.model.dao.OrderDAO;
import com.maor.shop.model.dao.ProductDAO;
import com.maor.shop.model.dto.OrderDTO;
import com.maor.shop.model.entity.Customer;
import com.maor.shop.model.entity.Order;
import com.maor.shop.model.entity.Product;
import com.maor.shop.model.exceptionhandling.CustomerNotFoundException;
import com.maor.shop.model.exceptionhandling.OrderNotFoundException;
import com.maor.shop.model.exceptionhandling.ProductNotFoundException;
import com.maor.shop.model.factory.OrderDTOAssembler;
import com.maor.shop.model.service.OrderService;
import com.maor.shop.model.service.mapper.ModelMapper;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDAO orderRepository;
	@Autowired
	private ProductDAO productRepository;
	@Autowired
	CustomerDAO customerRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private OrderDTOAssembler orderDTOAssembler;

	public ResponseEntity<CollectionModel<EntityModel<OrderDTO>>> getAllOrders(LocalDate startTime, LocalDate endTime) {
		List<OrderDTO> ordersDTO;

		if (startTime == null && endTime == null) {
			ordersDTO = getAllOrders();
		} else {
			ordersDTO = modelMapper.convertOrdersToDTOs(orderRepository.findAllOrdersWithDateRange(startTime, endTime));
		}

		return assemblerListOfOrdersDTO(ordersDTO);
	}

	public ResponseEntity<EntityModel<OrderDTO>> getOrderById(Long id) {
		OrderDTO orderDTO = new OrderDTO(
				orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id)));
		return assemblerOrderDTO(orderDTO);
	}

	public ResponseEntity<CollectionModel<EntityModel<OrderDTO>>> getOrdersByProduct(Long id) {
		List<OrderDTO> ordersDTO = getOrdersDTOsByProduct(id);

		return assemblerListOfOrdersDTO(ordersDTO);
	}
	
	public ResponseEntity<CollectionModel<EntityModel<OrderDTO>>> getOrdersOfProductWithLowerGivenPrice(String limitPrice) {
		List<OrderDTO> ordersDTO;
		Double productPrice = Double.valueOf(limitPrice);
		ordersDTO = modelMapper
				.convertOrdersToDTOs(orderRepository.findAllOrdersOfProductWithLowerGivenPrice(productPrice));
		
		return assemblerListOfOrdersDTO(ordersDTO);
	}

	public ResponseEntity<EntityModel<OrderDTO>> addNewOrder(Order order) {
		
		Double totalPrice = calculateTotalOrderPrice(order);
		order.setPrice(totalPrice);
		OrderDTO orderDTO = new OrderDTO(orderRepository.save(order));
		return assemblerOrderDTO(orderDTO);
	}

	public ResponseEntity<?> deleteOrder(Long id) {
		orderRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	public ResponseEntity<?> updateOrder(Order newOrder, Long id) {
		Order order = orderRepository.findById(id).map(new Function<Order, Order>() {
			@Override
			public Order apply(Order order) {
				order.setTitle(newOrder.getTitle());
				order.setPrice(newOrder.getPrice());
				return orderRepository.save(order);
			}
		}).orElseGet(() -> {
			newOrder.setId(id);
			return orderRepository.save(newOrder);
		});

		OrderDTO orderDTO = new OrderDTO(order);
		return assemblerOrderDTO(orderDTO);
	}

	public ResponseEntity<CollectionModel<EntityModel<OrderDTO>>> getOrdersByCustomer(Long id) {
		Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
		List<OrderDTO> ordersDTO = modelMapper.convertOrdersToDTOs(orderRepository.findByCustomer(customer));
		return assemblerListOfOrdersDTO(ordersDTO);
	}

	private List<OrderDTO> getOrdersDTOsByProduct(Long id) {
		Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
		return modelMapper.convertOrdersToDTOs(orderRepository.findByProducts(product));
	}

	private List<OrderDTO> getAllOrders() {
		return modelMapper.convertOrdersToDTOs(orderRepository.findAll());
	}

	private ResponseEntity<CollectionModel<EntityModel<OrderDTO>>> assemblerListOfOrdersDTO(List<OrderDTO> ordersDTO) {

		List<EntityModel<OrderDTO>> ordersEntityModel = new ArrayList<>();
		for (OrderDTO orderDTO : ordersDTO) {
			EntityModel<OrderDTO> orderEntityModel = orderDTOAssembler.toModel(orderDTO);
			ordersEntityModel.add(orderEntityModel);
		}
		return ResponseEntity.ok(new CollectionModel<>(ordersEntityModel,

				linkTo(methodOn(OrderController.class).getAllOrders(null, null)).withSelfRel()));
	}

	private ResponseEntity<EntityModel<OrderDTO>> assemblerOrderDTO(OrderDTO orderDTO) {
		return ResponseEntity.ok(orderDTOAssembler.toModel(orderDTO));
	}
	
	private Double calculateTotalOrderPrice(Order order) {
		List<Product> products = order.getProducts();
		Double totalPrice = 0.0;
		for (Product product: products) {
			totalPrice+= product.getPrice();
		}
		
		return totalPrice;
	}

}
