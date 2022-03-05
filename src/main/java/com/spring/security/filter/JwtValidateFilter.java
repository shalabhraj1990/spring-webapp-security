package com.spring.security.filter;

import java.io.IOException;

import javax.management.RuntimeErrorException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.spring.security.service.JwtService;

@Service
public class JwtValidateFilter extends OncePerRequestFilter {
	@Autowired
	JwtService jwtService;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader("Authorization");
		if (!StringUtils.isEmpty(header) && header.startsWith("Bearer")) {
			String jsonToken = header.substring(7);
			String userName = jwtService.extractUsername(jsonToken);
			UserDetails dbuser = userDetailsService.loadUserByUsername(userName);
			if (SecurityContextHolder.getContext().getAuthentication() == null) {
				if (jwtService.validateToken(jsonToken, dbuser.getUsername())) {
					UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
							dbuser.getPassword(), dbuser.getUsername(), dbuser.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(token);
				} else {
					throw new RuntimeException("invalid token !!!");
				}
			}
		}
		filterChain.doFilter(request, response);
	}

}
