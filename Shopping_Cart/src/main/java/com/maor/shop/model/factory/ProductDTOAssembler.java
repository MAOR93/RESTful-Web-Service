package com.maor.shop.model.factory;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.maor.shop.controller.ProductController;
import com.maor.shop.model.dto.ProductDTO;

@Component // Component is automatically created when the application is started
public class ProductDTOAssembler implements RepresentationModelAssembler<ProductDTO, EntityModel<ProductDTO>> {

	@Override
	public EntityModel<ProductDTO> toModel(ProductDTO productDTO) {

		return new EntityModel<>(productDTO,
				linkTo(methodOn(ProductController.class).getProductByID(productDTO.getProduct().getId())).withSelfRel(),
				linkTo(methodOn(ProductController.class).getAllProducts(null, null)).withRel("products"));
	}
}
