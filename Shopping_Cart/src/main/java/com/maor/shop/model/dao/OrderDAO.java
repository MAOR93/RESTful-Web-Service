package com.maor.shop.model.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.maor.shop.model.entity.Customer;
import com.maor.shop.model.entity.Order;
import com.maor.shop.model.entity.Product;

public interface OrderDAO extends JpaRepository<Order, Long> {

	List<Order> findByProducts(Product product);

	List<Order> findByCustomer(Customer customer);

	@Query(nativeQuery = true, value = "select * from orders where orders.purchase_date between :startTime and :endTime")
	List<Order> findAllOrdersWithDateRange(@Param("startTime") LocalDate startTime,
			@Param("endTime") LocalDate endTime);

	// find all the orders that consist products with lower given price.
	@Query(nativeQuery = true, value = "select orders.* from orders INNER JOIN products INNER JOIN orders_products ON orders_id= orders.id AND products_id= products.id AND products.price < :limitPrice")
	List<Order> findAllOrdersOfProductWithLowerGivenPrice(@Param("limitPrice") Double limitPrice);
}
