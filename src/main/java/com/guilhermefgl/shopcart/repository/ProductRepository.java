package com.guilhermefgl.shopcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guilhermefgl.shopcart.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> { 
	
}
