package com.example.gestiondents.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gestiondents.entities.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Integer> {
	Optional<Admin> findByUsernameOrEmail(String username, String email);
}
