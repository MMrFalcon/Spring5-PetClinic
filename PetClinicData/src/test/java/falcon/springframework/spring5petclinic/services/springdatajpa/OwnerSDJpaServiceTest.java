package falcon.springframework.spring5petclinic.services.springdatajpa;

import falcon.springframework.spring5petclinic.model.Owner;
import falcon.springframework.spring5petclinic.repositories.OwnerRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    private static final String LAST_NAME = "Smith";
    private static final Long FIRST_ID = 1L;

    @Mock
    OwnerRepository ownerRepository;

    @InjectMocks
    OwnerSDJpaService ownerService;

    private Owner returnedOwner;

    @BeforeEach
    void setUp() {
        returnedOwner = Owner.builder().id(FIRST_ID).lastName(LAST_NAME).build();
    }

    @Test
    void findByLastName() {
        when(ownerRepository.findByLastName(any())).thenReturn(returnedOwner);
        Owner smith = ownerService.findByLastName(LAST_NAME);
        assertEquals(LAST_NAME, smith.getLastName());
        assertEquals(returnedOwner, smith);
    }

    @Test
    void findAll() {
        Set<Owner> owners = new HashSet<>();
        owners.add(returnedOwner);
        owners.add(Owner.builder().id(2L).build());
        when(ownerRepository.findAll()).thenReturn(owners);
        assertEquals(2L, ownerService.findAll().size());
        assertNotNull(ownerService.findAll());
    }

    @Test
    void findById() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(returnedOwner));
        assertNotNull(ownerService.findById(2L));
    }

    @Test
    void findByIdNotPresent() {
        final String exceptionMessage = "Entity doesn't exist";
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> ownerService.findById(2L));
        assertEquals(exceptionMessage, exception.getMessage());
    }


    @Test
    void save() {
       when(ownerRepository.save(any())).thenReturn(returnedOwner);
       assertNotNull(ownerService.save(returnedOwner));
       verify(ownerRepository).save(any());
    }

    @Test
    void deleteById() {
        ownerService.deleteById(1L);
        //default 1 times
        verify(ownerRepository).deleteById(anyLong());
    }

    @Test
    void delete() {
        ownerService.delete(returnedOwner);
        verify(ownerRepository).delete(any());
    }
}