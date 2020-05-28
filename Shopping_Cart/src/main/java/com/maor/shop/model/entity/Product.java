package com.maor.shop.model.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private LocalDate creationDate;
	private Double price;
	private String description;

	@ManyToMany(mappedBy = "products")
	private List<Order> orders;
}
