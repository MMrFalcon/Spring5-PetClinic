package falcon.springframework.spring5petclinic.services.springdatajpa;

import falcon.springframework.spring5petclinic.model.Pet;
import falcon.springframework.spring5petclinic.repositories.PetRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetSDJpaServiceTest {

    private static final Long PET_ID = 1L;
    private Pet pet;

    @Mock
    PetRepository petRepository;

    @InjectMocks
    PetSDJpaService petService;

    @BeforeEach
    void setUp() {
        pet = Pet.builder().id(PET_ID).build();
    }

    @Test
    void findAll() {
        Set<Pet> pets = new HashSet<>();
        pets.add(pet);
        pets.add(Pet.builder().id(2L).build());

        when(petRepository.findAll()).thenReturn(pets);
        Set<Pet> foundPets = petService.findAll();

        assertNotNull(foundPets);
        assertEquals(2, foundPets.size());
    }

    @Test
    void findById() {
        when(petRepository.findById(PET_ID)).thenReturn(Optional.of(pet));

        Pet foundPet = petService.findById(PET_ID);

        assertNotNull(foundPet);
        assertEquals(pet.getId(), foundPet.getId());
        verify(petRepository, times(1)).findById(PET_ID);
    }

    @Test
    void findByIdNotPresent() {
        final String exceptionMessage = "Entity doesn't exist";
        when(petRepository.findById(anyLong())).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> petService.findById(2L));
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    void save() {
        when(petRepository.save(any())).thenReturn(pet);
        assertNotNull(petService.save(pet));
        verify(petRepository).save(any());
    }

    @Test
    void deleteById() {
        petService.deleteById(1L);
        verify(petRepository).deleteById(anyLong());
    }

    @Test
    void delete() {
        petService.delete(pet);
        verify(petRepository).delete(any());
    }
}