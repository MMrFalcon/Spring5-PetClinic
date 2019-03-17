package falcon.springframework.spring5petclinic.controllers;

import falcon.springframework.spring5petclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/owners")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @RequestMapping({"","/", "/index", "/index.html", "/find"})
    String ownersList(Model model)
    {
        model.addAttribute("owners", ownerService.findAll());

        return "owners/index";
    }

}
