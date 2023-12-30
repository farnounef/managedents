package com.example.gestiondents.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity

@AllArgsConstructor
@NoArgsConstructor
public class Tooth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;
    private String name ;


    @OneToMany (mappedBy = "toothPw")
    @JsonIgnore
    private List<PW> pwTooth ;


	public void setId(int id2) {
		// TODO Auto-generated method stub
		
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public List<PW> getPwTooth() {
		return pwTooth;
	}


	public void setPwTooth(List<PW> pwTooth) {
		this.pwTooth = pwTooth;
	}


	public int getId() {
		return id;
	}
	
	
}
