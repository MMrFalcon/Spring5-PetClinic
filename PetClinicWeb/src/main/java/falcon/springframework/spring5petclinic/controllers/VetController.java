package falcon.springframework.spring5petclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vets")
public class VetController {

    @RequestMapping({"","/","/index","/index.html"})
    String vetsList(){
        return "vets/index";
    }
}
