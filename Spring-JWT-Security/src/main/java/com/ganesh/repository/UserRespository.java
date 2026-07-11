package com.ganesh.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ganesh.entity.User;

public interface UserRespository extends JpaRepository<User, Integer>{
	
	
	Optional<User> findByUsername(String username);

}
