package falcon.springframework.spring5petclinic.services.map;

import falcon.springframework.spring5petclinic.model.Speciality;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SpecialityServiceMapTest {

    private static final Long SPECIALITY_ID = 1L;
    private static final String SPECIALITY_DESCIRPTION = "Surgery";

    private Speciality savedSpeciality;

    private SpecialityServiceMap specialityService;

    @BeforeEach
    void setUp() {
        specialityService = new SpecialityServiceMap();
        savedSpeciality = specialityService.save(Speciality.builder().id(SPECIALITY_ID).
                description(SPECIALITY_DESCIRPTION).build());
    }

    @Test
    void findAll() {
        Set<Speciality> specialities = specialityService.findAll();
        assertEquals(specialities.size(), 1);
        assertTrue(specialities.contains(savedSpeciality));
    }

    @Test
    void delete() {
        specialityService.delete(savedSpeciality);
        assertEquals(0, specialityService.findAll().size());
    }

    @Test
    void deleteById() {
        specialityService.deleteById(SPECIALITY_ID);
        assertEquals(0, specialityService.findAll().size());
    }

    @Test
    void saveWithExistingId() {
        Speciality speciality= Speciality.builder().id(2L).build();
        Speciality secondSavedPetType = specialityService.save(speciality);

        assertNotNull(secondSavedPetType);
        assertEquals(secondSavedPetType.getId(), speciality.getId());
    }

    @Test
    void saveWithoutId() {
        Speciality specialityWithoutId = Speciality.builder().build();
        Speciality savedSpeciality = specialityService.save(specialityWithoutId);
        assertNotNull(savedSpeciality);
    }

    @Test
    void saveNull() {
        final String exceptionMessage = "Object cannot be null";
        Exception exception = assertThrows(RuntimeException.class, () -> specialityService.save(null));
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    void findById() {
        Speciality foundSpeciality = specialityService.findById(SPECIALITY_ID);
        assertNotNull(foundSpeciality);
        assertEquals(savedSpeciality.getId(), savedSpeciality.getId());
        assertEquals(savedSpeciality.getDescription(), savedSpeciality.getDescription());
    }
}