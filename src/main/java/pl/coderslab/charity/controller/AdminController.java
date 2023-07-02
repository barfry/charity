package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.charity.model.Institution;
import pl.coderslab.charity.service.CategoryService;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.InstitutionService;
import pl.coderslab.charity.service.UserService;

import javax.naming.Binding;
import javax.validation.Valid;

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

        return "admin/admin-dashboard";
    }

    @GetMapping("/institutions")
    public String showInstitutionsPage(Model model){
        model.addAttribute("institutions", institutionService.getAllInstitutions());
        return "admin/all-institutions";
    }

    @GetMapping("/institutions/add-new-institution")
    public String initAddNewInstitutionForm(Model model){
        model.addAttribute("institution", new Institution());
        return "admin/add-new-institution";
    }

    @PostMapping("/institutions/add-new-institution")
    public String addNewInstitution(@Valid Institution institution, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("institution", institution);
            return "admin/add-new-institution";
        }

        institutionService.saveOrUpdateInstitution(institution);

        return "redirect:/admin/institutions";
    }

    @GetMapping("/institutions/edit-institution")
    public String initEditInstitutionForm(@RequestParam(name = "id") Long id, Model model){
        model.addAttribute("institution", institutionService.getInstitutionById(id));

        return "admin/add-new-institution";
    }

    @PostMapping("/institutions/edit-institution")
    public String editInstitution(@Valid Institution institution, BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("institution", institution);

            return "admin/add-new-institution";
        }

        institutionService.saveOrUpdateInstitution(institution);

        return "redirect:/admin/institutions";
    }

    @PostMapping("/institutions/remove-institution")
    public String initRemoveInstitution(@RequestParam(name = "id") Long id){
        institutionService.removeInstitutionById(id);

        return "redirect:/admin/institutions";
    }
}
