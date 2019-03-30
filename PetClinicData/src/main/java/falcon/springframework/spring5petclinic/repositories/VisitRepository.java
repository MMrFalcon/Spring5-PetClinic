package falcon.springframework.spring5petclinic.repositories;

import falcon.springframework.spring5petclinic.model.Visit;
import org.springframework.data.repository.CrudRepository;

public interface VisitRepository extends CrudRepository<Visit, Long> {
}
