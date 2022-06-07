package com.orderhippo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@CrossOrigin
public class PageController {
	
	@GetMapping("/homepage")
	public String homepagePage() {
		return "homepage.html";
	}

	@GetMapping("/register")
	public String registerPage() {
		return "register.html";
	}
	
	@GetMapping("/login")
	public String loginPage() {
		return "login.html";
	}
	
}
