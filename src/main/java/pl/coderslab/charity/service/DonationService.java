package pl.coderslab.charity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.model.Donation;
import pl.coderslab.charity.model.User;
import pl.coderslab.charity.repository.DonationRepository;

import java.util.List;

@Service
public class DonationService {

    @Autowired
    DonationRepository donationRepository;
    UserService userService;

    public DonationService(DonationRepository donationRepository, UserService userService) {
        this.donationRepository = donationRepository;
        this.userService = userService;
    }

    public Integer sumDonationsQuantity(){
        List<Donation> donationList = donationRepository.findAll();
        return donationList.stream().mapToInt(Donation::getQuantity).sum();
    }

    public long sumDonations(){
        return donationRepository.count();
    }

    public void saveNewDonation(Donation donation){
        donation.setCollected(false);
        donationRepository.save(donation);

        if(userService.isLoggedIn()) {
            userService.addNewDonationToUser(donation);
        }
    }

    public List<Donation> getAllDonationsForCurrentUser(){
        return userService.getCurrentUser().getDonations();
    }

    public List<Donation> getAllDonations(){
        return donationRepository.findAll();
    }

    public void removeDonationFromUserByDonationId(Long id){
        userService.removeDonationFromUser(donationRepository.getOne(id));
    }

    public void removeDonationById(Long id){
        donationRepository.deleteById(id);
    }

    public void collectDonationById(Long id){
        donationRepository.markDonationAsCollected(id);
    }

    public List<Donation> getAllDonationsOrderedByCollectedAscAndPickUpDateAsc(){
        return donationRepository.findAllOrderedByCollectedAndPickUpDate();
    }
}
