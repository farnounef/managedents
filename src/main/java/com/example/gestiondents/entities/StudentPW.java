package com.example.gestiondents.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity

@AllArgsConstructor
@NoArgsConstructor
public class StudentPW {
    @EmbeddedId
    StudentPwPrimary pk ;
    private String time;
    private String imageFront;
    private String imageSide;
    @Temporal(TemporalType.DATE)
    private Date date ;
	public StudentPwPrimary getPk() {
		return pk;
	}
	public void setPk(StudentPwPrimary pk) {
		this.pk = pk;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getImageFront() {
		return imageFront;
	}
	public void setImageFront(String imageFront) {
		this.imageFront = imageFront;
	}
	public String getImageSide() {
		return imageSide;
	}
	public void setImageSide(String imageSide) {
		this.imageSide = imageSide;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

}
