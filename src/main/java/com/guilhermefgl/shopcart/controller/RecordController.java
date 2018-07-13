package com.guilhermefgl.shopcart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.guilhermefgl.shopcart.model.Cart;
import com.guilhermefgl.shopcart.security.model.AuthenticationFacade;
import com.guilhermefgl.shopcart.service.dao.CartDao;

@Controller
@RequestMapping("/record")
public class RecordController {

	@Autowired
	private CartDao service;

	@Autowired
	private AuthenticationFacade authentication;

	@GetMapping
	public ModelAndView list() {
		Authentication auth = authentication.getAuthentication();
		if (auth instanceof AnonymousAuthenticationToken) {
			return new ModelAndView("redirect:/login");
		}
		
		List<Cart> carts = service.findAll(auth.getName());
		if (carts == null || carts.isEmpty()) {
			return new ModelAndView("redirect:/");
		}
			
		ModelAndView mv = new ModelAndView("/record/list");
		mv.addObject("carts", carts);
		return mv;
	}
}
