package pl.coderslab.charity.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.charity.model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testSaveUser() {
        User user = createSampleUser();

        User savedUser = userRepository.save(user);
        assertNotNull(savedUser.getId());

        User retrievedUser = userRepository.findById(savedUser.getId()).orElse(null);
        assertNotNull(retrievedUser);
        assertEquals(user.getFirstName(), retrievedUser.getFirstName());
        assertEquals(user.getLastName(), retrievedUser.getLastName());
        assertEquals(user.getEmail(), retrievedUser.getEmail());
    }

    @Test
    public void testFindUserById() {
        User user = createSampleUser();

        User savedUser = userRepository.save(user);

        Long userId = savedUser.getId();
        User foundUser = userRepository.findById(userId).orElse(null);
        assertNotNull(foundUser);
        assertEquals(user.getFirstName(), foundUser.getFirstName());
        assertEquals(user.getLastName(), foundUser.getLastName());
        assertEquals(user.getEmail(), foundUser.getEmail());
    }

    @Test
    public void testUpdateUser() {
        User user = createSampleUser();

        User savedUser = userRepository.save(user);
        assertNotNull(savedUser.getId());

        savedUser.setFirstName("Updated first name");
        savedUser.setLastName("Updated last name");

        User updatedUser = userRepository.save(savedUser);
        assertEquals("Updated first name", updatedUser.getFirstName());
        assertEquals("Updated last name", updatedUser.getLastName());
    }

    @Test
    public void testDeleteUser() {
        User user = createSampleUser();

        User savedUser = userRepository.save(user);
        assertNotNull(savedUser.getId());

        Long userId = savedUser.getId();
        userRepository.deleteById(userId);

        User foundUser = userRepository.findById(userId).orElse(null);

        assertNull(foundUser);
    }

    @Test
    public void testFindVerificationTokenByUserId() {
        User user = createSampleUser();
        User savedUser = userRepository.save(user);

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(savedUser);
        verificationToken.setToken("sampleToken");
        VerificationToken savedToken = verificationTokenRepository.save(verificationToken);

        Long userId = savedUser.getId();
        VerificationToken foundToken = userRepository.findVerificationTokenByUserId(userId);
        assertNotNull(foundToken);
        assertEquals("sampleToken", foundToken.getToken());
    }

    @Test
    public void testFindByDonationId() {
        User user = createSampleUser();
        Donation donation = createSampleDonation();

        user.setDonations(new ArrayList<>());
        user.getDonations().add(donation);

        User savedUser = userRepository.save(user);

        Long donationId = donation.getId();
        User foundUser = userRepository.findUserByDonationId(donationId);
        assertNotNull(foundUser);
        assertEquals(savedUser.getId(), foundUser.getId());
    }

    @Test
    public void testFindByEmail(){
        User user = createSampleUser();

        User savedUser = userRepository.save(user);

        User foundUser = userRepository.findByEmail(savedUser.getEmail());

        assertNotNull(foundUser);
        assertEquals(savedUser.getEmail(), foundUser.getEmail());
    }

    @Test
    public void testExistsByEmail(){
        User user = createSampleUser();

        User savedUser = userRepository.save(user);

        Boolean exists = userRepository.existsByEmail(savedUser.getEmail());

        assertTrue(exists);
    }

    @Test
    public void testFindByRoles_Name(){
        User user = createSampleUser();

        User savedUser = userRepository.save(user);

        List<User> foundUsers = userRepository.findByRoles_Name("ROLE_USER");

        assertNotNull(foundUsers);
        assertTrue(foundUsers.size() > 0);
    }

    @Test
    public void testFindByVerificationToken(){
        User user = createSampleUser();

        User savedUser = userRepository.save(user);

        User foundUser = userRepository.findByVerificationToken(savedUser.getVerificationToken());

        assertNotNull(foundUser);
        assertEquals(savedUser,foundUser);
    }

    private User createSampleUser() {
        User user = new User();
        user.setFirstName("Sample first name");
        user.setLastName("Sample last name");
        user.setEmail("sample@example.com");
        user.setPassword("S4mpl3Passw0rd");

        Role role = new Role();
        role.setName("ROLE_USER");
        roleRepository.save(role);

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        user.setRoles(roles);
        user.setEnabled(true);

        return user;
    }

    private Donation createSampleDonation() {
        Donation donation = new Donation();
        donation.setQuantity(3);

        List<Category> categoryList = new ArrayList<>();
        Category category1 = new Category();
        category1.setName("Sample category1 name");
        categoryRepository.save(category1);
        categoryList.add(category1);

        Category category2 = new Category();
        category2.setName("Sample category2 name");
        categoryRepository.save(category2);
        categoryList.add(category2);

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
