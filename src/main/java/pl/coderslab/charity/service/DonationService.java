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

        donationRepository.save(donation);

        if(userService.isLoggedIn()) {
            userService.addNewDonationToUser(donation);
        }
    }
}
