package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.model.User;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.InstitutionService;
import pl.coderslab.charity.service.RoleService;
import pl.coderslab.charity.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("")
public class HomeController {

    @Autowired
    InstitutionService institutionService;
    DonationService donationService;
    UserService userService;
    RoleService roleService;

    public HomeController(InstitutionService institutionService, DonationService donationService, UserService userService, RoleService roleService) {
        this.institutionService = institutionService;
        this.donationService = donationService;
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("")
    public String showIndexPage(Model model){
            model.addAttribute("allInstitutions", institutionService.getAllInstitutions());
            model.addAttribute("sumDonationsQuantity", donationService.sumDonationsQuantity());
            model.addAttribute("sumDonations", donationService.sumDonations());
        return "index";
    }

    @GetMapping("/register")
    public String initRegisterFormPage(Model model){
        model.addAttribute("user", new User());

        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(@Valid User user, BindingResult result, Model model){
            if (result.hasErrors() || !user.getPassword().equals(user.getConfirmPassword()) || userService.checkIfEmailExists(user)) {
                if (!user.getPassword().equals(user.getConfirmPassword())) {
                    result.rejectValue("confirmPassword", "error.confirmPassword", "Hasła się różnią, proszę o poprawne powtórzenie hasła");
                }
                if(userService.checkIfEmailExists(user)){
                    result.rejectValue("email", "error.email", "Podany e-mail jest już zarejestrowany");
                }
                model.addAttribute("user", user);
                return "register";
            }
        userService.registerUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }
}
