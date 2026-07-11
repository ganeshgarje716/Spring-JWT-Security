package com.ganesh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ganesh.entity.User;

public interface UserRespository extends JpaRepository<User, Integer>{
	
	

}
