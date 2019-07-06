package falcon.springframework.spring5petclinic.services.map;

import falcon.springframework.spring5petclinic.model.Owner;
import falcon.springframework.spring5petclinic.model.Pet;
import falcon.springframework.spring5petclinic.model.Visit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class VisitServiceMapTest {

    private static final String VISIT_DESCRIPTION = "Text";
    private static final Long ID = 1L;

    private Visit savedVisit;

    private Pet pet;

    private VisitServiceMap visitService;

    @BeforeEach
    void setUp() {
        visitService = new VisitServiceMap();

        Owner petOwner = Owner.builder().id(ID).build();
        pet = Pet.builder().id(ID).owner(petOwner).build();


        savedVisit = visitService.save(Visit.builder()
                .id(ID).pet(pet).description(VISIT_DESCRIPTION).date(LocalDate.now()).build());
    }

    @Test
    void findAll() {
        Set<Visit> visits = visitService.findAll();
        assertEquals(1, visits.size());
        assertTrue(visits.contains(savedVisit));
    }

    @Test
    void delete() {
        visitService.delete(savedVisit);
        assertEquals(0, visitService.findAll().size());
    }

    @Test
    void deleteById() {
        visitService.deleteById(ID);
        assertEquals(0, visitService.findAll().size());
    }

    @Test
    void saveWithExistingId() {
        Visit visit= Visit.builder().id(2L).pet(pet).build();
        Visit secondVisit = visitService.save(visit);

        assertNotNull(secondVisit);
        assertEquals(secondVisit.getId(), visit.getId());
    }

    @Test
    void saveWithoutId() {
        Visit visitWithoutId = Visit.builder().pet(pet).build();
        Visit savedVisit = visitService.save(visitWithoutId);
        assertNotNull(savedVisit);
    }

    @Test
    void saveNull() {
        final String exceptionMessage = "Visit entity cannot be null";
        Exception exception = assertThrows(RuntimeException.class, () -> visitService.save(null));
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    void findById() {
        Visit foundVisit = visitService.findById(ID);
        assertEquals(foundVisit.getId(), ID);
        assertEquals(foundVisit.getDescription(), savedVisit.getDescription());
    }
}