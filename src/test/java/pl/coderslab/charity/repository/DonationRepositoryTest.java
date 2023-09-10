package pl.coderslab.charity.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.coderslab.charity.model.Category;
import pl.coderslab.charity.model.Donation;
import pl.coderslab.charity.model.Institution;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class DonationRepositoryTest {

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testSaveDonation() {
        Donation donation = createSampleDonation();

        Donation savedDonation = donationRepository.save(donation);
        assertNotNull(savedDonation.getId());

        Donation retrievedDonation = donationRepository.findById(savedDonation.getId()).orElse(null);
        assertNotNull(retrievedDonation);
        assertEquals(donation.getQuantity(), retrievedDonation.getQuantity());
        assertEquals(donation.getCategoryList().size(), retrievedDonation.getCategoryList().size());
        assertEquals(donation.getInstitution().getId(), retrievedDonation.getInstitution().getId());
        assertEquals(donation.getStreet(), retrievedDonation.getStreet());
        assertEquals(donation.getCity(), retrievedDonation.getCity());
        assertEquals(donation.getZipCode(), retrievedDonation.getZipCode());
        assertEquals(donation.getPickUpDate(), retrievedDonation.getPickUpDate());
        assertEquals(donation.getPickUpTime(), retrievedDonation.getPickUpTime());
        assertEquals(donation.getPhoneNumber(), retrievedDonation.getPhoneNumber());
        assertEquals(donation.getPickUpComment(), retrievedDonation.getPickUpComment());
        assertEquals(donation.getCollected(), retrievedDonation.getCollected());
        assertEquals(donation.getCollectionDateTime(), retrievedDonation.getCollectionDateTime());
    }

    @Test
    public void testFindDonationById() {
        Donation donation = createSampleDonation();

        Donation savedDonation = donationRepository.save(donation);

        Long donationId = savedDonation.getId();
        Donation foundDonation = donationRepository.findById(donationId).orElse(null);
        assertNotNull(foundDonation);
        assertEquals(donation.getQuantity(), foundDonation.getQuantity());
        assertEquals(donation.getCategoryList().size(), foundDonation.getCategoryList().size());
        assertEquals(donation.getInstitution().getId(), foundDonation.getInstitution().getId());
        assertEquals(donation.getStreet(), foundDonation.getStreet());
        assertEquals(donation.getCity(), foundDonation.getCity());
        assertEquals(donation.getZipCode(), foundDonation.getZipCode());
        assertEquals(donation.getPickUpDate(), foundDonation.getPickUpDate());
        assertEquals(donation.getPickUpTime(), foundDonation.getPickUpTime());
        assertEquals(donation.getPhoneNumber(), foundDonation.getPhoneNumber());
        assertEquals(donation.getPickUpComment(), foundDonation.getPickUpComment());
        assertEquals(donation.getCollected(), foundDonation.getCollected());
        assertEquals(donation.getCollectionDateTime(), foundDonation.getCollectionDateTime());
    }

    @Test
    public void testUpdateDonation() {
        Donation donation = createSampleDonation();

        Donation savedDonation = donationRepository.save(donation);
        assertNotNull(savedDonation.getId());

        savedDonation.setQuantity(5);
        savedDonation.setStreet("Updated street");
        savedDonation.setCollected(true);

        Donation updatedDonation = donationRepository.save(savedDonation);
        assertEquals(Integer.valueOf(5), updatedDonation.getQuantity());
        assertEquals("Updated street", updatedDonation.getStreet());
        assertEquals(true, updatedDonation.getCollected());
    }

    @Test
    public void testDeleteDonation() {
        Donation donation = createSampleDonation();

        Donation savedDonation = donationRepository.save(donation);
        assertNotNull(savedDonation.getId());

        Long donationId = savedDonation.getId();
        donationRepository.deleteById(donationId);

        Donation foundDonation = donationRepository.findById(donationId).orElse(null);

        assertNull(foundDonation);
    }

    @Test
    @Transactional
    public void testMarkDonationAsCollected() {
        Donation donation = createSampleDonation();

        Donation savedDonation = donationRepository.save(donation);

        entityManager.flush();

        donationRepository.markDonationAsCollected(savedDonation.getId());

        entityManager.clear();

        Donation foundDonation = donationRepository.getOne(savedDonation.getId());

        assertTrue(foundDonation.getCollected());
        assertNotNull(foundDonation.getCollectionDateTime());
    }

    @Test
    public void testFindAllOrderedByCollectedAndPickUpDate() throws InterruptedException {
        Donation donation1 = createSampleDonation();
        donation1.setCollected(false);
        donationRepository.save(donation1);

        Donation donation2 = createSampleDonation();

        donationRepository.save(donation2);

        entityManager.flush();
        donationRepository.markDonationAsCollected(donation2.getId());
        Thread.sleep(100);
        donationRepository.markDonationAsCollected(donation1.getId());
        entityManager.clear();

        Donation donation3 = createSampleDonation();
        donation3.setCollected(false);

        donationRepository.save(donation3);


        List<Donation> donations = donationRepository.findAllOrderedByCollectedAndPickUpDate();

        assertEquals(3, donations.size());
        assertFalse(donations.get(0).getCollected());
        assertTrue(donations.get(1).getCollected());
        assertTrue(donations.get(2).getCollected());
        assertFalse(donations.get(1).getPickUpDate().isAfter(donations.get(2).getPickUpDate()));
    }


    private Donation createSampleDonation() {
        Donation donation = new Donation();
        donation.setQuantity(3);

        List<Category> categoryList = new ArrayList<>();
        Category category1 = new Category();
        category1.setName("Sample category1 name");
        categoryList.add(category1);
        categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setName("Sample category2 name");
        categoryList.add(category2);
        categoryRepository.save(category2);

        donation.setCategoryList(categoryList);

        Institution institution = new Institution();
        institution.setName("Sample institution name");
        institution.setDescription("Sample institution description");

        institution = institutionRepository.save(institution);

        donation.setInstitution(institution);

        donation.setStreet("Sample street");
        donation.setCity("Sample city");
        donation.setZipCode("12-345");
        donation.setPickUpDate(LocalDate.now());
        donation.setPickUpTime(LocalTime.now());
        donation.setPhoneNumber("123456789");
        donation.setPickUpComment("Sample comment");
        donation.setCollected(false);

        return donation;
    }
}
