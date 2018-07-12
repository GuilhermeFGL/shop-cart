package com.guilhermefgl.shopcart.service.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.access.annotation.Secured;

import com.guilhermefgl.shopcart.model.Cart;

@Secured("USER")
public interface CartDao {

	List<Cart> findAll(User user);
	
	Optional<Cart> find(Long id, User user);

	Cart save(Cart cart, User user);
	
}
