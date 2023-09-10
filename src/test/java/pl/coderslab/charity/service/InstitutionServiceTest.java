package pl.coderslab.charity.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.coderslab.charity.model.Institution;
import pl.coderslab.charity.repository.InstitutionRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class InstitutionServiceTest {

    @InjectMocks
    private InstitutionService institutionService;

    @Mock
    private InstitutionRepository institutionRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        institutionService = new InstitutionService(institutionRepository);
    }

    @Test
    public void testGetAllInstitutions(){

        Institution institution1 = new Institution(1L, "Institution1", "Institution1 description");
        Institution institution2 = new Institution(2L, "Institution2", "Institution2 description");

        List<Institution> institutions = Arrays.asList(institution1,institution2);

        when(institutionRepository.findAll()).thenReturn(institutions);

        List<Institution> result = institutionService.getAllInstitutions();

        assertEquals(institutions.size(),result.size());
        assertEquals(institution1.getName(), result.get(0).getName());
        assertEquals(institution2.getName(), result.get(1).getName());

        verify(institutionRepository, times(1)).findAll();
        verifyNoMoreInteractions(institutionRepository);
    }

    @Test
    public void testSaveOrUpdateInstitution() {
        Institution institutionToSave = new Institution(1L, "Sample institution", "Description");

        when(institutionRepository.save(institutionToSave)).thenReturn(institutionToSave);

        Institution savedInstitution = institutionService.saveOrUpdateInstitution(institutionToSave);

        verify(institutionRepository, times(1)).save(institutionToSave);

        assertEquals(institutionToSave, savedInstitution);
    }

    @Test
    public void testGetInstitutionById(){
        Institution expectedInstitution = new Institution(1L, "Sample institution", "Description");

        when(institutionRepository.findInstitutionById(expectedInstitution.getId())).thenReturn(expectedInstitution);

        Institution retrievedInstitution = institutionService.getInstitutionById(expectedInstitution.getId());

        verify(institutionRepository, times(1)).findInstitutionById(expectedInstitution.getId());

        assertEquals(expectedInstitution, retrievedInstitution);
    }

    @Test
    public void testRemoveInstitutionById() {
        Long institutionIdToRemove = 1L;

        institutionService.removeInstitutionById(institutionIdToRemove);

        verify(institutionRepository, times(1)).deleteById(institutionIdToRemove);
    }
}
