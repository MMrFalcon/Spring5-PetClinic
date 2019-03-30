package falcon.springframework.spring5petclinic.repositories;

import falcon.springframework.spring5petclinic.model.Vet;
import org.springframework.data.repository.CrudRepository;

public interface VetRepository extends CrudRepository<Vet, Long> {
}
