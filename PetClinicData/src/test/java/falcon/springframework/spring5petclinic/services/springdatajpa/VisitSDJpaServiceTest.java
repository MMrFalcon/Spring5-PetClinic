package falcon.springframework.spring5petclinic.services.springdatajpa;

import falcon.springframework.spring5petclinic.model.Visit;
import falcon.springframework.spring5petclinic.repositories.VisitRepository;
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
class VisitSDJpaServiceTest {

    private final static Long VISIT_ID = 1L;

    private Visit visit;

    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    VisitSDJpaService visitService;

    @BeforeEach
    void setUp() {
        visit = Visit.builder().id(VISIT_ID).build();
    }

    @Test
    void findAll() {
        Set<Visit> visits = new HashSet<>();
        visits.add(visit);
        visits.add(Visit.builder().id(2L).build());

        when(visitRepository.findAll()).thenReturn(visits);
        Set<Visit> foundVisits = visitService.findAll();

        assertNotNull(foundVisits);
        assertEquals(2, foundVisits.size());
    }

    @Test
    void findById() {
        when(visitRepository.findById(VISIT_ID)).thenReturn(Optional.of(visit));

        Visit foundVisit = visitService.findById(VISIT_ID);

        assertNotNull(foundVisit);
        assertEquals(visit.getId(), foundVisit.getId());
        verify(visitRepository, times(1)).findById(VISIT_ID);
    }

    @Test
    void findByIdNotPresent() {
        final String exceptionMessage = "Entity doesn't exist";
        when(visitRepository.findById(anyLong())).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> visitService.findById(2L));
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    void save() {
        when(visitRepository.save(any())).thenReturn(visit);
        assertNotNull(visitService.save(visit));
        verify(visitRepository).save(any());
    }

    @Test
    void deleteById() {
        visitService.deleteById(1L);
        verify(visitRepository).deleteById(anyLong());
    }

    @Test
    void delete() {
        visitService.delete(visit);
        verify(visitRepository).delete(any());
    }
}