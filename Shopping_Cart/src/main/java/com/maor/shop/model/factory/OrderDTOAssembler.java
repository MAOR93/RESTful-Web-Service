package com.maor.shop.model.factory;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.maor.shop.controller.OrderController;
import com.maor.shop.model.dto.OrderDTO;

@Component // Component is automatically created when the application is started
public class OrderDTOAssembler implements RepresentationModelAssembler<OrderDTO, EntityModel<OrderDTO>> {

	@Override
	public EntityModel<OrderDTO> toModel(OrderDTO orderDTO) {

		return new EntityModel<>(orderDTO,
				linkTo(methodOn(OrderController.class).getOrderByID(orderDTO.getOrder().getId())).withSelfRel(),
				linkTo(methodOn(OrderController.class).getAllOrders(null, null)).withRel("orders"));
	}
}
