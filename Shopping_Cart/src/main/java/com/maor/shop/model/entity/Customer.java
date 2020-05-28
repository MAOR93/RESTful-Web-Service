package com.maor.shop.model.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "customers")
public class Customer {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String email;
	private String address;
	private String country;
	private String city;
	@OneToMany(mappedBy = "customer")
	private List<Order> orders;

}