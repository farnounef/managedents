package com.example.gestiondents.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gestiondents.entities.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
	Optional<Student> findByUsernameOrEmail(String username, String email);
}
