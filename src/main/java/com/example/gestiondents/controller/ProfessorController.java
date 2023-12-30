
package  com.example.gestiondents.controller;

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


// import com.example.gestiondents.entities.MyUserDetails;
import com.example.gestiondents.entities.Professor;
import com.example.gestiondents.repository.ProfessorRepository;
import com.example.gestiondents.service.RoleService;

@Controller
public class ProfessorController {

	private final RoleService roleService;
	
    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    public ProfessorController(RoleService roleService){
    	this.roleService = roleService;
    }
    
    @GetMapping("/signups")
    public String showSignUpForm(Professor professor , BindingResult result, Model model) {
        List<Professor> profs = professorRepository.findAll();
        profs.forEach(studentItem -> {
            if (studentItem.getPhoto() != null) {
                String base64Image = Base64.getEncoder().encodeToString(studentItem.getPhoto());
                studentItem.setImageBase64(base64Image);
            }
        });
    	model.addAttribute("professors", professorRepository.findAll());
        return "index";
    }

    @PostMapping("/addprofessor")
    public String addProfessor(@RequestParam(value = "file", required = false) MultipartFile file,Professor professor, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-professor";
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        professor.setPassword(passwordEncoder.encode(professor.getPassword()));
        var role = this.roleService.findByName("ROLE_PROFESSOR");
    	if(role != null) {
    		
    		professor.setRoles(List.of(role));
    	}
    	professor.setTypeUser("PROFESSOR");
        
       try {
           
            if (file != null && !file.isEmpty()) {
                byte[] newImage = file.getBytes();
                professor.setPhoto(newImage); 
            }
        
        professorRepository.save(professor);
        
        } catch (IOException e) {
            // Gérer les exceptions liées à la manipulation de fichiers (IOException)
            // Ajoutez ici le code pour gérer l'erreur, par exemple, renvoyer à une page d'erreur.
        }
        List<Professor> professors = professorRepository.findAll();
        professors.forEach(studentItem -> {
            if (studentItem.getPhoto() != null) {
                String base64Image = Base64.getEncoder().encodeToString(studentItem.getPhoto());
                studentItem.setImageBase64(base64Image);
            }
        });
        model.addAttribute("professors", professorRepository.findAll());
        return "index";
    }
    
    @GetMapping("/addprofessor")
    public String showAddProfessorForm(Professor professor, Model model) {
    	var prof = new Professor();
    	var role = this.roleService.findByName("ROLE_PROFESSOR");
    	if(role != null) {
    		
    		prof.setRoles(List.of(role));
    	}
    	
    	prof.setTypeUser("PROFESSOR");
        model.addAttribute("professor", prof);
        return "add-professor";
    }

    

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid professor Id:" + id));
        
        professor.setPassword(null);
        model.addAttribute("professor", professor);
        return "update-professor";
    }

    @PostMapping("/update/{id}")
    public String updateProfessor(@PathVariable("id") int id, Professor professor , @RequestParam(value = "file", required = false) MultipartFile file,BindingResult result, Model model) {
    	if (result.hasErrors()) {
            professor.setId(id);
            return "update-professor";
        }
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
        	    	
        	professor.setId(id);
        	
        	var passwordIn = professor.getPassword();
        	if(passwordIn != null && passwordIn.length() > 0)
        	{
        		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        		professor.setPassword(passwordEncoder.encode(passwordIn));	
        	}
        	else 
        	{
        		var professorDb = professorRepository.findById((int)professor.getId());
        		professor.setPassword(professorDb.get().getPassword());
        	}
        	
            var role = this.roleService.findByName("ROLE_PROFESSOR");
        	if(role != null) {
        		professor.setRoles(null);
        		professor.setRoles(List.of(role));
        	}
        	Professor std = professorRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("test"));
        try {
            // Vérifiez si un fichier a été téléchargé
            if (!file.isEmpty() && file!=null) {
                byte[] newImage = file.getBytes();
                professor.setPhoto(newImage); // Mettre à jour l'image
            }else {
            	professor.setPhoto(std.getPhoto());
            }
            
        	professor.setTypeUser("PROFESSOR");
        	professorRepository.save(professor);
             } catch (IOException e) {
            // Gérer les exceptions liées à la manipulation de fichiers (IOException)
            // Ajoutez ici le code pour gérer l'erreur, par exemple, renvoyer à une page d'erreur.
        }

        	// Le role de l'utilisateur connecté
            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
            if (roles.contains("ROLE_PROFESSOR")) {
            	return "welcome";
            }
            
            // Admin qu'est connecté
            model.addAttribute("professors", professorRepository.findAll());
            List<Professor> profs = professorRepository.findAll();
        profs.forEach(studentItem -> {
            if (studentItem.getPhoto() != null) {
                String base64Image = Base64.getEncoder().encodeToString(studentItem.getPhoto());
                studentItem.setImageBase64(base64Image);
            }
        });
            return "index";
        	
        }
        
        return "login";
    }

    @GetMapping("/delete/{id}")
    public String deleteProfessor(@PathVariable("id") int id, Model model) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid professor Id:" + id));
        professorRepository.delete(professor);
        List<Professor> profs = professorRepository.findAll();
        profs.forEach(studentItem -> {
            if (studentItem.getPhoto() != null) {
                String base64Image = Base64.getEncoder().encodeToString(studentItem.getPhoto());
                studentItem.setImageBase64(base64Image);
            }
        });
        model.addAttribute("professors", professorRepository.findAll());
        return "index";
    }
}
