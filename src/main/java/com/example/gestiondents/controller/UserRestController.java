package com.example.gestiondents.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.gestiondents.entities.Admin;
import com.example.gestiondents.entities.Professor;
import com.example.gestiondents.entities.Student;
import com.example.gestiondents.entities.User;
import com.example.gestiondents.repository.RoleRepository;
import com.example.gestiondents.repository.UserRepository;
import com.example.gestiondents.service.AdminService;
import com.example.gestiondents.service.ProfessorService;
import com.example.gestiondents.service.RoleService;
import com.example.gestiondents.service.StudentService;
import com.example.gestiondents.service.UserService;

public class UserRestController {

	//@Autowired
    //private UserRepository userRepository;

    //@Autowired
    //private RoleRepository roleRepository;
    
    //@Autowired
    //private UserService service;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private StudentService studentService;
    
    @Autowired
    private RoleService roleService;

    @Autowired
    private AdminService adminService;

    @Autowired
	private UserRepository userRepository;


    

    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++");
        System.out.println(user);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("User already exists");
        }
        /*user.setPassword(passwordEncoder.encode(user.getPassword()));
        var userRole = roleRepository.findByName("ROLE_USER").orElse(null);
        user.setRoles(List.of(userRole));
        return ResponseEntity.ok(userRepository.save(user));
        */
        switch(user.getTypeUser()) {
    	case "STUDENT":
    		var student = new Student();
    		var roleStudent = this.roleService.findByName("ROLE_STUDENT");
        	if(roleStudent != null) {
        		student.setRoles(List.of(roleStudent));
        	}
        	student.setTypeUser("STUDENT");
    		student.setEmail(user.getEmail());
    		student.setFirstName(user.getFirstName());
    		student.setLastName(user.getLastName());
    		student.setPassword(user.getPassword()); //passwordEncoder.encode(user.getPassword()));
    		student.setUsername(user.getUsername());
			student.setImage(" ");
			student.setNumber("0");
			return ResponseEntity.ok(studentService.create(student));
    		
    	case "PROFESSOR":
    		var professor = new Professor();
    		var roleProfessor = this.roleService.findByName("ROLE_PROFESSOR");
        	if(roleProfessor != null) {
        		professor.setRoles(List.of(roleProfessor));
        	}
        	professor.setTypeUser("PROFESSOR");
        	professor.setEmail(user.getEmail());
        	professor.setFirstName(user.getFirstName());
        	professor.setLastName(user.getLastName());
        	professor.setPassword(user.getPassword()); //passwordEncoder.encode(user.getPassword()));
        	professor.setUsername(user.getUsername());
        	professor.setImage(" ");
        	professor.setGrade(" ");
			return ResponseEntity.ok(professorService.create(professor));
    		
    	case "ADMIN":
    		var admin = new Admin();
    		var roleAdmin = this.roleService.findByName("ROLE_ADMIN");
        	if(roleAdmin != null) {
        		admin.setRoles(List.of(roleAdmin));
        	}
        	admin.setTypeUser("ADMIN");
        	admin.setEmail(user.getEmail());
        	admin.setFirstName(user.getFirstName());
        	admin.setLastName(user.getLastName());
        	admin.setPassword(user.getPassword()); //passwordEncoder.encode(user.getPassword()));
        	admin.setUsername(user.getUsername());
        	admin.setImage(" ");
        	return ResponseEntity.ok(adminService.create(admin));
    		
    	default:
    		//user.setPassword(user.getPassword()); //passwordEncoder.encode(user.getPassword()));
    		return ResponseEntity.ok(user);
	}
    }
    
}
