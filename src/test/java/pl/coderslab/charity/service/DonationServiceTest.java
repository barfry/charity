package pl.coderslab.charity.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.coderslab.charity.model.Donation;
import pl.coderslab.charity.repository.DonationRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DonationServiceTest {
    @InjectMocks
    private DonationService donationService;

    @Mock
    private DonationRepository donationRepository;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        donationService = new DonationService(donationRepository, userService);
    }

    @Test
    public void testSumDonationsQuantity() {
        List<Donation> donations = new ArrayList<>();
        donations.add(new Donation(1L, 5, null, null, null, null, null, null, null, null, null, null, null));
        donations.add(new Donation(2L, 3, null, null, null, null, null, null, null, null, null, null, null));

        when(donationRepository.findAll()).thenReturn(donations);

        int sum = donationService.sumDonationsQuantity();

        assertEquals(8, sum);
    }

    @Test
    public void testSumDonations() {
        when(donationRepository.count()).thenReturn(5L);

        long count = donationService.sumDonations();

        assertEquals(5L, count);
    }

    @Test
    public void testSaveNewDonation() {
        Donation donation = new Donation();

        donationService.saveNewDonation(donation);

        verify(donationRepository, times(1)).save(donation);
    }

    @Test
    public void testRemoveDonationFromUserByDonationId() {
        Long donationId = 1L;
        Donation donation = new Donation();

        when(donationRepository.getOne(donationId)).thenReturn(donation);

        donationService.removeDonationFromUserByDonationId(donationId);

        verify(userService, times(1)).removeDonationFromUser(donation);
    }

    @Test
    public void testRemoveDonationById() {
        Long donationId = 1L;

        donationService.removeDonationById(donationId);

        verify(donationRepository, times(1)).deleteById(donationId);
    }

    @Test
    public void testCollectDonationById() {
        Long donationId = 1L;

        donationService.collectDonationById(donationId);

        verify(donationRepository, times(1)).markDonationAsCollected(donationId);
    }

    @Test
    public void testGetAllDonationsOrderedByCollectedAscAndPickUpDateAsc() {
        List<Donation> donations = new ArrayList<>();
        donations.add(new Donation(1L, 5, null, null, null, null, null, null, null, null, null, null, null));
        donations.add(new Donation(2L, 3, null, null, null, null, null, null, null, null, null, null, null));

        when(donationRepository.findAllOrderedByCollectedAndPickUpDate()).thenReturn(donations);

        List<Donation> result = donationService.getAllDonationsOrderedByCollectedAscAndPickUpDateAsc();

        assertEquals(donations, result);
    }
}