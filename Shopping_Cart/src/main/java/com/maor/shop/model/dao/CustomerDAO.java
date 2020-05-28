package com.maor.shop.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maor.shop.model.entity.Customer;

public interface CustomerDAO extends JpaRepository<Customer, Long> {

}
