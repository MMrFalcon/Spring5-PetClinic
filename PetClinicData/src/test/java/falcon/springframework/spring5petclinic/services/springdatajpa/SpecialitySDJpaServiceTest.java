package falcon.springframework.spring5petclinic.services.springdatajpa;

import falcon.springframework.spring5petclinic.model.Speciality;
import falcon.springframework.spring5petclinic.repositories.SpecialityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    private static Long SPECIALITY_ID = 1L;

    private Speciality speciality;

    @Mock
    SpecialityRepository specialityRepository;

    @InjectMocks
    SpecialitySDJpaService specialityService;

    @BeforeEach
    void setUp() {
        speciality = Speciality.builder().id(SPECIALITY_ID).build();
    }

    @Test
    void findAll() {
        Set<Speciality> specialities = new HashSet<>();
        specialities.add(speciality);
        specialities.add(Speciality.builder().id(2L).build());

        when(specialityRepository.findAll()).thenReturn(specialities);
        Set<Speciality> foundSpecialities = specialityService.findAll();

        assertNotNull(foundSpecialities);
        assertEquals(2, foundSpecialities.size());
    }

    @Test
    void findById() {
        when(specialityRepository.findById(SPECIALITY_ID)).thenReturn(Optional.of(speciality));

        Speciality foundSpeciality = specialityService.findById(SPECIALITY_ID);

        assertNotNull(foundSpeciality);
        assertEquals(speciality.getId(), foundSpeciality.getId());
        verify(specialityRepository, times(1)).findById(SPECIALITY_ID);
    }

    @Test
    void findByIdNotPresent() {
        final String exceptionMessage = "Entity doesn't exist";
        when(specialityRepository.findById(anyLong())).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> specialityService.findById(2L));
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    void save() {
        when(specialityRepository.save(any())).thenReturn(speciality);
        assertNotNull(specialityService.save(speciality));
        verify(specialityRepository).save(any());
    }

    @Test
    void deleteById() {
        specialityService.deleteById(1L);
        verify(specialityRepository).deleteById(anyLong());
    }

    @Test
    void delete() {
        specialityService.delete(speciality);
        verify(specialityRepository).delete(any());
    }
}