package com.guilhermefgl.shopcart.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.guilhermefgl.shopcart.model.User;
import com.guilhermefgl.shopcart.model.dto.UserDto;
import com.guilhermefgl.shopcart.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@ModelAttribute("user")
	public UserDto userRegistrationDto() {
		return new UserDto();
	}

	@GetMapping("/login")
	public String login(Model model) {
		return "user/login";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:login";
	}

	@GetMapping("/registration")
	public String showRegistrationForm(Model model) {
		return "user/registration";
	}

	@PostMapping("/registration")
	public String registerUserAccount(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result,
			HttpServletRequest request) {

		User existing = userService.findByEmail(userDto.getEmail());
		if (existing != null) {
			result.rejectValue("email", null, "There is already an account registered with that email");
		}

		if (result.hasErrors()) {
			return "user/registration";
		}

		userService.save(userDto);
		
		try {
			request.login(userDto.getEmail(), userDto.getPassword());
			return "redirect:/";
		} catch (ServletException e) {
			return "redirect:/user/login";
		}	
	}

}
