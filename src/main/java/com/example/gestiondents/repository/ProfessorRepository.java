package com.example.gestiondents.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gestiondents.entities.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor,Integer>{
	Optional<Professor> findByUsernameOrEmail(String username, String email);
}
