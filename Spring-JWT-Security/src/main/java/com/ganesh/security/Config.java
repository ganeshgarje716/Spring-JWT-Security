package com.ganesh.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ganesh.service.CustomUserDetailsService;
import com.ganesh.service.JWTFilter;

@Configuration
@EnableWebSecurity
public class Config {
	
	
	@Autowired
	JWTFilter jwtFilter;
	
	
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	
	
	
	@Bean
	public BCryptPasswordEncoder encoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) {
		
		http.csrf(csrf->csrf.disable())
		.authorizeHttpRequests(auth->auth.requestMatchers("/user/create", "/authenticate")
				.permitAll()
				.anyRequest()
				.authenticated())
		        .addFilterBefore(jwtFilter,
		                UsernamePasswordAuthenticationFilter.class)
		.httpBasic(Customizer.withDefaults());
		
		return http.build();
	}
	
	
	
	@Bean
	public DaoAuthenticationProvider authenticateUser() {
		
		DaoAuthenticationProvider provider = new  DaoAuthenticationProvider(customUserDetailsService);
		
		provider.setPasswordEncoder(encoder());
		
		return provider;
	}
	
	
	
	@Bean
	public AuthenticationManager authManager(AuthenticationConfiguration auth)
	{
		return auth.getAuthenticationManager();
	}

}
