package com.guilhermefgl.shopcart.controller;

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
@RequestMapping("/registration")
public class UserController {

	@Autowired
	private UserService userService;

	@ModelAttribute("user")
	public UserDto userRegistrationDto() {
		return new UserDto();
	}

	@GetMapping
	public String showRegistrationForm(Model model) {
		return "user/registration";
	}

	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") @Valid UserDto userDto,
			BindingResult result) {

		User existing = userService.findByEmail(userDto.getEmail());
		if (existing != null) {
			result.rejectValue("email", null, "There is already an account registered with that email");
		}

		if (result.hasErrors()) {
			return "user/registration";
		}

		userService.save(userDto);
		return "redirect:/registration?success";
	}

}
