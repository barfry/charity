package pl.coderslab.charity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.model.Donation;
import pl.coderslab.charity.repository.DonationRepository;

import java.util.List;

@Service
public class DonationService {

    @Autowired
    DonationRepository donationRepository;

    public DonationService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public Integer sumDonationsQuantity(){
        List<Donation> donationList = donationRepository.findAll();
        return donationList.stream().mapToInt(Donation::getQuantity).sum();
    }

    public long sumDonations(){
        return donationRepository.count();
    }
}
