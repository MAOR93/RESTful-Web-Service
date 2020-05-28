package com.maor.shop.model.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.maor.shop.model.entity.Order;

import lombok.Value;

@Value
@JsonPropertyOrder({ "title", "price", "purchaseDate" })
public class OrderDTO {

	@JsonIgnore
	private Order order;

	public String getTitle() {
		return this.order.getTitle();
	}

	public LocalDate purchaseDate() {
		return this.order.getPurchaseDate();
	}

	public Double getPrice() {
		return this.order.getPrice();
	}
}