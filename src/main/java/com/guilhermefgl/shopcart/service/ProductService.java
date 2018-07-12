package com.guilhermefgl.shopcart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guilhermefgl.shopcart.model.Product;
import com.guilhermefgl.shopcart.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	
	public List<Product> findAll() {
		return repository.findAll();
	}
	
	public Optional<Product> findOne(Long id) {
		return repository.findById(id);
	}
	
	public Product save(Product product) {
		return repository.save(product);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
}
