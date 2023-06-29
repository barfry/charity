package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.service.CategoryService;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.InstitutionService;
import pl.coderslab.charity.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    CategoryService categoryService;
    DonationService donationService;
    InstitutionService institutionService;
    UserService userService;

    public AdminController(CategoryService categoryService, DonationService donationService, InstitutionService institutionService, UserService userService) {
        this.categoryService = categoryService;
        this.donationService = donationService;
        this.institutionService = institutionService;
        this.userService = userService;
    }

    @GetMapping("")
    public String showAdminDashboard(){

        return "/admin/admin-dashboard";
    }

    @GetMapping("/institutions")
    public String showInstitutionsPage(){
        return "";
    }
}
