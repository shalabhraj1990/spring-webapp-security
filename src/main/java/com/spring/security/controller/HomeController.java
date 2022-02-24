package com.spring.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("/index")
	public String homePage() {
		return "index";
	}

	@GetMapping("/user")
	public String userHomePage() {
		return "user/index";
	}

	@GetMapping("/employee")
	public String employeeHomePage() {
		return "employee/index";
	}

	@GetMapping("/admin")
	public String adminHomePage() {
		return "admin/index";
	}

}
