package falcon.springframework.spring5petclinic.services.springdatajpa;

import falcon.springframework.spring5petclinic.model.Visit;
import falcon.springframework.spring5petclinic.repositories.VisitRepository;
import falcon.springframework.spring5petclinic.services.VisitService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class VisitSDJpaService implements VisitService {
    private final VisitRepository visitRepository;

    public VisitSDJpaService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public Set<Visit> findAll() {
        Set<Visit> visits = new HashSet<>();
        visitRepository.findAll().forEach(visits::add);
        return visits;
    }

    @Override
    public Visit findById(Long aLong) {
        Optional<Visit> optionalVisit = visitRepository.findById(aLong);

        if (optionalVisit.isPresent()) {
            return optionalVisit.get();
        }else {
            throw new RuntimeException("Entity doesn't exist");
        }
    }

    @Override
    public Visit save(Visit object) {
        return visitRepository.save(object);
    }

    @Override
    public void deleteById(Long aLong) {
        visitRepository.deleteById(aLong);
    }

    @Override
    public void delete(Visit object) {
        visitRepository.delete(object);
    }
}
