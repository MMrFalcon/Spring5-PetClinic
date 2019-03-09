package falcon.springframework.spring5petclinic.model;

import java.util.HashSet;
import java.util.Set;

public class Vet extends  Person {

    private Set<Speciality> specialities = new HashSet<>(); //will save entity when it is added to Set

    public Set<Speciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(Set<Speciality> specialities) {
        this.specialities = specialities;
    }
}
