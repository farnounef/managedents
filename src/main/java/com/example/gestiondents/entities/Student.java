package com.example.gestiondents.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity

@AllArgsConstructor
@NoArgsConstructor
public class Student extends User{
    private String number ;

    @ManyToOne
    
    private Groupe studentGrp ;

    @OneToMany(mappedBy = "pk.student")
    @JsonIgnore
    private List <StudentPW> student ;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Groupe getStudentGrp() {
		return studentGrp;
	}

	public void setStudentGrp(Groupe studentGrp) {
		this.studentGrp = studentGrp;
	}

	public List<StudentPW> getStudent() {
		return student;
	}

	public void setStudent(List<StudentPW> student) {
		this.student = student;
	}

}
