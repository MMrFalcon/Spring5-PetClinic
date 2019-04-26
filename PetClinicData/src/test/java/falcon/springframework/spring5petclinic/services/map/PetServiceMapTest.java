package falcon.springframework.spring5petclinic.services.map;

import falcon.springframework.spring5petclinic.model.Pet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PetServiceMapTest {
    private static final String NAME = "Rex";
    private static final Long FIRST_ID = 1L;

    private PetServiceMap petService;

    @BeforeEach
    void setUp() {
        petService = new PetServiceMap();

        petService.save(Pet.builder().name(NAME).id(FIRST_ID).build());
    }


    @Test
    void findAll() {
        Set<Pet> pets = petService.findAll();
        assertEquals(1L, pets.size());
    }

    @Test
    void delete() {
        petService.delete(petService.findById(FIRST_ID));
        assertEquals(0, petService.findAll().size());
    }

    @Test
    void deleteById() {
        assertEquals(1, petService.findAll().size());
        petService.deleteById(FIRST_ID);
        assertEquals(0,petService.findAll().size());
    }

    @Test
    void saveWithExistingId() {
        final Long secondId = 2L;
        Pet pet = Pet.builder().id(secondId).build();
        Pet savedPet = petService.save(pet);
        assertEquals(secondId, savedPet.getId());
    }

    @Test
    void findById() {
        Pet pet = petService.findById(FIRST_ID);
        assertNotNull(pet);
        assertEquals(pet.getId(), FIRST_ID);
    }
}