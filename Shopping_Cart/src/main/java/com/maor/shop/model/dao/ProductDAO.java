package com.maor.shop.model.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.maor.shop.model.entity.Order;
import com.maor.shop.model.entity.Product;

public interface ProductDAO extends JpaRepository<Product, Long> {

	List<Product> findByOrders(Order order);

	@Query(nativeQuery = true, value = "select * from products where products.creation_date between :startTime and :endTime")
	List<Product> findAllProductsWithDateRange(@Param("startTime") LocalDate startTime,
			@Param("endTime") LocalDate endTime);

	@Query(nativeQuery = true, value = "select products.* from products INNER JOIN orders ON :orderId = orders.id AND orders.price < :orderPrice")
	List<Product> findAllProductsOfOrderWithLowerPrice(@Param("orderId") Long id,
			@Param("orderPrice") Double orderPrice);
}
