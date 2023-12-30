package com.example.gestiondents.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity

@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;
    private String username ;
    private String password ;
    private String firstName ;
    private String lastName ;
    private String image;
    private String email;
    private String typeUser;

	@Transient
    private String imageBase64;
    
    @Column(length = 100000)
    @Lob
    private byte[] photo;
    
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({ "users" })
    private List<Role> roles;

    
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String userName) {
		this.username = userName;
	}
	
	public String getTypeUser(){
		return typeUser;
	}
	
	public void setTypeUser(String typeUserIn) {
		typeUser = typeUserIn;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setEmail(String emailIn) {
		this.email = emailIn;
	}
	public String getEmail() {
		return email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setRoles(List<Role> rolesIn) {
		roles = rolesIn;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public String getImageBase64() {
		return imageBase64;
	}
	public void setImageBase64(String imageBase64) {
		this.imageBase64 = imageBase64;
	}
    
}
