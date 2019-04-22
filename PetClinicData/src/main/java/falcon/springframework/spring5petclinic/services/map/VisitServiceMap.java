package falcon.springframework.spring5petclinic.services.map;

import falcon.springframework.spring5petclinic.model.Visit;
import falcon.springframework.spring5petclinic.services.VisitService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({"default", "map"})
public class VisitServiceMap extends AbstractMapService<Visit, Long> implements VisitService {
    @Override
    public Set<Visit> findAll() {
        return super.findAll();
    }

    @Override
    public void delete(Visit object) {
        super.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public Visit save(Visit visit) {

        if(visit.getPet() == null || visit.getPet().getOwner() == null || visit.getPet().getId() == null
                || visit.getPet().getOwner().getId() == null){
            throw new RuntimeException("Invalid Visit:\n" +
                    "Pet Entity: " + visit.getPet() + "\n" +
                    "Owner Entity: " + visit.getPet().getOwner() + "\n" +
                    "PetId: " + visit.getPet().getId() + "\n" +
                    "OwnerId: " +  visit.getPet().getOwner().getId());

        }

        return super.save(visit);
    }

    @Override
    public Visit findById(Long id) {
        return super.findById(id);
    }
}
