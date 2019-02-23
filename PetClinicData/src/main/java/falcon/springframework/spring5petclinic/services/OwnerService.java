package falcon.springframework.spring5petclinic.services;

import falcon.springframework.spring5petclinic.model.Owner;



public interface OwnerService extends CrudService<Owner, Long> {

    Owner findByLastName(String lastName);

}
