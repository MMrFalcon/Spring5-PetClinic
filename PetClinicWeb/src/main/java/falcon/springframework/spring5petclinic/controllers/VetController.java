package falcon.springframework.spring5petclinic.controllers;

import falcon.springframework.spring5petclinic.services.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vets")
public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @RequestMapping({"","/","/index","/index.html"})
    String vetsList(Model model){

        model.addAttribute("vets", vetService.findAll());

        return "vets/index";
    }
}
