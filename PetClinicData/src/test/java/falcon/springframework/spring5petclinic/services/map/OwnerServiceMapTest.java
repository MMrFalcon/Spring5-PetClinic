package falcon.springframework.spring5petclinic.services.map;

import falcon.springframework.spring5petclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerServiceMapTest {

    private OwnerServiceMap ownerServiceMap;
    private static final Long OWNER_ID = 1L;
    private static final String OWNER_LAST_NAME = "Sparroww";

    private Owner savedOwner;

    @BeforeEach
    void setUp() {
        ownerServiceMap = new OwnerServiceMap(new PetTypeServiceMap(), new PetServiceMap());

        savedOwner = ownerServiceMap.save(Owner.builder().id(OWNER_ID).lastName(OWNER_LAST_NAME).build());
    }

    @Test
    void findAll() {
        Set<Owner> owners = ownerServiceMap.findAll();
        assertEquals(1L, owners.size());
    }

    @Test
    void delete() {
        ownerServiceMap.delete(ownerServiceMap.findById(OWNER_ID));
        assertEquals(0,ownerServiceMap.findAll().size());
    }

    @Test
    void deleteById() {
        ownerServiceMap.deleteById(OWNER_ID);
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
        Owner owner = ownerServiceMap.findById(OWNER_ID);
        assertEquals(OWNER_ID, owner.getId());
    }

    @Test
    void findByLastName() {
        Owner sparroww = ownerServiceMap.findByLastName(OWNER_LAST_NAME);
        assertNotNull(sparroww);
        assertEquals(OWNER_ID, sparroww.getId());
    }

    @Test
    void findByLastNameNotFound() {
        Owner owner = ownerServiceMap.findByLastName("Sam");
        assertNull(owner);
    }

    @Test
    void  findAllByLastNameLike() {
        List<Owner> foundOwners = ownerServiceMap.findAllByLastNameLike(OWNER_LAST_NAME);
        assertEquals(foundOwners.size(), 1);
        assertEquals(foundOwners.get(0), savedOwner);
    }
}