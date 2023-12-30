package com.example.gestiondents.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable

public class StudentPwPrimary implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManyToOne
    @JoinColumn(name = "id_student", insertable = false, updatable = false)
    private Student student;
    @ManyToOne
    @JoinColumn(name = "id_pw" , insertable = false, updatable = false)
    private PW pw;
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public PW getPw() {
		return pw;
	}
	public void setPw(PW pw) {
		this.pw = pw;
	}
    
    
}
