package com.example.gestiondents;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.gestiondents.entities.Admin;
import com.example.gestiondents.entities.Role;
import com.example.gestiondents.repository.RoleRepository;
import com.example.gestiondents.repository.UserRepository;
import com.example.gestiondents.service.AdminService;
import com.example.gestiondents.service.RoleService;

@SpringBootApplication
public class GestionDentsApplication {

	//@Autowired
	//private UserRepository userRepository;
	//@Autowired
	//private RoleRepository roleRepository;
	
	@Autowired
    private RoleService roleService;

    @Autowired
    private AdminService adminService;

    
    public static void main(String[] args) {
        SpringApplication.run(GestionDentsApplication.class, args);
    }
    
    @Bean
	CommandLineRunner init() {
		return args -> {
			
			
			/*Role roleAdmin = new Role();
			roleAdmin.setName("ROLE_ADMIN");
			roleService.create(roleAdmin);
			
			Role roleProf = new Role();
			roleProf.setName("ROLE_PROFESSOR");
			roleService.create(roleProf);
			
			Role roleStudent = new Role();
			roleStudent.setName("ROLE_STUDENT");
			roleService.create(roleStudent);
						
			var admin = new Admin();
    		admin.setRoles(List.of(roleAdmin));
        	
        	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        	admin.setEmail("admin@gmail.com");
        	admin.setFirstName("Admin");
        	admin.setLastName("Admin");
        	admin.setTypeUser("ADMIN");
        	admin.setUsername("admin");
        	admin.setPassword(passwordEncoder.encode("admin"));
        	adminService.create(admin);
        	*/
		};
	}
}
