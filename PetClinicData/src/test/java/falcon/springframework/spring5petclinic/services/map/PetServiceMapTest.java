package falcon.springframework.spring5petclinic.services.map;

import falcon.springframework.spring5petclinic.model.Pet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PetServiceMapTest {
    private static final String NAME = "Rex";
    private static final Long FIRST_ID = 1L;

    private Pet savedPet;

    private PetServiceMap petService;

    @BeforeEach
    void setUp() {
        petService = new PetServiceMap();
        savedPet = petService.save(Pet.builder().name(NAME).id(FIRST_ID).build());
    }


    @Test
    void findAll() {
        Set<Pet> pets = petService.findAll();
        assertEquals(1L, pets.size());
    }

    @Test
    void delete() {
        petService.delete(savedPet);
        assertEquals(0, petService.findAll().size());
    }

    @Test
    void deleteById() {
        petService.deleteById(FIRST_ID);
        assertEquals(0, petService.findAll().size());
    }

    @Test
    void saveWithExistingId() {
        final Long secondId = 2L;
        Pet pet = Pet.builder().id(secondId).build();
        Pet savedPet = petService.save(pet);
        assertEquals(secondId, savedPet.getId());
    }

    @Test
    void saveWithoutId() {
        Pet savedPet = petService.save(Pet.builder().build());
        assertNotNull(savedPet);
        assertNotNull(savedPet.getId());
    }

    @Test
    void saveWithoutObject() {
        final String exceptionMessage = "Object cannot be null";
        Exception exception = assertThrows(RuntimeException.class, () -> petService.save(null));
        assertEquals(exceptionMessage, exception.getMessage());
    }


    @Test
    void findById() {
        Pet pet = petService.findById(FIRST_ID);
        assertNotNull(pet);
        assertEquals(pet.getId(), FIRST_ID);
    }
}