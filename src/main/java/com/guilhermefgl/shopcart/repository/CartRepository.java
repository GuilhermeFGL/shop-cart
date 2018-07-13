package com.guilhermefgl.shopcart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guilhermefgl.shopcart.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
	
	List<Cart> findAllByUser(String user);
	
	Optional<Cart> findByIdAndUser(Long id, String user);
	
	Optional<Cart> findByUserAndClosed(String user, Boolean closed);

}
