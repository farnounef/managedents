package com.example.gestiondents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gestiondents.entities.Tooth;

@Repository
public interface ToothRepository extends JpaRepository<Tooth,Integer> {
}
