package com.ganesh.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ganesh.entity.User;
import com.ganesh.repository.UserRespository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	
	@Autowired
	UserRespository userRespository;
	
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> byUsername = userRespository.findByUsername(username);
		
		if (byUsername.isPresent()) {
			
			User user = byUsername.get();
			
			return org.springframework.security.core.userdetails.User
					.withUsername(user.getUsername())
					.password(user.getPassword())
					.roles(user.getRole())
					.build();
		}
		
		throw new UsernameNotFoundException("User Not Found");
	}
	
	
	
	

}
