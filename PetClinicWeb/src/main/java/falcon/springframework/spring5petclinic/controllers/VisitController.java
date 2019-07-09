package falcon.springframework.spring5petclinic.controllers;

import falcon.springframework.spring5petclinic.model.Pet;
import falcon.springframework.spring5petclinic.model.Visit;
import falcon.springframework.spring5petclinic.services.PetService;
import falcon.springframework.spring5petclinic.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.util.Map;

@Controller
@RequestMapping("/owners/{ownerId}/pets")
public class VisitController {

    private static final String VIEWS_VISIT_CREATE_OR_UPDATE_FORM = "pets/createOrUpdateVisitForm";

    private final VisitService visitService;
    private final PetService petService;

    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    @InitBinder
    public void dataBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");

        dataBinder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException{
                setValue(LocalDate.parse(text));
            }
        });
    }

    @ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable Long petId, Map<String, Object> model) {
        Pet pet = petService.findById(petId);
        model.put("pet", pet);
        Visit visit = new Visit();
        visit.setPet(pet);
        return visit;
    }

    @GetMapping("/{petId}/visits/new")
    public String initNewVisitForm() {
        return VIEWS_VISIT_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/{petId}/visits/new")
    public String processNewVisitForm(@Valid Visit visit, BindingResult result, @PathVariable Long ownerId) {
        if (result.hasErrors()) {
            return VIEWS_VISIT_CREATE_OR_UPDATE_FORM;
        } else {
            visitService.save(visit);
            return "redirect:/owners/" + ownerId;
        }
    }

    @GetMapping("/{petId}/visits/{visitId}/edit")
    public String initUpdateVisitForm(@PathVariable Long visitId, Map<String, Object> model) {
        Visit foundVisit = visitService.findById(visitId);
        model.put("visit", foundVisit);
        return VIEWS_VISIT_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/{petId}/visits/{visitId}/edit")
    public String processUpdateVisitForm(@Valid Visit visit, BindingResult result, @PathVariable Long ownerId,
                                         @PathVariable Long visitId) {

        if (result.hasErrors()) {
            return VIEWS_VISIT_CREATE_OR_UPDATE_FORM;
        } else {
            visit.setId(visitId);
            visitService.save(visit);
            return "redirect:/owners/" + ownerId;
        }
    }
}
