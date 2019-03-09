package falcon.springframework.spring5petclinic.bootstrap;

import falcon.springframework.spring5petclinic.model.Owner;
import falcon.springframework.spring5petclinic.model.PetType;
import falcon.springframework.spring5petclinic.model.Vet;
import falcon.springframework.spring5petclinic.services.OwnerService;
import falcon.springframework.spring5petclinic.services.PetTypeService;
import falcon.springframework.spring5petclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;

    public DataInitializer(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
    }

    @Override
    public void run(String... args) throws Exception {

        /**
         * Creation of simple POJO when application starts
         */

        PetType dog = new PetType();
        dog.setName("Dog");
        petTypeService.save(dog);

        PetType cat = new PetType();
        dog.setName("Cat");
        petTypeService.save(cat);

        System.out.println("Loaded Pet Types");

        Owner falcon = new Owner();
        falcon.setFirstName("Jacob");
        falcon.setLastName("Falcon");

        ownerService.save(falcon);

        Owner geralt = new Owner();
        geralt.setFirstName("Geralt");
        geralt.setLastName("of Rivia");

        ownerService.save(geralt);

        System.out.println("Loaded owners");

        Vet sam = new Vet();
        sam.setFirstName("Sam");
        sam.setLastName("Sparrow");

        vetService.save(sam);

        Vet alberta = new Vet();
        alberta.setFirstName("Alberta");
        alberta.setLastName("Grimm");

        vetService.save(alberta);

        System.out.println("Loaded vets");


    }
}
