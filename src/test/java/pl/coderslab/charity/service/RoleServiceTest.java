package pl.coderslab.charity.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.coderslab.charity.model.Role;
import pl.coderslab.charity.model.User;
import pl.coderslab.charity.repository.RoleRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        roleService = new RoleService(roleRepository);
    }

    @Test
    void testAssignUserRole() {
        User user = new User();
        Role userRole = new Role(1L, "ROLE_USER");

        when(roleRepository.findById(1L)).thenReturn(Optional.of(userRole));

        roleService.assignUserRole(user);

        verify(roleRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(roleRepository);

        assert user.getRoles() != null;
        assert user.getRoles().size() == 1;
        assert user.getRoles().contains(userRole);
    }

    @Test
    void testAssignAdminRole() {
        User user = new User();
        Role adminRole = new Role(2L, "ROLE_ADMIN");

        when(roleRepository.findById(2L)).thenReturn(Optional.of(adminRole));

        roleService.assignAdminRole(user);

        verify(roleRepository, times(1)).findById(2L);
        verifyNoMoreInteractions(roleRepository);

        assert user.getRoles() != null;
        assert user.getRoles().size() == 1;
        assert user.getRoles().contains(adminRole);
    }
}
