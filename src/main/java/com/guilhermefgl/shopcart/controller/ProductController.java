package com.guilhermefgl.shopcart.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.guilhermefgl.shopcart.model.Product;
import com.guilhermefgl.shopcart.model.dto.ProductDto;
import com.guilhermefgl.shopcart.service.dao.ProductDao;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductDao service;

	@GetMapping
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView("/product/list");
		mv.addObject("products", service.findAll());
		return mv;
	}

	@GetMapping("/create")
	public ModelAndView create(ProductDto produc) {
		ModelAndView mv = new ModelAndView("/product/create");
		mv.addObject("product", produc);
		return mv;
	}

	@GetMapping("/update/{id}")
	public ModelAndView update(@PathVariable("id") Long id) {
		Optional<Product> oProduct = service.find(id);
		if (oProduct.get() != null) {
			return create(new ProductDto().createProdutoDTO(oProduct.get()));
		} 
		return create(new ProductDto());
	}

	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		service.delete(id);
		return list();
	}

	@PostMapping("/save")
	public ModelAndView save(@Valid ProductDto product, BindingResult result) {
		if (result.hasErrors()) {
			return create(product);
		}
		service.save(product.toModel(service, result));
		return list();
	}
}

