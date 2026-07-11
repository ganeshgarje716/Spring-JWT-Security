package com.ganesh.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/welcome")
public class WelcomeRestController {
	
	
	
	@GetMapping
	public String welcomeMsg() {
		
		return "Welcome We Are Learning Spring JWT Security";
	}

}
