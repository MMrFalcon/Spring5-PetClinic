package falcon.springframework.spring5petclinic.bootstrap;

import falcon.springframework.spring5petclinic.model.Owner;
import falcon.springframework.spring5petclinic.model.Pet;
import falcon.springframework.spring5petclinic.model.PetType;
import falcon.springframework.spring5petclinic.model.Vet;
import falcon.springframework.spring5petclinic.services.OwnerService;
import falcon.springframework.spring5petclinic.services.PetTypeService;
import falcon.springframework.spring5petclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

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
        PetType savedDog = petTypeService.save(dog);

        PetType horse = new PetType();
        dog.setName("Horse");
        PetType savedHorse = petTypeService.save(horse);

        System.out.println("Loaded Pet Types");

        Owner falcon = new Owner();
        falcon.setFirstName("Jacob");
        falcon.setLastName("Falcon");
        falcon.setAddress("Some Street 22");
        falcon.setCity("Some City");
        falcon.setPhone("+48 5532 1233 42");

        Pet jacobsPet = new Pet();
        jacobsPet.setPetType(savedDog);
        jacobsPet.setBirthDate(LocalDate.of(2017,3,22));
        jacobsPet.setOwner(falcon);
        jacobsPet.setName("Pimpek");
        falcon.getPets().add(jacobsPet);

        ownerService.save(falcon);

        Owner geralt = new Owner();
        geralt.setFirstName("Geralt");
        geralt.setLastName("of Rivia");
        falcon.setAddress("Port Alley Tavern");
        falcon.setCity("Wizima");
        falcon.setPhone("555-555");

        Pet geraltsPet = new Pet();
        geraltsPet.setPetType(savedHorse);
        geraltsPet.setBirthDate(LocalDate.of(2008,11,13));
        geraltsPet.setName("Plotka");
        geraltsPet.setOwner(geralt);
        geralt.getPets().add(geraltsPet);


        ownerService.save(geralt);

        System.out.println("Loaded owners and their pets");

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
