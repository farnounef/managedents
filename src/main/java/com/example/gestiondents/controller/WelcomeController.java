package com.example.gestiondents.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.gestiondents.entities.MyUserDetails;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class WelcomeController {

	/*
    @GetMapping("/")
    public String showWelcomePage() {
        return "welcome";
    }*/
    
    @GetMapping
    public String index() {
        return "welcome";
    }
    
    
    @GetMapping("/csrf-totken")
    public CsrfToken csrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }

    @GetMapping("/example")
    public String example() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
            System.out.println(user);
            return "Hello, " + authentication.getName() + "!";
        } else {
            return "Not authenticated";
        }
    }

	
	
}
