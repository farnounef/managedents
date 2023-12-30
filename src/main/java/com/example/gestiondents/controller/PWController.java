
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
import com.example.gestiondents.repository.ToothRepository;

@Controller
public class PWController {

    @Autowired
    private PWRepository pwRepository;

    @Autowired
    private ToothRepository toothRepository;

    @Autowired
    private GroupeRepository groupeRepository;

    @GetMapping("/signuppw")
    public String showSignUpFormpw(Model model) {
        model.addAttribute("pws", pwRepository.findAll());
        model.addAttribute("tooths", toothRepository.findAll());

        return "index4";
    }

    @PostMapping("/addpw")
    public String addPW(@ModelAttribute("pw") PW pw, BindingResult result, Model model, @RequestParam("groupPw") List<Integer> groupIds) {
        if (result.hasErrors()) {
            return "add-pw";
        }

        List<Groupe> selectedGroups = groupeRepository.findAllById(groupIds);
        pw.setGroupPw(selectedGroups);
        for (Groupe group : selectedGroups) {
            group.getPwGroup().add(pw);
        }
        pwRepository.save(pw);
        return "redirect:/signuppw";
    }

    @GetMapping("/addpw")
    public String showAddPWForm(Model model) {
        model.addAttribute("pw", new PW());
        model.addAttribute("tooths", toothRepository.findAll());
        model.addAttribute("groupes", groupeRepository.findAll());
        
        return "add-pw";
    }

    @GetMapping("/editpw/{id}")
    public String showUpdateFormpw(@PathVariable("id") int id, Model model) {
        PW pw = pwRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid pw Id:" + id));
        model.addAttribute("pw", pw);
        model.addAttribute("tooths", toothRepository.findAll());
        return "update-pw";
    }

    @PostMapping("/updatepw/{id}")
    public String updatePW(@PathVariable("id") int id, @ModelAttribute("pw") PW pw, BindingResult result, Model model) {
        if (result.hasErrors()) {
            pw.setId(id);
            return "update-pw";
        }
        pwRepository.save(pw);
        return "redirect:/signuppw";
    }

    @GetMapping("/deletepw/{id}")
    public String deletePW(@PathVariable("id") int id) {
        PW pw = pwRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid pw Id:" + id));
        pwRepository.delete(pw);
        return "redirect:/signuppw";
    }
}