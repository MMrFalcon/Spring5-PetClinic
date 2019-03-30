package falcon.springframework.spring5petclinic.services.springdatajpa;

import falcon.springframework.spring5petclinic.model.PetType;
import falcon.springframework.spring5petclinic.repositories.PetTypeRepository;
import falcon.springframework.spring5petclinic.services.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class PetTypeSDJpaService implements PetTypeService {

    private final PetTypeRepository petTypeRepository;

    public PetTypeSDJpaService(PetTypeRepository petTypeRepository) {
        this.petTypeRepository = petTypeRepository;
    }

    @Override
    public Set<PetType> findAll() {
        Set<PetType> petTypes = new HashSet<>();
        petTypeRepository.findAll().forEach(petTypes::add);
        return petTypes;
    }

    @Override
    public PetType findById(Long aLong) {
        Optional<PetType> optionalPetType = petTypeRepository.findById(aLong);
        if (optionalPetType.isPresent()) {
            return optionalPetType.get();
        }else {
            throw new RuntimeException("Entity doesn't exist");
        }
    }

    @Override
    public PetType save(PetType object) {
        return petTypeRepository.save(object);
    }

    @Override
    public void deleteById(Long aLong) {
        petTypeRepository.deleteById(aLong);
    }

    @Override
    public void delete(PetType object) {
        petTypeRepository.delete(object);
    }
}
