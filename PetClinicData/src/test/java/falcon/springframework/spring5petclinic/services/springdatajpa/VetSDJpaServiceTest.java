package falcon.springframework.spring5petclinic.services.springdatajpa;

import falcon.springframework.spring5petclinic.model.Vet;
import falcon.springframework.spring5petclinic.repositories.VetRepository;
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
class VetSDJpaServiceTest {

    private static  final Long VET_ID =  1L;

    private Vet vet;

    @Mock
    VetRepository vetRepository;

    @InjectMocks
    VetSDJpaService vetService;

    @BeforeEach
    void setUp() {
        vet = Vet.builder().id(VET_ID).build();
    }

    @Test
    void findAll() {
        Set<Vet> vets = new HashSet<>();
        vets.add(vet);
        vets.add(Vet.builder().id(2L).build());

        when(vetRepository.findAll()).thenReturn(vets);
        Set<Vet> foundVets = vetService.findAll();

        assertNotNull(foundVets);
        assertEquals(2, foundVets.size());
    }

    @Test
    void findById() {
        when(vetRepository.findById(VET_ID)).thenReturn(Optional.of(vet));

        Vet foundVet = vetService.findById(VET_ID);

        assertNotNull(foundVet);
        assertEquals(vet.getId(), foundVet.getId());
        verify(vetRepository, times(1)).findById(VET_ID);
    }

    @Test
    void findByIdNotPresent() {
        final String exceptionMessage = "Entity doesn't exist";
        when(vetRepository.findById(anyLong())).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> vetService.findById(2L));
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    void save() {
        when(vetRepository.save(any())).thenReturn(vet);
        assertNotNull(vetService.save(vet));
        verify(vetRepository).save(any());
    }

    @Test
    void deleteById() {
        vetService.deleteById(1L);
        verify(vetRepository).deleteById(anyLong());
    }

    @Test
    void delete() {
        vetService.delete(vet);
        verify(vetRepository).delete(any());
    }
}