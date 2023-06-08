package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.InstitutionService;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    InstitutionService institutionService;
    DonationService donationService;

    public HomeController(InstitutionService institutionService, DonationService donationService) {
        this.institutionService = institutionService;
        this.donationService = donationService;
    }

    @GetMapping("")
    public String showIndexPage(Model model){
            model.addAttribute("allInstitutions", institutionService.getAllInstitutions());
            model.addAttribute("sumDonationsQuantity", donationService.sumDonationsQuantity());
            model.addAttribute("sumDonations", donationService.sumDonations());
        return "index";
    }
}
