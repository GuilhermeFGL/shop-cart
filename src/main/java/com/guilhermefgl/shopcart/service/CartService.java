package com.guilhermefgl.shopcart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;

import com.guilhermefgl.shopcart.model.Cart;
import com.guilhermefgl.shopcart.repository.CartRepository;
import com.guilhermefgl.shopcart.service.dao.CartDao;

@Service
public class CartService implements CartDao {

	@Autowired
	private CartRepository repository;

	@Override
	public List<Cart> findAll(String user) {
		if (user == null) {
			return null;
		}

		return repository.findAllByUser(user);
	}

	@Override
	public Optional<Cart> find(Long id, User user) {
		if (user == null) {
			return null;
		}

		return repository.findByIdAndUser(id, user.getName());
	}

	@Override
	public Optional<Cart> getOpenCart(String user) {
		return repository.findByUserAndClosed(user, false);
	}
	
	@Override
	public List<Cart> getAllCarts(String user) {
		return repository.findAllByUser(user);
	}

	@Override
	public Cart save(Cart cart) {
		return repository.save(cart);
	}

}
