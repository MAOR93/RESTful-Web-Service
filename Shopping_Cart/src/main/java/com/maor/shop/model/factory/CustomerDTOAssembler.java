package com.maor.shop.model.factory;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.maor.shop.controller.CustomerController;
import com.maor.shop.model.dto.CustomerDTO;

@Component // Component is automatically created when the application is started
public class CustomerDTOAssembler implements RepresentationModelAssembler<CustomerDTO, EntityModel<CustomerDTO>> {

	@Override
	public EntityModel<CustomerDTO> toModel(CustomerDTO customerDTO) {

		return new EntityModel<>(customerDTO,
				linkTo(methodOn(CustomerController.class).getCustomerByID(customerDTO.getCustomer().getId()))
						.withSelfRel(),
				linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("customers"));
	}
}
