package com.example.gestiondents.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity

@AllArgsConstructor
@NoArgsConstructor
public class PW {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title ;
    private String objectif;
    private String docs ;

    @ManyToOne
    private Tooth toothPw;


    @ManyToMany(mappedBy = "pwGroup")
    @JsonIgnore
    private List<Groupe> groupPw ;

    @OneToMany(mappedBy = "pk.pw")
    @JsonIgnore
    private List<StudentPW> pw1 ;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getObjectif() {
		return objectif;
	}

	public void setObjectif(String objectif) {
		this.objectif = objectif;
	}

	public String getDocs() {
		return docs;
	}

	public void setDocs(String docs) {
		this.docs = docs;
	}

	public Tooth getToothPw() {
		return toothPw;
	}

	public void setToothPw(Tooth toothPw) {
		this.toothPw = toothPw;
	}

	public List<Groupe> getGroupPw() {
		return groupPw;
	}

	public void setGroupPw(List<Groupe> groupPw) {
		this.groupPw = groupPw;
	}

	public List<StudentPW> getPw1() {
		return pw1;
	}

	public void setPw1(List<StudentPW> pw1) {
		this.pw1 = pw1;
	}
    
    
}
