package falcon.springframework.spring5petclinic.repositories;

import falcon.springframework.spring5petclinic.model.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet, Long> {
}
