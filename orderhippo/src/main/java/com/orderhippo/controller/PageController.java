package com.orderhippo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "頁面顯示API")
@Controller
//@CrossOrigin(origins = "http://127.0.0.1:8080")
public class PageController {
	
	@ApiOperation("主頁面")
	@ApiResponses({
		@ApiResponse(code = 401, message = "沒有權限"),
		@ApiResponse(code = 404, message = "找不到路徑")
	})
	@GetMapping("/homepage")
	public String homepagePage() {
		return "homepage.html";
	}

	@ApiOperation("註冊頁面")
	@ApiResponses({
		@ApiResponse(code = 401, message = "沒有權限"),
		@ApiResponse(code = 404, message = "找不到路徑")
	})
	@GetMapping("/register")
	public String registerPage() {
		return "register.html";
	}
	
	@ApiOperation("登入頁面")
	@ApiResponses({
		@ApiResponse(code = 401, message = "沒有權限"),
		@ApiResponse(code = 404, message = "找不到路徑")
	})
	@GetMapping("/login")
	public String loginPage() {
		return "login.html";
	}
	
}
