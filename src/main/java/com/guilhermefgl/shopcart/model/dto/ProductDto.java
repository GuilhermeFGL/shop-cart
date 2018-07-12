package com.guilhermefgl.shopcart.model.dto;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.guilhermefgl.shopcart.model.Product;
import com.guilhermefgl.shopcart.service.dao.ProductDao;

public class ProductDto {
	
	private Long id;
	private String name;
	private Double price;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@NotEmpty(message = "name cannot be empty")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message = "price cannot be empty")
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Product toModel(ProductDao productService, BindingResult result) {
		Product product = new Product();

		if (getId() != null) {
			Optional<Product> produtoOpt = productService.findOne(getId());
			if (produtoOpt.isPresent()) {
				product = produtoOpt.get();
			} else {
				result.addError(new ObjectError("product", "product not found"));
			}
		}
		
		product.setName(getName());
		product.setPrice(getPrice());

		return product;
	}
	
	public ProductDto createProdutoDTO(Product product) {
		setId(product.getId());
		setName(product.getName());
		setPrice(product.getPrice());
		return this;
	}

}
