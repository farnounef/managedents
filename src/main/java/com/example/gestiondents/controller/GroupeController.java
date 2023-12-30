
package  com.example.gestiondents.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.gestiondents.entities.Groupe;
import com.example.gestiondents.entities.PW;
import com.example.gestiondents.repository.GroupeRepository;
import com.example.gestiondents.repository.PWRepository;
import com.example.gestiondents.repository.ProfessorRepository;

@Controller
public class GroupeController {

    @Autowired
    private GroupeRepository groupeRepository;
    @Autowired
    private ProfessorRepository profRepository;
    @Autowired
    private PWRepository pwRepository;

    
    @GetMapping("/signupgroupe")
    public String showSignUpFormgroupe(Groupe groupe , BindingResult result, Model model) {
    	model.addAttribute("groupes", groupeRepository.findAll());
    	model.addAttribute("prof", profRepository.findAll());
        return "index5";
    }

    @PostMapping("/addgroupe")
    public String addGroupe(Groupe groupe, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-groupe";
        }

        groupeRepository.save(groupe);
        model.addAttribute("groupes", groupeRepository.findAll());
        return "index5";	
    }
    
    @GetMapping("/addgroupe")
    public String showAddGroupeForm(Groupe groupe, Model model) {
        model.addAttribute("groupe", new Groupe());
        model.addAttribute("professors", profRepository.findAll());
        
        return "add-groupe";
    }

    

    @GetMapping("/editgroupe/{id}")
    public String showUpdateFormgroupe(@PathVariable("id") int id, Model model) {
        Groupe groupe = groupeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid groupe Id:" + id));
        model.addAttribute("groupe", groupe);
        model.addAttribute("professors", profRepository.findAll());
        return "update-groupe";
    }

    @PostMapping("/updategroupe/{id}")
    public String updateGroupe(@PathVariable("id") int id, Groupe groupe, BindingResult result, Model model) {
        if (result.hasErrors()) {
            groupe.setId(id);
            return "update-groupe";
        }

        groupeRepository.save(groupe);
        model.addAttribute("groupes", groupeRepository.findAll());
        return "index5";
    }

    @GetMapping("/deletegroupe/{id}")
    public String deleteGroupe(@PathVariable("id") int id, Model model) {
        Groupe groupe = groupeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid groupe Id:" + id));
        groupeRepository.delete(groupe);
        model.addAttribute("groupes", groupeRepository.findAll());
        return "index5";
    }
    
    @GetMapping("/groupe-details/{id}")
    public String showGroupDetails(@PathVariable("id") int id, Model model) {
        Groupe groupe = groupeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid groupe Id:" + id));

        model.addAttribute("group", groupe);

        // Récupérer les PW associés à ce groupe
        List<PW> pwList = pwRepository.findPWsByGroup(groupe);
        model.addAttribute("pwList", pwList);

        return "groupe-details";
    }
}
