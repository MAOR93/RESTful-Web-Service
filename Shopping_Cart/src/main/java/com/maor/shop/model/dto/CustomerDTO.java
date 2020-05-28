package com.maor.shop.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.maor.shop.model.entity.Customer;

import lombok.Value;

@Value
@JsonPropertyOrder({ "name", "email", "address", "country", "city" })
public class CustomerDTO {

	@JsonIgnore
	private Customer customer;

	public String getName() {
		return this.customer.getName();
	}

	public String getEmail() {
		return this.customer.getEmail();
	}

	public String getAddress() {
		return this.customer.getAddress();
	}

	public String getCountry() {
		return this.customer.getCountry();
	}

	public String getCity() {
		return this.customer.getCity();
	}

}
