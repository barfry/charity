package pl.coderslab.charity.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.charity.model.Institution;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class InstitutionRepositoryTest {

    @Autowired
    private InstitutionRepository institutionRepository;

    @Test
    public void testSaveInstitution() {
        Institution institution = createSampleInstitution();

        Institution savedInstitution = institutionRepository.save(institution);
        assertNotNull(savedInstitution.getId());

        Institution retrievedInstitution = institutionRepository.findById(savedInstitution.getId()).orElse(null);
        assertNotNull(retrievedInstitution);
        assertEquals(institution.getName(), retrievedInstitution.getName());
        assertEquals(institution.getDescription(), retrievedInstitution.getDescription());
    }

    @Test
    public void testFindInstitutionById() {
        Institution institution = createSampleInstitution();

        Institution savedInstitution = institutionRepository.save(institution);

        Long institutionId = savedInstitution.getId();
        Institution foundInstitution = institutionRepository.findById(institutionId).orElse(null);
        assertNotNull(foundInstitution);
        assertEquals(institution.getName(), foundInstitution.getName());
        assertEquals(institution.getDescription(), foundInstitution.getDescription());
    }

    @Test
    public void testUpdateInstitution() {
        Institution institution = createSampleInstitution();

        Institution savedInstitution = institutionRepository.save(institution);
        assertNotNull(savedInstitution.getId());


        savedInstitution.setName("Updated institution name");
        savedInstitution.setDescription("Updated institution description");

        Institution updatedInstitution = institutionRepository.save(savedInstitution);
        assertEquals("Updated institution name", updatedInstitution.getName());
        assertEquals("Updated institution description", updatedInstitution.getDescription());
    }

    @Test
    public void testDeleteInstitution() {
        Institution institution = createSampleInstitution();

        Institution savedInstitution = institutionRepository.save(institution);
        assertNotNull(savedInstitution.getId());

        Long institutionId = savedInstitution.getId();
        institutionRepository.deleteById(institutionId);

        Institution foundInstitution = institutionRepository.findById(institutionId).orElse(null);

        assertNull(foundInstitution);
    }


    private Institution createSampleInstitution() {
        Institution institution = new Institution();
        institution.setName("Sample institution");
        institution.setDescription("Sample institution description");
        return institution;
    }
}
