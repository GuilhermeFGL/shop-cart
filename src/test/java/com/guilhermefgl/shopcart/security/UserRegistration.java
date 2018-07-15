package com.guilhermefgl.shopcart.security;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ActiveProfiles("it")
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
public class UserRegistration {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void registration_submitRegistrationAccountExists() throws Exception {
		this.mockMvc
				.perform(post("/user/registration").with(csrf()).contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("firstName", "Memory").param("lastName", "Not Found")
						.param("email", "info@memorynotfound.com").param("confirmEmail", "info@memorynotfound.com")
						.param("password", "password").param("confirmPassword", "password"))
				.andExpect(model().hasErrors()).andExpect(model().attributeHasFieldErrors("user", "email"))
				.andExpect(status().isOk());
	}

	@Test
	public void registration_submitRegistrationPasswordNotMatching() throws Exception {
		this.mockMvc
				.perform(post("/user/registration").with(csrf()).contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("firstName", "Memory").param("lastName", "Not Found")
						.param("email", "new@memorynotfound.com").param("confirmEmail", "new@memorynotfound.com")
						.param("password", "password").param("confirmPassword", "invalid"))
				.andExpect(model().hasErrors()).andExpect(model().attributeHasErrors("user"))
				.andExpect(status().isOk());
	}

	@Test
	public void registration_submitRegistrationEmailNotMatching() throws Exception {
		this.mockMvc
				.perform(post("/user/registration").with(csrf()).contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("firstName", "Memory").param("lastName", "Not Found")
						.param("email", "new@memorynotfound.com").param("confirmEmail", "different@memorynotfound.com")
						.param("password", "password").param("confirmPassword", "invalid"))
				.andExpect(model().hasErrors()).andExpect(model().attributeHasErrors("user"))
				.andExpect(status().isOk());
	}

	@Test
	public void registration_submitRegistrationSuccess() throws Exception {
		this.mockMvc
				.perform(post("/user/registration").with(csrf()).contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("firstName", "Memory").param("lastName", "Not Found")
						.param("email", "new@memorynotfound.com").param("confirmEmail", "new@memorynotfound.com")
						.param("password", "password").param("confirmPassword", "password"))
				.andExpect(redirectedUrl("/"));
	}

}
