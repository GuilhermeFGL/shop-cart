package com.guilhermefgl.shopcart.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.guilhermefgl.shopcart.model.Cart;
import com.guilhermefgl.shopcart.model.Product;
import com.guilhermefgl.shopcart.model.dto.ProductDto;
import com.guilhermefgl.shopcart.security.model.AuthenticationFacade;
import com.guilhermefgl.shopcart.service.dao.CartDao;
import com.guilhermefgl.shopcart.service.dao.ProductDao;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartDao cartService;

	@Autowired
	private ProductDao productService;

	@Autowired
	private AuthenticationFacade authentication;
	
	@GetMapping
	public ModelAndView openCart() {
		Authentication auth = authentication.getAuthentication();
		if (auth instanceof AnonymousAuthenticationToken) {
			return new ModelAndView("redirect:/login");
		}
		
		Optional<Cart> oCart = cartService.getOpenCart(auth.getName());
		if (!oCart.isPresent()) {
			return new ModelAndView("redirect:/");
		}
		
		Double total = 0d;
		for (Product product : oCart.get().getProducts()) {
			total += product.getPrice();
		}
		
		ModelAndView mv = new ModelAndView("/cart/current");
		mv.addObject("products", oCart.get().getProducts());
		mv.addObject("user", auth.getName());
		mv.addObject("total", total);
		return mv;
	}

	@PostMapping("/add")
    public ModelAndView addToCart(@Valid ProductDto product, BindingResult result) {
		Authentication auth = authentication.getAuthentication();
		if (auth instanceof AnonymousAuthenticationToken) {
			return new ModelAndView("redirect:/login");
		}
		
		Optional<Product> oProduct = productService.find(product.getId());
		if (oProduct.get() == null) {
			return new ModelAndView("redirect:/");
		} 
		
		Cart cart;
		Optional<Cart> oCart = cartService.getOpenCart(auth.getName());
		if (oCart.isPresent()) {
			cart = oCart.get();
		} else {
			cart = new Cart();
			cart.setUser(auth.getName());
			cart.setProducts(new ArrayList<Product>());
		}
		cart.getProducts().add(oProduct.get());
		cartService.save(cart);
		
		return openCart();
	}
	
	@GetMapping("/close")
    public ModelAndView close() {
		Authentication auth = authentication.getAuthentication();
		if (auth instanceof AnonymousAuthenticationToken) {
			return new ModelAndView("redirect:/login");
		}
		
		Optional<Cart> oCart = cartService.getOpenCart(auth.getName());
		if (!oCart.isPresent()) {
			return new ModelAndView("redirect:/");
		} else {
			Cart cart = oCart.get();
			cart.setClosed(true);
			cart.setDate(LocalDateTime.now());
			
			double total = 0d;
			for (Product product : cart.getProducts()) {
				total += product.getPrice();
			}
			cart.setTotal(total);
			
			cartService.save(cart);
		}
		
		return new ModelAndView("redirect:/");
	}

}
