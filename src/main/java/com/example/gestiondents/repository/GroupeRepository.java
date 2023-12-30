package com.example.gestiondents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gestiondents.entities.Groupe;

@Repository
public interface GroupeRepository  extends JpaRepository<Groupe,Integer>{
}
