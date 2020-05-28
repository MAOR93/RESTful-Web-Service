package com.maor.shop.model.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maor.shop.model.entity.User;

public interface UserDAO extends JpaRepository<User, Integer> {
	Optional<User> findByUserName(String userName);

}
