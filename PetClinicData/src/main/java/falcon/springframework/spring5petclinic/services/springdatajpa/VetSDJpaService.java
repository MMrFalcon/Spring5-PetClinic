package falcon.springframework.spring5petclinic.services.springdatajpa;

import falcon.springframework.spring5petclinic.model.Vet;
import falcon.springframework.spring5petclinic.repositories.VetRepository;
import falcon.springframework.spring5petclinic.services.VetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class VetSDJpaService implements VetService {

    private final VetRepository vetRepository;

    public VetSDJpaService(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    @Override
    public Set<Vet> findAll() {
        Set<Vet> vets = new HashSet<>();
        vetRepository.findAll().forEach(vets::add);
        return vets;
    }

    @Override
    public Vet findById(Long aLong) {
       Optional<Vet> optionalVet = vetRepository.findById(aLong);

       if (optionalVet.isPresent()) {
           return optionalVet.get();
       }else {
           throw new RuntimeException("Entity doesn't exist");
       }
    }

    @Override
    public Vet save(Vet object) {
        return vetRepository.save(object);
    }

    @Override
    public void deleteById(Long aLong) {
        vetRepository.deleteById(aLong);
    }

    @Override
    public void delete(Vet object) {
        vetRepository.delete(object);
    }
}
