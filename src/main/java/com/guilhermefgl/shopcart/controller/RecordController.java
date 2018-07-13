package com.guilhermefgl.shopcart.controller;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.guilhermefgl.shopcart.model.Cart;
import com.guilhermefgl.shopcart.model.Product;
import com.guilhermefgl.shopcart.security.model.AuthenticationFacade;
import com.guilhermefgl.shopcart.service.dao.CartDao;

@Controller
@RequestMapping("/record")
public class RecordController {
	
	private static final String CSV_SEPARATOR = ";";
	private static final DateTimeFormatter DATE_FORMATER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

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
			carts = new ArrayList<Cart>();
		}

		ModelAndView mv = new ModelAndView("/record/list");
		mv.addObject("carts", carts);
		return mv;
	}

	@GetMapping(value = "/download/cart_record.csv", produces = "text/csv")
	public void downloadCSV(HttpServletResponse response) throws IOException {
		List<Cart> carts = new ArrayList<Cart>();
		Authentication auth = authentication.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			carts = service.findAll(auth.getName());
		}
		
		response.setHeader("Content-Type", "text/csv");

		StringBuilder sb = new StringBuilder();
		sb.append("Date and Time;Situation;Total;Products").append(System.lineSeparator());
		for (Cart cart : carts) {
			if (cart.getDate() != null) {
				sb.append(DATE_FORMATER.format(cart.getDate()));
			}
			sb.append(CSV_SEPARATOR);
			sb.append(cart.getClosed() ? "closed" : "open").append(CSV_SEPARATOR);
			sb.append("$ " + cart.getTotal()).append(CSV_SEPARATOR);
			
			for (Product product : cart.getProducts()) {
				sb.append(product.getName() + "($ " + product.getPrice() + ")").append(CSV_SEPARATOR);
			}
			
			sb.append(System.lineSeparator());
		}
		
		response.getWriter().println(sb.toString());
	}
}
