package falcon.springframework.spring5petclinic.controllers;

import falcon.springframework.spring5petclinic.model.Person;
import falcon.springframework.spring5petclinic.model.Vet;
import falcon.springframework.spring5petclinic.services.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/vets")
public class VetController {

    private final static String VETS_LIST_VIEW_URL = "vets/index";
    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @GetMapping("/vets.html")
    String vetsList(Model model){
        List<Vet> vetsList = new ArrayList<>(vetService.findAll());
        vetsList.sort(Comparator.comparing(Person::getFirstName));

        model.addAttribute("vets", vetsList);

        return VETS_LIST_VIEW_URL ;
    }
}
