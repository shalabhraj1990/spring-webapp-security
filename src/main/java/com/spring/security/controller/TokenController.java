package com.spring.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.modal.CreateJwtRequest;
import com.spring.security.service.JwtService;
import com.spring.security.service.ReactiveUserDetailsServiceImpl;

import reactor.core.publisher.Mono;

@RestController
public class TokenController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	JwtService jwtService;

	@Autowired
	ReactiveUserDetailsServiceImpl reactiveUserDetailsServiceImpl;

	@Autowired
	private BCryptPasswordEncoder passerdEncoder;

	@PostMapping("/servlet/create-token")
	public String createJwtToken(@RequestBody CreateJwtRequest request) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getUsername(),
				request.getPassword());
		authenticationManager.authenticate(token);// Error If User Name & Password is not Matching
		return jwtService.createToken(request.getUsername());
	}

	@PostMapping("/reactive/create-token")
	public Mono<String> createReativeJwtToken(@RequestBody CreateJwtRequest request) {

		Mono<UserDetails> monUser = reactiveUserDetailsServiceImpl.loadUserByUsername(request.getUsername());
		return monUser.flatMap(user -> {
			if (passerdEncoder.matches(request.getPassword(), user.getPassword())) {
				return Mono.just(jwtService.createToken(request.getUsername()));
			} else {
				return Mono.error(new RuntimeException("Username and Password is not matched !!!"));
			}
		});

	}
}
