package com.maor.shop.model.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue
	private Long id;
	private String title;
	private LocalDate purchaseDate;
	private Double price;
	@ManyToOne
	private Customer customer;

	@ManyToMany
	@JoinTable(name = "orders_products", joinColumns = { @JoinColumn(name = "orders_id") }, inverseJoinColumns = {
			@JoinColumn(name = "products_id") })
	private List<Product> products;

}
