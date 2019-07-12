package falcon.springframework.spring5petclinic.controllers;

import falcon.springframework.spring5petclinic.model.Vet;
import falcon.springframework.spring5petclinic.services.VetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VetControllerTest {

    private final static String VETS_LIST_VIEW_URL = "vets/index";
    private final static String FIRST_VET_NAME = "Alberta";
    private final static String SECOND_VET_NAME = "Jack";

    @Mock
    VetService vetService;

    @InjectMocks
    VetController vetController;

    private MockMvc mockMvc;

    private Set<Vet> vets;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(vetController).build();

        vets = new HashSet<>();
        vets.add(Vet.builder().firstName(FIRST_VET_NAME).build());
        vets.add(Vet.builder().firstName(SECOND_VET_NAME).build());

    }

    @Test
    void vetsList() throws Exception {
        when(vetService.findAll()).thenReturn(vets);

        mockMvc.perform(get("/vets/vets.html"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("vets"))
                .andExpect(view().name(VETS_LIST_VIEW_URL));

        verify(vetService).findAll();
    }

    @Test
    void getVetsJSON() throws Exception {
        when(vetService.findAll()).thenReturn(vets);

        mockMvc.perform(get("/vets/api/vets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is(FIRST_VET_NAME)))
                .andExpect(jsonPath("$[1].firstName", is(SECOND_VET_NAME)));

        verify(vetService).findAll();
    }
}