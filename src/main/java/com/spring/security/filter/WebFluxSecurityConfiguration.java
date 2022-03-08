package com.spring.security.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.spring.security.config.ReactiveAuthenticationMangerImp;
import com.spring.security.config.ServerSecurityContextRepositoryImp;

@Configuration
@EnableWebFluxSecurity
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebFluxSecurityConfiguration {
	@Autowired
	private ReactiveAuthenticationMangerImp authManager;
	@Autowired
	private ServerSecurityContextRepositoryImp securityContext;

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		return http.csrf().disable().httpBasic().disable().formLogin().disable().authenticationManager(authManager)
				.securityContextRepository(securityContext).authorizeExchange().pathMatchers("/reactive/create-token")
				.permitAll().anyExchange().authenticated().and().build();

	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
}
