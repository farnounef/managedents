package com.example.gestiondents.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity

@NoArgsConstructor
@AllArgsConstructor

public class Groupe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;
    private String code ;
    private String year ;

    @ManyToOne
    private Professor profGrp;

    @OneToMany(mappedBy = "studentGrp")
    @JsonIgnore
    private List<Student> grpStdnt ;

    @ManyToMany
    private List<PW> pwGroup ;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Professor getProfGrp() {
		return profGrp;
	}

	public void setProfGrp(Professor profGrp) {
		this.profGrp = profGrp;
	}

	public List<Student> getGrpStdnt() {
		return grpStdnt;
	}

	public void setGrpStdnt(List<Student> grpStdnt) {
		this.grpStdnt = grpStdnt;
	}

	public List<PW> getPwGroup() {
		return pwGroup;
	}

	public void setPwGroup(List<PW> pwGroup) {
		this.pwGroup = pwGroup;
	}
    
    
}