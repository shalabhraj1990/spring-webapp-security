package com.spring.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.spring.security.filter.ServerSecurityContextRepositoryImp;

@Configuration
public class WebFluxSecurityConfiguration {
	@Autowired
	private ReactiveAuthenticationMangerImp authManager;
	@Autowired
	private ServerSecurityContextRepositoryImp securityContext;

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		return  http.csrf().disable().httpBasic().disable().formLogin().disable().authenticationManager(authManager)
				.securityContextRepository(securityContext).authorizeExchange().pathMatchers("reactive/create-token")
				.permitAll().anyExchange().authenticated().and().build();

	}
}
