package com.maor.shop.model.service;

import java.time.LocalDate;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import com.maor.shop.model.dto.ProductDTO;
import com.maor.shop.model.entity.Product;

public interface ProductService {

	public ResponseEntity<CollectionModel<EntityModel<ProductDTO>>> getAllProducts(LocalDate startTime,
			LocalDate endTime);

	public ResponseEntity<EntityModel<ProductDTO>> getProductById(Long id);

	public ResponseEntity<CollectionModel<EntityModel<ProductDTO>>> getProductsByOrder(Long id, String price);

	public ResponseEntity<?> deleteProduct(Long id);

	public ResponseEntity<EntityModel<ProductDTO>> addNewProduct(Product product);

	public ResponseEntity<?> updateProduct(Product newProduct, Long id);

}
