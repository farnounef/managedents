package com.example.gestiondents.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gestiondents.entities.User;


@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
	
	Optional<User> findByUsernameOrEmail(String username, String email);
}
