package falcon.springframework.spring5petclinic.services.map;

import falcon.springframework.spring5petclinic.model.PetType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PetTypeServiceMapTest {

    private static final String PET_TYPE = "Dog";
    private static final Long PET_TYPE_ID = 1L;

    private PetType savedPetType;

    private PetTypeServiceMap petTypeService;

    @BeforeEach
    void setUp() {
        petTypeService = new PetTypeServiceMap();
        savedPetType = petTypeService.save(PetType.builder().name(PET_TYPE).id(PET_TYPE_ID).build());
    }

    @Test
    void findAll() {
        Set<PetType> petTypes = petTypeService.findAll();
        assertEquals(petTypes.size(), 1);
        assertTrue(petTypes.contains(savedPetType));
    }

    @Test
    void delete() {
        petTypeService.delete(savedPetType);
        assertEquals(0, petTypeService.findAll().size());
    }

    @Test
    void deleteById() {
        petTypeService.deleteById(PET_TYPE_ID);
        assertEquals(0, petTypeService.findAll().size());
    }

    @Test
    void saveWithExistingId() {
        PetType petType = PetType.builder().id(2L).build();
        PetType secondSavedPetType = petTypeService.save(petType);

        assertNotNull(secondSavedPetType);
        assertEquals(secondSavedPetType.getId(), petType.getId());
    }

    @Test
    void saveWithoutId() {
        PetType petTypeWithoutId = PetType.builder().build();
        PetType savedPetType = petTypeService.save(petTypeWithoutId);
        assertNotNull(savedPetType);
    }

    @Test
    void saveNull() {
        final String exceptionMessage = "Object cannot be null";
        Exception exception = assertThrows(RuntimeException.class, () -> petTypeService.save(null));
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    void findById() {
        PetType foundPetType = petTypeService.findById(PET_TYPE_ID);
        assertNotNull(foundPetType);
        assertEquals(foundPetType.getId(), PET_TYPE_ID);
    }
}