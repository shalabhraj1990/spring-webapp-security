package com.spring.security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//@Component
public class JdbcSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder encoder = passwordEncoder();
		auth.jdbcAuthentication().dataSource(dataSource);
		// .withDefaultSchema()
//				.withUser("employee").password(encoder.encode("emp123")).roles("EMPLOYEE").and().withUser("user")
//				.password(encoder.encode("user123")).roles("USER").and().withUser("admin")
//				.password(encoder.encode("admin123")).roles("ADMIN");
		// for customized table
//		.usersByUsernameQuery("SELECT * FROM world.users  where username = ?")
//		.authoritiesByUsernameQuery("SELECT * FROM world.authorities where username = ?")
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/index").permitAll().antMatchers("/user").authenticated()
				.antMatchers("/employee").hasAnyRole("EMPLOYEE", "ADMIN").antMatchers("/admin").hasRole("ADMIN").and()
				.logout().logoutSuccessUrl("/index").and().formLogin();

	}

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}
}
