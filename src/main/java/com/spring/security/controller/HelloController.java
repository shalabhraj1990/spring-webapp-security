package com.spring.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.modal.CreateJwtRequest;
import com.spring.security.service.JwtService;

@RestController
public class HelloController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	JwtService jwtService;

	@GetMapping("hi")
	public String sayHello() {
		return "Spring Security with JWT-TOKEN (Servelet-application)";
	}

	@PostMapping("create-token")
	public String createJwtToken(@RequestBody CreateJwtRequest request) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getUsername(),
				request.getPassword());
		authenticationManager.authenticate(token);// Error If User Name & Password is not Matching
		return jwtService.createToken(request.getUsername());
	}
}
