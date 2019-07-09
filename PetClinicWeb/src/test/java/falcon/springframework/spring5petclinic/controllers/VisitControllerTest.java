package falcon.springframework.spring5petclinic.controllers;

import falcon.springframework.spring5petclinic.model.Pet;
import falcon.springframework.spring5petclinic.model.Visit;
import falcon.springframework.spring5petclinic.services.PetService;
import falcon.springframework.spring5petclinic.services.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    private static final Long VISIT_ID = 1L;
    private static final Long PET_ID = 1L;
    private static final String CREATE_OR_UPDATE_VISIT_FORM_URL = "pets/createOrUpdateVisitForm";

    private Pet pet;
    private Visit visit;

    @Mock
    private VisitService visitService;

    @Mock
    private PetService petService;

    @InjectMocks
    private VisitController visitController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        pet = Pet.builder().id(PET_ID).build();
        visit = Visit.builder().id(VISIT_ID).build();
        mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();
    }

    @Test
    void initNewVisitForm() throws Exception{
        when(petService.findById(PET_ID)).thenReturn(pet);

        mockMvc.perform(get("/owners/1/pets/1/visits/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pet", "visit"))
                .andExpect(view().name(CREATE_OR_UPDATE_VISIT_FORM_URL));
    }

    @Test
    void processNewVisitForm() throws  Exception{

        mockMvc.perform(post("/owners/1/pets/1/visits/new")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("date", "2019-07-09")
        .param("description", "Visit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"))
                .andExpect(model().attributeExists("visit"));
    }

    @Test
    void initUpdateVisitFrom() throws Exception {
        when(visitService.findById(VISIT_ID)).thenReturn(visit);
        when(petService.findById(PET_ID)).thenReturn(pet);
        mockMvc.perform(get("/owners/1/pets/1/visits/1/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("visit", "pet"))
                .andExpect(view().name(CREATE_OR_UPDATE_VISIT_FORM_URL));
    }

    @Test
    void processUpdateVisitForm() throws Exception {
        mockMvc.perform(post("/owners/1/pets/1/visits/1/edit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("date", "2019-07-09")
                .param("description", "Edited Visit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"))
                .andExpect(model().attributeExists("visit"));
    }
}