package com.guilhermefgl.shopcart.service.dao;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.guilhermefgl.shopcart.model.User;
import com.guilhermefgl.shopcart.model.dto.UserDto;

public interface UserDao extends UserDetailsService {

	User findByEmail(String email);

	User save(UserDto registration);
}