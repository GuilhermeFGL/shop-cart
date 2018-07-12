package com.guilhermefgl.shopcart.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.guilhermefgl.shopcart.model.Product;
import com.guilhermefgl.shopcart.security.model.AuthenticationFacade;
import com.guilhermefgl.shopcart.service.dao.ProductDao;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private ProductDao service;
	
    @Autowired
    private AuthenticationFacade authentication;
    
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:login";
	}
	
	@GetMapping
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView("/home/home");
		mv.addObject("products", service.findAll());
		
		Authentication auth = authentication.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			mv.addObject("user", auth.getName());
		}
		return mv;
	}
	
	@GetMapping("{id}")
	public ModelAndView show(@PathVariable("id") Long id) {
		Optional<Product> product = service.find(id);
		
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
