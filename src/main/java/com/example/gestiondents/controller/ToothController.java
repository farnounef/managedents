
package  com.example.gestiondents.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.gestiondents.entities.Tooth;
import com.example.gestiondents.repository.ToothRepository;

@Controller
@RequestMapping("/tooth")
public class ToothController {

    @Autowired
    private ToothRepository toothRepository;

   
    @GetMapping("/signup")
    public String showSignUpFormDent(Tooth tooth , BindingResult result, Model model) {
    	model.addAttribute("tooths", toothRepository.findAll());
        return "index3";
    }

    @PostMapping("/addtooth")
    public String addTooth(Tooth tooth, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-tooth";
        }

        toothRepository.save(tooth);
        model.addAttribute("tooths", toothRepository.findAll());
        return "index3";
    }
    
    @GetMapping("/addtooth")
    public String showAddToothForm(Tooth tooth, Model model) {
        model.addAttribute("tooth", new Tooth());
        return "add-tooth";
    }

    

    @GetMapping("/edit/{id}")
    public String showUpdateFormDent(@PathVariable("id") int id, Model model) {
        Tooth tooth = toothRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid tooth Id:" + id));
        model.addAttribute("tooth", tooth);
        return "update-tooth";
    }

    @PostMapping("/update/{id}")
    public String updateTooth(@PathVariable("id") int id, Tooth tooth, BindingResult result, Model model) {
        if (result.hasErrors()) {
            tooth.setId(id);
            return "update-tooth";
        }

        toothRepository.save(tooth);
        model.addAttribute("tooths", toothRepository.findAll());
        return "index3";
    }

    @GetMapping("/delete/{id}")
    public String deleteTooth(@PathVariable("id") int id, Model model) {
        Tooth tooth = toothRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid tooth Id:" + id));
        toothRepository.delete(tooth);
        model.addAttribute("tooths", toothRepository.findAll());
        return "index3";
    }
}
