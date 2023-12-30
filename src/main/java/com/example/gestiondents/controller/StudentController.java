package com.example.gestiondents.controller;

import java.util.List;
import java.util.Set;
import java.util.Base64;
import java.io.IOException;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import com.example.gestiondents.entities.MyUserDetails;
// import com.example.gestiondents.entities.MyUserDetails;
import com.example.gestiondents.entities.Student;
import com.example.gestiondents.repository.GroupeRepository;
import com.example.gestiondents.repository.StudentRepository;
import com.example.gestiondents.service.RoleService;

@Controller
public class StudentController {

	private final RoleService roleService;
	
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private GroupeRepository groupeRepository;

    public StudentController(RoleService roleService) {
    	this.roleService = roleService;
    }
    
    @GetMapping("/signup2")
    public String showSignUppForm(Student student , BindingResult result, Model model) {
    	
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            List<Student> students = studentRepository.findAll();
        students.forEach(studentItem -> {
            if (studentItem.getPhoto() != null) {
                String base64Image = Base64.getEncoder().encodeToString(studentItem.getPhoto());
                studentItem.setImageBase64(base64Image);
            }
        });
            model.addAttribute("students", studentRepository.findAll());
        	model.addAttribute("groupes", groupeRepository.findAll());

            return "index2";
        }
        return "login";
    }

    @PostMapping("/addstudent")
    public String addStudent(@RequestParam(value = "file", required = false) MultipartFile file,Student student, BindingResult result, Model model) {
    	
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
        	if (result.hasErrors()) {
                return "add-student";
            }
        	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            student.setPassword(passwordEncoder.encode(student.getPassword()));
            var role = this.roleService.findByName("ROLE_STUDENT");
        	if(role != null) {
        		student.setRoles(List.of(role));
        	}
            
        	
        	student.setTypeUser("STUDENT");
            
             try {
           
            if (file != null && !file.isEmpty()) {
                byte[] newImage = file.getBytes();
                student.setPhoto(newImage); 
            }
        
        studentRepository.save(student);
        
        } catch (IOException e) {
            // Gérer les exceptions liées à la manipulation de fichiers (IOException)
            // Ajoutez ici le code pour gérer l'erreur, par exemple, renvoyer à une page d'erreur.
        }
        List<Student> students = studentRepository.findAll();
        students.forEach(studentItem -> {
            if (studentItem.getPhoto() != null) {
                String base64Image = Base64.getEncoder().encodeToString(studentItem.getPhoto());
                studentItem.setImageBase64(base64Image);
            }
        });
            model.addAttribute("students", studentRepository.findAll());
            return "index2";
        }
        return null;
        
    }
    
    @GetMapping("/addstudent")
    public String showAddStudentForm(Student student, Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("groupes", groupeRepository.findAll());

        return "add-student";
    }

    

    @GetMapping("/editt/{id}")
    public String showUpdateeForm(@PathVariable("id") int id, Model model) {
        
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
        
        	Student student = studentRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
            
        	student.setPassword(null);
        	model.addAttribute("student", student);
            model.addAttribute("groupes", groupeRepository.findAll());
            return "update-student";
            
        }
        
        return "login";
    }

    @PostMapping("/updatee/{id}")
    public String updateStudent(@PathVariable("id") int id, Student student, @RequestParam(value = "file", required = false) MultipartFile file, BindingResult result, Model model) {
        
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
        
        	if (result.hasErrors()) {
                student.setId(id);
                return "update-student";
            }
        	
        	var passwordIn = student.getPassword();
        	if(passwordIn != null && passwordIn.length() > 0)
        	{
        		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        		student.setPassword(passwordEncoder.encode(passwordIn));	
        	}
        	else 
        	{
        		var studentDb = studentRepository.findById((int)student.getId());
        		student.setPassword(studentDb.get().getPassword());
        	}
        	
            var role = this.roleService.findByName("ROLE_STUDENT");
        	if(role != null) {
        		student.setRoles(null);
        		student.setRoles(List.of(role));
        	}
        	
        	student.setTypeUser("STUDENT");
        	student.setId(id);
            studentRepository.save(student);
            
            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
            if (roles.contains("ROLE_STUDENT")) {
            	return "welcome";
            }
        	
            Student std = studentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("test"));
        try {
            // Vérifiez si un fichier a été téléchargé
            if (!file.isEmpty() && file!=null) {
                byte[] newImage = file.getBytes();
                student.setPhoto(newImage); // Mettre à jour l'image
            }else {
            	student.setPhoto(std.getPhoto());
            }

            studentRepository.save(student);
            model.addAttribute("students", studentRepository.findAll());
        } catch (IOException e) {
            // Gérer les exceptions liées à la manipulation de fichiers (IOException)
            // Ajoutez ici le code pour gérer l'erreur, par exemple, renvoyer à une page d'erreur.
        }
        List<Student> students = studentRepository.findAll();
        students.forEach(studentItem -> {
            if (studentItem.getPhoto() != null) {
                String base64Image = Base64.getEncoder().encodeToString(studentItem.getPhoto());
                studentItem.setImageBase64(base64Image);
            }
        });
            return "index2";
        	
        }
    	return "login";
    }

    @GetMapping("/deletee/{id}")
    public String deleteStudent(@PathVariable("id") int id, Model model) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        studentRepository.delete(student);
        List<Student> students = studentRepository.findAll();
        students.forEach(studentItem -> {
            if (studentItem.getPhoto() != null) {
                String base64Image = Base64.getEncoder().encodeToString(studentItem.getPhoto());
                studentItem.setImageBase64(base64Image);
            }
        });
        model.addAttribute("students", studentRepository.findAll());
        return "index2";
    }
}
