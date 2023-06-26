package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.model.Donation;
import pl.coderslab.charity.repository.CategoryRepository;
import pl.coderslab.charity.service.CategoryService;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.InstitutionService;
import pl.coderslab.charity.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("")
public class DonateController {

    @Autowired
    CategoryService categoryService;
    DonationService donationService;
    InstitutionService institutionService;
    UserService userService;

    public DonateController(CategoryService categoryService, DonationService donationService, InstitutionService institutionService, UserService userService) {
        this.categoryService = categoryService;
        this.donationService = donationService;
        this.institutionService = institutionService;
        this.userService = userService;
    }

    @GetMapping("/donate")
    public String initDonationFormPage(Model model){
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("institutions", institutionService.getAllInstitutions());
        model.addAttribute("donation", new Donation());
        return "form";
    }

    @PostMapping("/donate")
    public String persistDonation(@Valid Donation donation, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("institutions", institutionService.getAllInstitutions());
            model.addAttribute("donation", donation);
            return "form";
        }

        donationService.saveNewDonation(donation);

        return "redirect:";
    }

}
