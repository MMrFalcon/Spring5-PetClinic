package falcon.springframework.spring5petclinic.repositories;

import falcon.springframework.spring5petclinic.model.PetType;
import org.springframework.data.repository.CrudRepository;

public interface PetTypeRepository extends CrudRepository<PetType, Long> {
}
