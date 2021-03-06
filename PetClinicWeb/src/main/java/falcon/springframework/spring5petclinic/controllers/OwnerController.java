package falcon.springframework.spring5petclinic.controllers;

import falcon.springframework.spring5petclinic.model.Owner;
import falcon.springframework.spring5petclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/owners")
public class OwnerController {

    private static final String VIEW_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

   @GetMapping
   public String processFindForm(Owner owner, BindingResult bindingResult, Model model) {

       if (owner.getLastName() == null) {
           owner.setLastName("");
       }

       List<Owner> owners = ownerService.findAllByLastNameLike("%"+ owner.getLastName() + "%");

       if (owners.isEmpty()) {

           bindingResult.rejectValue("lastName", "notFound", "not found");
           return "owners/findOwners";

       } else if (owners.size() == 1) {

           owner = owners.get(0);
           return "redirect:/owners/" + owner.getId();

       } else {

           model.addAttribute("selections", owners);
           return "owners/ownersList";

       }
   }

    @GetMapping("/find")
    public String findOwners(Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return "owners/findOwners";
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable Long ownerId) {
        ModelAndView mav = new ModelAndView("owners/ownerDetails");
        mav.addObject(ownerService.findById(ownerId));
        return mav;
    }

    @GetMapping("/new")
    public String initCreationForm(Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return VIEW_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid Owner owner, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return VIEW_OWNER_CREATE_OR_UPDATE_FORM;
        } else {
            Owner savedOwner =  ownerService.save(owner);
            return "redirect:/owners/" + savedOwner.getId();
        }
    }

    @GetMapping("/{ownerId}/edit")
    public String initUpdateOwnerForm(@PathVariable Long ownerId, Model model) {
        model.addAttribute(ownerService.findById(ownerId));
        return VIEW_OWNER_CREATE_OR_UPDATE_FORM;
    }


    @PostMapping("/{ownerId}/edit")
    public String processUpdateOwnerForm(@Valid Owner owner, BindingResult result, @PathVariable Long ownerId) {
        if (result.hasErrors()) {
            return VIEW_OWNER_CREATE_OR_UPDATE_FORM;
        } else {
            owner.setId(ownerId);
            Owner savedOwner = ownerService.save(owner);
            return "redirect:/owners/" + savedOwner.getId();
        }
    }

}
