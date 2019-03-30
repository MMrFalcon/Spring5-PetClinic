package falcon.springframework.spring5petclinic.services.springdatajpa;

import falcon.springframework.spring5petclinic.model.Speciality;
import falcon.springframework.spring5petclinic.repositories.SpecialityRepository;
import falcon.springframework.spring5petclinic.services.SpecialityService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class SpecialitySDJpaService implements SpecialityService {

    private final SpecialityRepository specialityRepository;

    public SpecialitySDJpaService(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    @Override
    public Set<Speciality> findAll() {
        Set<Speciality> specialities = new HashSet<>();
        specialityRepository.findAll().forEach(specialities::add);
        return specialities;
    }

    @Override
    public Speciality findById(Long aLong) {
        Optional<Speciality> optionalSpeciality = specialityRepository.findById(aLong);
        if (optionalSpeciality.isPresent()) {
            return optionalSpeciality.get();
        }else {
            throw new RuntimeException("Entity doesn't exist");
        }
    }

    @Override
    public Speciality save(Speciality object) {
        return specialityRepository.save(object);
    }

    @Override
    public void deleteById(Long aLong) {
        specialityRepository.deleteById(aLong);
    }

    @Override
    public void delete(Speciality object) {
        specialityRepository.delete(object);
    }
}
