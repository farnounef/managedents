package com.example.gestiondents.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestiondents.entities.StudentPW;
import com.example.gestiondents.service.StudentPWService;



@RestController
@RequestMapping("/api/studentpw")
public class StudentPWController {

    private final StudentPWService studentPWService;

    @Autowired
    public StudentPWController(StudentPWService studentPWService) {
        this.studentPWService = studentPWService;
    }

    @PostMapping("/create")
    public ResponseEntity<StudentPW> createStudentPW(@RequestBody StudentPW studentPW) {
        StudentPW createdStudentPW = studentPWService.create(studentPW);
        return new ResponseEntity<>(createdStudentPW, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteStudentPW(@RequestBody StudentPW studentPW) {
        boolean deleted = studentPWService.delete(studentPW);
        if (deleted) {
            return new ResponseEntity<>("StudentPW deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to delete StudentPW", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<StudentPW> updateStudentPW(@RequestBody StudentPW studentPW) {
        StudentPW updatedStudentPW = studentPWService.update(studentPW);
        return new ResponseEntity<>(updatedStudentPW, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentPW> getStudentPWById(@PathVariable int id) {
        StudentPW studentPW = studentPWService.findById(id);
        if (studentPW != null) {
            return new ResponseEntity<>(studentPW, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentPW>> getAllStudentPWs() {
        List<StudentPW> studentPWs = studentPWService.findAll();
        return new ResponseEntity<>(studentPWs, HttpStatus.OK);
    }
}