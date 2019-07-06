package falcon.springframework.spring5petclinic.services.map;

import falcon.springframework.spring5petclinic.model.Vet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class VetServiceMapTest {

    private static  final Long VET_ID = 1L;

    private Vet savedVet;

    private VetServiceMap vetService;

    @BeforeEach
    void setUp() {
        vetService = new VetServiceMap();
        savedVet = vetService.save(Vet.builder().id(VET_ID).build());
    }

    @Test
    void findAll() {
        Set<Vet> vets = vetService.findAll();
        assertEquals(vets.size(), 1);
        assertTrue(vets.contains(savedVet));
    }

    @Test
    void delete() {
        vetService.delete(savedVet);
        assertEquals(0, vetService.findAll().size());
    }

    @Test
    void deleteById() {
        vetService.deleteById(VET_ID);
        assertEquals(0, vetService.findAll().size());
    }

    @Test
    void saveWithExistingId() {
        Vet vet= Vet.builder().id(2L).build();
        Vet secondVet = vetService.save(vet);

        assertNotNull(secondVet);
        assertEquals(secondVet.getId(), vet.getId());
    }

    @Test
    void saveWithoutId() {
        Vet vetWithoutId = Vet.builder().build();
        Vet savedVet = vetService.save(vetWithoutId);
        assertNotNull(savedVet);
    }

    @Test
    void saveNull() {
        final String exceptionMessage = "Object cannot be null";
        Exception exception = assertThrows(RuntimeException.class, () -> vetService.save(null));
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    void findById() {
        Vet foundVet = vetService.findById(VET_ID);
        assertNotNull(foundVet);
        assertEquals(foundVet.getId(), VET_ID);
    }
}