package com.example.gestiondents.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity

@AllArgsConstructor
@NoArgsConstructor
public class Professor extends User {
    private String grade;

    @OneToMany(mappedBy = "profGrp")
    @JsonIgnore
    private List<Groupe> grpProf;

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public List<Groupe> getGrpProf() {
		return grpProf;
	}

	public void setGrpProf(List<Groupe> grpProf) {
		this.grpProf = grpProf;
	}
    
    
}
