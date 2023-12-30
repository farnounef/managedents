package com.example.gestiondents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gestiondents.entities.StudentPW;

@Repository
public interface StudentPWRepository extends JpaRepository<StudentPW,Integer>{

}
