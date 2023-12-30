package com.example.gestiondents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gestiondents.entities.PW;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.gestiondents.entities.Groupe;

@Repository
public interface PWRepository extends JpaRepository<PW,Integer> {
 @Query("SELECT pw FROM PW pw JOIN pw.groupPw gp WHERE gp = :groupe")
	    List<PW> findPWsByGroup(Groupe groupe);
}
