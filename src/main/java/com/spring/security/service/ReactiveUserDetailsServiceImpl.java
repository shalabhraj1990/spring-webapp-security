package com.spring.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.spring.security.entity.AppUser;
import com.spring.security.repository.AppUserRepository;

import reactor.core.publisher.Mono;

@Service
public class ReactiveUserDetailsServiceImpl {
	@Autowired
	AppUserRepository appUserRepository;

	public Mono<UserDetails> loadUserByUsername(String username) throws UsernameNotFoundException {
		if (StringUtils.isEmpty(username)) {
			new UsernameNotFoundException("user name is empty!!");
		}
		Optional<AppUser> appUser = appUserRepository.findByUsername(username);
		appUser.orElseThrow(() -> new UsernameNotFoundException("user not exsist in db"));
		AppUser user = appUser.get();
		return Mono.just(User.builder().username(user.getUsername()).password(user.getPassword())
				.disabled(!user.getActive()).roles(user.getRoles()).build());
	}

}
