package falcon.springframework.spring5petclinic.bootstrap;

import falcon.springframework.spring5petclinic.model.*;
import falcon.springframework.spring5petclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;
    private final VisitService visitService;
    private final PetService petService;

    public DataInitializer(OwnerService ownerService, VetService vetService, PetTypeService petTypeService,
                           SpecialityService specialityService, VisitService visitService, PetService petService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
        this.visitService = visitService;
        this.petService = petService;
    }

    @Override
    public void run(String... args) throws Exception {

        int count = ownerService.findAll().size();

        if(count == 0) {
            loadData();
        }
    }

    private void loadData() {

        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDog = petTypeService.save(dog);

        PetType horse = new PetType();
        dog.setName("Horse");
        PetType savedHorse = petTypeService.save(horse);

        System.out.println("Loaded Pet Types");

        Speciality radiology = new Speciality();
        radiology.setDescription("Radiology Vet Spec");
        Speciality savedRadiology = specialityService.save(radiology);

        Speciality dentist = new Speciality();
        dentist.setDescription("Dentist specialization");
        Speciality savedDentist = specialityService.save(dentist);

        Speciality surgery = new Speciality();
        surgery.setDescription("Surgery specialization");
        Speciality savedSurgery = specialityService.save(surgery);

        System.out.println("Loaded Vet Specialization");

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

        Visit dogVisit = new Visit();
        dogVisit.setPet(jacobsPet);
        dogVisit.setDate(LocalDate.now());
        dogVisit.setDescription("That was visit");

        visitService.save(dogVisit);

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

        System.out.println("Loaded owners and their pets with visits");

        Vet sam = new Vet();
        sam.setFirstName("Sam");
        sam.setLastName("Sparrow");
        sam.getSpecialities().add(savedDentist);
        sam.getSpecialities().add(savedSurgery);
        vetService.save(sam);

        Vet alberta = new Vet();
        alberta.setFirstName("Alberta");
        alberta.setLastName("Grimm");
        alberta.getSpecialities().add(savedRadiology);
        vetService.save(alberta);

        System.out.println("Loaded vets");
    }
}
