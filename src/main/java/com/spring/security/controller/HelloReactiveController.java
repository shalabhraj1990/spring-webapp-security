package com.spring.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class HelloReactiveController {

	@GetMapping("hi")
	public Mono<String> sayHello() {
		return Mono.just("Spring Security with JWT-TOKEN (Reactive-application)");
	}

}
