package com.spring.security.config;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.spring.security.service.JwtService;
import com.spring.security.service.ReactiveUserDetailsServiceImpl;

import reactor.core.publisher.Mono;

@Component
public class ReactiveAuthenticationMangerImp implements ReactiveAuthenticationManager {
	@Autowired
	JwtService jwtService;

	@Autowired
	ReactiveUserDetailsServiceImpl reactiveUserDetailsServiceImpl;

	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		String jwtToken = authentication.getCredentials().toString();
		String userName = jwtService.extractUsername(jwtToken);
		Mono<UserDetails> monoUser = reactiveUserDetailsServiceImpl.loadUserByUsername(userName);

		return monoUser.flatMap(user -> {
			if (!jwtService.validateToken(jwtToken, user.getUsername())) {
				Mono.empty();
			}
			Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					user.getUsername(), null, authorities);
			return Mono.just(authenticationToken);
		});

	}

}
