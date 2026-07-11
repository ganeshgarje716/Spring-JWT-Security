package com.ganesh.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ganesh.entity.User;
import com.ganesh.repository.UserRespository;

@RestController
@RequestMapping("/user")
public class UserRestController {
	
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private final UserRespository userRespository;
	
	
	public UserRestController(BCryptPasswordEncoder bCryptPasswordEncoder, UserRespository userRespository) {
		
		this.bCryptPasswordEncoder=bCryptPasswordEncoder;
		
		this.userRespository=userRespository;
	}
	
	
	
	@PostMapping("/create")
	public ResponseEntity<User> createUsers(@RequestBody User user) {
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		user.setRole("USER");
		
		User save = userRespository.save(user);
		
		return new ResponseEntity<User>(save, HttpStatus.CREATED);
	}

}
