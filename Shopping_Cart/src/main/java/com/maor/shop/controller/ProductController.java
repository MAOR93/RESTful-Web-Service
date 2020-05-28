package com.maor.shop.controller;

import java.net.URISyntaxException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maor.shop.model.dto.ProductDTO;
import com.maor.shop.model.entity.Product;
import com.maor.shop.model.service.ProductService;

@RestController
public class ProductController {

	@Autowired
	private ProductService service;

	@GetMapping("/products")
	public ResponseEntity<CollectionModel<EntityModel<ProductDTO>>> getAllProducts(
			@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate startTime,
			@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate endTime) {

		return service.getAllProducts(startTime, endTime);
	}

	@GetMapping("/products/{id}")
	public ResponseEntity<EntityModel<ProductDTO>> getProductByID(@PathVariable Long id) {
		return service.getProductById(id);
	}

	@GetMapping("/orders/{id}/products")
	public ResponseEntity<CollectionModel<EntityModel<ProductDTO>>> getProductsByOrder(@PathVariable Long id,
			@RequestParam(required = false) String price) {
		return service.getProductsByOrder(id, price);
	}

	@DeleteMapping("/products/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
		return service.deleteProduct(id);
	}

	@PostMapping("/products")
	public ResponseEntity<EntityModel<ProductDTO>> addNewProduct(@RequestBody Product product) {
		return service.addNewProduct(product);
	}

	@PutMapping("/products/{id}")
	public ResponseEntity<?> updateProduct(@RequestBody Product newProduct, @PathVariable Long id)
			throws URISyntaxException {
		return service.updateProduct(newProduct, id);
	}

}
