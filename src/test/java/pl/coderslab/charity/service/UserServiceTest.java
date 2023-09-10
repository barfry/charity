package pl.coderslab.charity.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.coderslab.charity.exception.UserNotVerifiedException;
import pl.coderslab.charity.model.Donation;
import pl.coderslab.charity.model.User;
import pl.coderslab.charity.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUserByUsername() {

        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        user.setVerified(true);
        user.setEnabled(true);

        when(userRepository.findByEmail(email)).thenReturn(user);

        UserDetails userDetails = userService.loadUserByUsername(email);

        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertTrue(userDetails.isEnabled());
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        String email = "nonexistent@example.com";

        when(userRepository.findByEmail(email)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(email));
    }

    @Test
    public void testLoadUserByUsername_UserNotVerified() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        user.setVerified(false);

        when(userRepository.findByEmail(email)).thenReturn(user);

        assertThrows(UserNotVerifiedException.class, () -> userService.loadUserByUsername(email));
    }

    @Test
    public void testGetCurrentUser() {
        User user = new User();
        user.setEmail("test@example.com");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User currentUser = userService.getCurrentUser();

        assertNotNull(currentUser);
        assertEquals("test@example.com", currentUser.getUsername());
    }

    @Test
    public void testRemoveDonationFromUser() {
        User user = new User();
        user.setId(1L);

        Donation donation = new Donation();
        donation.setId(1L);

        List<Donation> donations = new ArrayList<>();
        donations.add(donation);
        user.setDonations(donations);

        when(userRepository.findUserByDonationId(donation.getId())).thenReturn(user);

        userService.removeDonationFromUser(donation);

        verify(userRepository).findUserByDonationId(donation.getId());
        verify(userRepository).save(user);
    }

}
