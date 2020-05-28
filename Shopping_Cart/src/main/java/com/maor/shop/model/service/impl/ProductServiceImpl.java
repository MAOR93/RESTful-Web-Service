package com.maor.shop.model.service.impl;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.maor.shop.controller.ProductController;
import com.maor.shop.model.dao.OrderDAO;
import com.maor.shop.model.dao.ProductDAO;
import com.maor.shop.model.dto.ProductDTO;
import com.maor.shop.model.entity.Order;
import com.maor.shop.model.entity.Product;
import com.maor.shop.model.exceptionhandling.OrderNotFoundException;
import com.maor.shop.model.exceptionhandling.ProductNotFoundException;
import com.maor.shop.model.factory.ProductDTOAssembler;
import com.maor.shop.model.service.ProductService;
import com.maor.shop.model.service.mapper.ModelMapper;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDAO productRepository;
	@Autowired
	private OrderDAO orderRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private ProductDTOAssembler productDTOAssembler;

	public ResponseEntity<CollectionModel<EntityModel<ProductDTO>>> getAllProducts(LocalDate startTime,
			LocalDate endTime) {
		List<ProductDTO> productsDTO;
		if (startTime == null && endTime == null) {
			productsDTO = getAllProducts();
		} else {
			productsDTO = modelMapper
					.convertProductsToDTOs(productRepository.findAllProductsWithDateRange(startTime, endTime));
		}

		return assemblerListOfProductsDTO(productsDTO);
	}

	public ResponseEntity<EntityModel<ProductDTO>> getProductById(Long id) {
		ProductDTO productDTO = new ProductDTO(
				productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id)));

		return assemblerProductDTO(productDTO);
	}

	public ResponseEntity<CollectionModel<EntityModel<ProductDTO>>> getProductsByOrder(Long id, String price) {
		List<ProductDTO> productsDTO;

		if (price == null) {
			productsDTO = getProductsByOrder(id);
		} else {
			Double orderPrice = Double.valueOf(price);
			productsDTO = modelMapper
					.convertProductsToDTOs(productRepository.findAllProductsOfOrderWithLowerPrice(id, orderPrice));
		}

		return assemblerListOfProductsDTO(productsDTO);
	}

	public ResponseEntity<?> deleteProduct(Long id) {
		productRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	public ResponseEntity<EntityModel<ProductDTO>> addNewProduct(Product product) {
		ProductDTO productDTO = new ProductDTO(productRepository.save(product));
		return assemblerProductDTO(productDTO);
	}

	public ResponseEntity<?> updateProduct(Product newProduct, Long id) {
		Product product = productRepository.findById(id).map(new Function<Product, Product>() {
			@Override
			public Product apply(Product product) {
				product.setName(newProduct.getName());
				product.setDescription(newProduct.getDescription());
				product.setPrice(newProduct.getPrice());
				return productRepository.save(product);
			}
		}).orElseGet(() -> {
			newProduct.setId(id);
			return productRepository.save(newProduct);
		});

		ProductDTO productDTO = new ProductDTO(product);
		return assemblerProductDTO(productDTO);
	}

	private List<ProductDTO> getProductsByOrder(Long id) {
		Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
		return modelMapper.convertProductsToDTOs(productRepository.findByOrders(order));
	}

	private List<ProductDTO> getAllProducts() {
		return modelMapper.convertProductsToDTOs(productRepository.findAll());
	}

	private ResponseEntity<CollectionModel<EntityModel<ProductDTO>>> assemblerListOfProductsDTO(
			List<ProductDTO> productsDTO) {

		List<EntityModel<ProductDTO>> productsEntityModel = new ArrayList<>();
		for (ProductDTO productDTO : productsDTO) {
			EntityModel<ProductDTO> productEntityModel = productDTOAssembler.toModel(productDTO);
			productsEntityModel.add(productEntityModel);
		}
		return ResponseEntity.ok(new CollectionModel<>(productsEntityModel,

				linkTo(methodOn(ProductController.class).getAllProducts(null, null)).withSelfRel()));
	}

	private ResponseEntity<EntityModel<ProductDTO>> assemblerProductDTO(ProductDTO productDTO) {
		return ResponseEntity.ok(productDTOAssembler.toModel(productDTO));
	}

}
