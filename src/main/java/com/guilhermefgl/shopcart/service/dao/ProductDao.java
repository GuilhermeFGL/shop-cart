package com.guilhermefgl.shopcart.service.dao;

import java.util.List;
import java.util.Optional;

import com.guilhermefgl.shopcart.model.Product;

public interface ProductDao {
	
	List<Product> findAll();
	
	Optional<Product> findOne(Long id);

	Product save(Product product);

	void delete(Long id);
}
