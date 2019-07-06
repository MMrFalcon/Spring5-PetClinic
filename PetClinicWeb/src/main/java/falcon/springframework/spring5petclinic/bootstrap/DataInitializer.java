package falcon.springframework.spring5petclinic.bootstrap;

import falcon.springframework.spring5petclinic.model.*;
import falcon.springframework.spring5petclinic.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
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
        horse.setName("Horse");
        PetType savedHorse = petTypeService.save(horse);

        log.debug("Loaded Pet Types");

        Speciality radiology = new Speciality();
        radiology.setDescription("Radiology Vet Spec");
        Speciality savedRadiology = specialityService.save(radiology);

        Speciality dentist = new Speciality();
        dentist.setDescription("Dentist specialization");
        Speciality savedDentist = specialityService.save(dentist);

        Speciality surgery = new Speciality();
        surgery.setDescription("Surgery specialization");
        Speciality savedSurgery = specialityService.save(surgery);

        log.debug("Loaded Vet Specialization");

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
        geralt.setAddress("Port Alley Tavern");
        geralt.setCity("Wizima");
        geralt.setPhone("555-555");

        Pet geraltsPet = new Pet();
        geraltsPet.setPetType(savedHorse);
        geraltsPet.setBirthDate(LocalDate.of(2008,11,13));
        geraltsPet.setName("Plotka");
        geraltsPet.setOwner(geralt);
        geralt.getPets().add(geraltsPet);


        ownerService.save(geralt);

        Visit horseVisit = new Visit();
        horseVisit.setPet(geraltsPet);
        horseVisit.setDate(LocalDate.now());
        horseVisit.setDescription("That was visit");

        visitService.save(horseVisit);

        Owner falcon2 = new Owner();
        falcon2.setFirstName("Andrew");
        falcon2.setLastName("Falcon");
        falcon2.setAddress("Some Street 22");
        falcon2.setCity("Some City");
        falcon2.setPhone("+48 5532 1233 42");

        Pet falconPet = new Pet();
        falconPet.setPetType(savedDog);
        falconPet.setBirthDate(LocalDate.of(2017,3,22));
        falconPet.setOwner(falcon2);
        falconPet.setName("Rex");
        falcon2.getPets().add(falconPet);

        ownerService.save(falcon2);

        Visit secondDogVisit = new Visit();
        secondDogVisit.setPet(falconPet);
        secondDogVisit.setDate(LocalDate.now());
        secondDogVisit.setDescription("That was visit");

        visitService.save(secondDogVisit);


        log.debug("Loaded owners and their pets with visits");

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

        log.debug("Loaded vets");
    }
}
