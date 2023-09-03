package pl.coderslab.charity.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.charity.model.Role;
import pl.coderslab.charity.model.User;
import pl.coderslab.charity.model.VerificationToken;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VerificationTokenRepositoryTest {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testFindByToken() {
        User user = createSampleUser();

        User savedUser = userRepository.save(user);

        VerificationToken token = new VerificationToken();
        token.setToken("sample-token");
        token.setUser(user);
        VerificationToken savedToken = verificationTokenRepository.save(token);

        VerificationToken foundToken = verificationTokenRepository.findByToken(savedToken.getToken());

        assertNotNull(foundToken);
        assertEquals("sample-token", foundToken.getToken());
        assertEquals(user.getId(), foundToken.getUser().getId());
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
}
