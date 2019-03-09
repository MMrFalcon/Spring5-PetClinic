package falcon.springframework.spring5petclinic.model;

import java.util.HashSet;
import java.util.Set;

public class Owner extends Person {

    private String address;
    private String phone;
    private String city;
    private Set<Pet> pets = new HashSet<>(); //will save entity when it is added to Set

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Set<Pet> getPets() {
        return pets;
    }

    public void setPets(Set<Pet> pets) {
        this.pets = pets;
    }
}
