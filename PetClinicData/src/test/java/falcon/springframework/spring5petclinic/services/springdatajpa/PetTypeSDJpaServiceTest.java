package falcon.springframework.spring5petclinic.services.springdatajpa;

import falcon.springframework.spring5petclinic.model.PetType;
import falcon.springframework.spring5petclinic.repositories.PetTypeRepository;
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
class PetTypeSDJpaServiceTest {

    private final static Long PET_TYPE_ID = 1L;

    private PetType petType;

    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    PetTypeSDJpaService petTypeService;

    @BeforeEach
    void setUp() {
        petType = PetType.builder().id(PET_TYPE_ID).build();
    }

    @Test
    void findAll() {
        Set<PetType> petTypes = new HashSet<>();
        petTypes.add(petType);
        petTypes.add(PetType.builder().id(2L).build());

        when(petTypeRepository.findAll()).thenReturn(petTypes);
        Set<PetType> foundPetTypes = petTypeService.findAll();

        assertNotNull(foundPetTypes);
        assertEquals(2, foundPetTypes.size());
    }

    @Test
    void findById() {
        when(petTypeRepository.findById(PET_TYPE_ID)).thenReturn(Optional.of(petType));

        PetType foundPetType = petTypeService.findById(PET_TYPE_ID);

        assertNotNull(foundPetType);
        assertEquals(petType.getId(), foundPetType.getId());
        verify(petTypeRepository, times(1)).findById(PET_TYPE_ID);
    }

    @Test
    void findByIdNotPresent() {
        final String exceptionMessage = "Entity doesn't exist";
        when(petTypeRepository.findById(anyLong())).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> petTypeService.findById(2L));
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    void save() {
        when(petTypeRepository.save(any())).thenReturn(petType);
        assertNotNull(petTypeService.save(petType));
        verify(petTypeRepository).save(any());
    }

    @Test
    void deleteById() {
        petTypeService.deleteById(1L);
        verify(petTypeRepository).deleteById(anyLong());
    }

    @Test
    void delete() {
        petTypeService.delete(petType);
        verify(petTypeRepository).delete(any());
    }
}