package falcon.springframework.spring5petclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/owners")
public class OwnerController {

    @RequestMapping({"","/", "/index", "/index.html"})
    String ownersList() {
        return "owners/index";
    }
}
