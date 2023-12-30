package com.example.gestiondents.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestiondents.entities.Admin;
import com.example.gestiondents.entities.Professor;
import com.example.gestiondents.entities.Student;
import com.example.gestiondents.entities.User;
import com.example.gestiondents.repository.UserRepository;
import com.example.gestiondents.service.AdminService;
import com.example.gestiondents.service.ProfessorService;
import com.example.gestiondents.service.RoleService;
import com.example.gestiondents.service.StudentService;
import com.example.gestiondents.service.UserService;




@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService service;

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

    
    @GetMapping("allUsers")
    public List<User> findAllUsers() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable int id) {
        User user = service.findById(id);
        if (user == null) {
            return new ResponseEntity<Object>("User avec Id " + id + " nexiste pas", HttpStatus.BAD_REQUEST);

        } else {
        	
        	switch(user.getTypeUser()) {
        	case "STUDENT":
        		var student = studentService.findById(id);
        		if(student != null) {
        			return ResponseEntity.ok(student);
        		}
        		break;
        	case "PROFESSOR":
        		var professor = professorService.findById(id);
        		if(professor != null) {
        			return ResponseEntity.ok(professor);
        		}
        		break;
        	case "ADMIN":
        		var admin = adminService.findById(id);
        		if(admin != null) {
        			return ResponseEntity.ok(admin);
        		}
        		break;
        	default:
        		return ResponseEntity.ok(user);
        	}
        	
            return ResponseEntity.ok(user);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletUser(@PathVariable int id) {
        User User = service.findById(id);
        if (User == null) {
            return new ResponseEntity<Object>("User avec Id " + id + " nexiste pas", HttpStatus.BAD_REQUEST);
        } else {
            service.delete(User);
            return ResponseEntity.ok("User avec id " + id + " suprime");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable int id, @RequestBody User newUser) {

        User oldUser = service.findById(id);
        if (oldUser == null) {
            return new ResponseEntity<Object>("User avec id" + id + "nexiste pas ", HttpStatus.BAD_REQUEST);

        } else {
            newUser.setId(id);
            return ResponseEntity.ok(service.update(newUser));
        }
    }
    
    /*
    @PostMapping
    public User creatUser(@RequestBody User User) {
        User.setId(0);
        return service.create(User);
    }*/
    
    /*
    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("User already exists");
        }
        
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
	    		user.setPassword(user.getPassword()); //passwordEncoder.encode(user.getPassword()));
	    		return ResponseEntity.ok(user);
    	}
        
    }*/
    
        
    @GetMapping("/signup")
    public String register(Model model) {
    	
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String registerUser(User user, BindingResult result, Model model) {
        if (userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail()).isPresent()) {
            model.addAttribute("error", "User already exists");
            return "signup";
        }
        
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        
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
				studentService.create(student);
	    		break;
	    		
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
	        	professor.setPassword(user.getPassword()); // passwordEncoder.encode(user.getPassword()));
	        	professor.setUsername(user.getUsername());
	        	professor.setImage(" ");
	        	professor.setGrade(" ");
				professorService.create(professor);
	    		break;
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
	        	admin.setPassword(user.getPassword()); // passwordEncoder.encode(user.getPassword()));
	        	admin.setUsername(user.getUsername());
	        	admin.setImage(" ");
	        	adminService.create(admin);
	    		break;
	    	default:
	    		user.setPassword(user.getPassword()); // passwordEncoder.encode(user.getPassword()));
	    		service.create(user);
        }
        
        
        /*user.setPassword(passwordEncoder.encode(user.getPassword()));
        var userRole = roleRepository.findByName("ROLE_USER").orElse(null);
        user.setRoles(Set.of(userRole));
        userRepository.save(user);
        */
        
        
        
        
        return "login";
    }

    
}
