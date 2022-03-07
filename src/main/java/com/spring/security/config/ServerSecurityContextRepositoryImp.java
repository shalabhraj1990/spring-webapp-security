package com.spring.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class ServerSecurityContextRepositoryImp implements ServerSecurityContextRepository {
	@Autowired
	private ReactiveAuthenticationMangerImp authManager;

	@Override
	public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
		return Mono.error(new UnsupportedOperationException("Not Supported Operation!!!"));
	}

	@Override
	public Mono<SecurityContext> load(ServerWebExchange exchange) {
		ServerHttpRequest httpRequest = exchange.getRequest();
		String header = httpRequest.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		if (!StringUtils.isEmpty(header) && header.startsWith("Bearer")) {
			String jsonToken = header.substring(7);
			Authentication authentication = new UsernamePasswordAuthenticationToken(jsonToken, jsonToken);
			return authManager.authenticate(authentication).map(successAuth -> new SecurityContextImpl(successAuth));
		}
		return Mono.empty();
		//return Mono.error(new UnsupportedOperationException("Invalid Token !!!"));
	}

}
