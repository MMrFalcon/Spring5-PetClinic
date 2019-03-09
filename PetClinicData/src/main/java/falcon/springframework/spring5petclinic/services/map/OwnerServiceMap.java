package falcon.springframework.spring5petclinic.services.map;

import falcon.springframework.spring5petclinic.model.Owner;
import falcon.springframework.spring5petclinic.services.OwnerService;
import falcon.springframework.spring5petclinic.services.PetService;
import falcon.springframework.spring5petclinic.services.PetTypeService;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
public class OwnerServiceMap extends AbstractMapService<Owner, Long> implements OwnerService {

    private final PetTypeService petTypeService;
    private final PetService petService;

    public OwnerServiceMap(PetTypeService petTypeService, PetService petService) {
        this.petTypeService = petTypeService;
        this.petService = petService;
    }

    @Override
    public Set<Owner> findAll() {
        return super.findAll();
    }

    @Override
    public void delete(Owner object) {
        super.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public Owner save(Owner object) {
        if (object != null) {
            if (object.getPets() != null) {

                object.getPets().forEach(pet -> {
                    if (pet.getPetType() != null) {
                        if (pet.getPetType().getId() == null) {
                            pet.setPetType(petTypeService.save(pet.getPetType()));
                        }else{
                            throw new RuntimeException("Object already exist");
                        }
                    }else {
                        throw new RuntimeException("Pet Type is required");
                    }

                });

            }

            return super.save(object);
        }else {
            throw new NullPointerException("Null Entity");
        }

    }

    @Override
    public Owner findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Owner findByLastName(String lastName) {
        return null;
    }
}
