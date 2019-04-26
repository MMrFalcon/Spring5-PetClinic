package falcon.springframework.spring5petclinic.services.map;

import falcon.springframework.spring5petclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerServiceMapTest {

    private OwnerServiceMap ownerServiceMap;
    private final Long id  = 1L;
    private final String lastName = "Sparroww";
    @BeforeEach
    void setUp() {
        ownerServiceMap = new OwnerServiceMap(new PetTypeMap(), new PetServiceMap());

        ownerServiceMap.save(Owner.builder().id(id).lastName(lastName).build());
    }

    @Test
    void findAll() {
        Set<Owner> owners = ownerServiceMap.findAll();
        assertEquals(1L, owners.size());
    }

    @Test
    void delete() {
        ownerServiceMap.delete(ownerServiceMap.findById(id));
        assertEquals(0,ownerServiceMap.findAll().size());
    }

    @Test
    void deleteById() {
        ownerServiceMap.deleteById(id);
        assertEquals(0,ownerServiceMap.findAll().size());
    }

    @Test
    void saveWithExistingId() {
        final Long secondId = 2L;
        Owner owner = Owner.builder().id(secondId).build();
        Owner savedOwner = ownerServiceMap.save(owner);
        assertEquals(secondId, savedOwner.getId());
    }

    @Test
    void saveWithoutId() {
        Owner savedOwner = ownerServiceMap.save(Owner.builder().build());
        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    void saveWithoutObject() {
        final String exceptionMessage = "Entity is empty";
        Exception exception = assertThrows(NullPointerException.class, () -> ownerServiceMap.save(null));
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    void findById() {
        Owner owner = ownerServiceMap.findById(id);
        assertEquals(id, owner.getId());
    }

    @Test
    void findByLastName() {
        Owner sparroww = ownerServiceMap.findByLastName("Sparroww");
        assertNotNull(sparroww);
        assertEquals(id, sparroww.getId());
    }

    @Test
    void findByLastNameNotFound() {
        Owner owner = ownerServiceMap.findByLastName("Sam");
        assertNull(owner);
    }
}