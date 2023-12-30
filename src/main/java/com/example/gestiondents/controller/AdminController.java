package com.example.gestiondents.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestiondents.entities.Admin;
import com.example.gestiondents.service.AdminService;
import com.example.gestiondents.service.RoleService;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

	private final RoleService roleService;
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService, RoleService roleService) {
        this.adminService = adminService;
        this.roleService = roleService;
    }

    @PostMapping("/create")
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
    	var role = this.roleService.findByName("ROLE_ADMIN");
    	if(role != null) {
    		admin.setRoles(List.of(role));
    	}
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
    	admin.setTypeUser("ADMIN");
        Admin createdAdmin = adminService.create(admin);
        return new ResponseEntity<>(createdAdmin, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAdmin(@RequestBody Admin admin) {
        boolean deleted = adminService.delete(admin);
        if (deleted) {
            return new ResponseEntity<>("Admin deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to delete Admin", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Admin> updateAdmin(@RequestBody Admin admin) {
        Admin updatedAdmin = adminService.update(admin);
        return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable("id") int id) {
        Admin admin = adminService.findById(id);
        if (admin != null) {
            return new ResponseEntity<>(admin, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminService.findAll();
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }
}