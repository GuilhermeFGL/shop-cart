package com.guilhermefgl.shopcart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guilhermefgl.shopcart.model.Product;
import com.guilhermefgl.shopcart.repository.ProductRepository;
import com.guilhermefgl.shopcart.service.dao.ProductDao;

@Service
public class ProductService implements ProductDao {

	@Autowired
	private ProductRepository repository;
	
	@Override
	public List<Product> findAll() {
		return repository.findAll();
	}
	
	@Override
	public Optional<Product> findOne(Long id) {
		return repository.findById(id);
	}
	
	@Override
	public Product save(Product product) {
		return repository.save(product);
	}
	
	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}
}
