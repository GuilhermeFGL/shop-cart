package com.guilhermefgl.shopcart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.guilhermefgl.shopcart.service.dao.ProductDao;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private ProductDao service;
	
	@GetMapping
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView("/home/list");
		mv.addObject("products", service.findAll());
		return mv;
	}

}
