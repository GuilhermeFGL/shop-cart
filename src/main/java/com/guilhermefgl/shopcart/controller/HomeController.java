package com.guilhermefgl.shopcart.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.guilhermefgl.shopcart.model.Cart;
import com.guilhermefgl.shopcart.model.Product;
import com.guilhermefgl.shopcart.security.model.AuthenticationFacade;
import com.guilhermefgl.shopcart.service.dao.CartDao;
import com.guilhermefgl.shopcart.service.dao.ProductDao;

@Controller
@RequestMapping("/")
public class HomeController {

	@Autowired
	private ProductDao productService;

	@Autowired
	private CartDao cartService;

	@Autowired
	private AuthenticationFacade authentication;

	@GetMapping
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView("/home/home");
		mv.addObject("products", productService.findAll());

		Integer cartQuantity = 0;
		Authentication auth = authentication.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			mv.addObject("user", auth.getName());

			Optional<Cart> oCart = cartService.getOpenCart(auth.getName());
			if (oCart.isPresent()) {
				cartQuantity = oCart.get().getProducts().size();
			}
		}
		mv.addObject("cartQuantity", cartQuantity);

		return mv;
	}

	@GetMapping("{id}")
	public ModelAndView show(@PathVariable("id") Long id) {
		Optional<Product> product = productService.find(id);

		if (product.isPresent()) {
			ModelAndView mv = new ModelAndView("/home/detail");
			mv.addObject("product", product.get());

			Authentication auth = authentication.getAuthentication();
			if (!(auth instanceof AnonymousAuthenticationToken)) {
				mv.addObject("user", auth.getName());
			}

			return mv;
		} else {
			return list();
		}
	}

}
