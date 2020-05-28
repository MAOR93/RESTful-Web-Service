package com.maor.shop.model.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.maor.shop.model.entity.Product;

import lombok.Value;

@Value
@JsonPropertyOrder({ "name", "price", "creationDate", "description" })
public class ProductDTO {

	@JsonIgnore
	private Product product;

	public String getName() {
		return this.product.getName();
	}

	public Double getPrice() {
		return this.product.getPrice();
	}

	public LocalDate getcreationDate() {
		return this.product.getCreationDate();
	}

	public String getDescription() {
		return this.product.getDescription();
	}

}
