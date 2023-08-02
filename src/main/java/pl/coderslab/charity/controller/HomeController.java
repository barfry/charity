package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.charity.dto.ChangePasswordDTO;
import pl.coderslab.charity.model.User;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.InstitutionService;
import pl.coderslab.charity.service.RoleService;
import pl.coderslab.charity.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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

    @GetMapping("/initiate-reset-password")
    public String initResetPassword(){

        return "reset-password-email";
    }

    @PostMapping("/initiate-reset-password")
    public String initiatePasswordReset(@RequestParam("email") @NotBlank @Email String email, Model model) {
        try {
            userService.initiatePasswordReset(email);
            model.addAttribute("successMessage", "Na podany adres e-mail wysłano link do zresetowania hasła");
            return "login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", "Podany adres e-mail nie odnaleziony");
            return "reset-password-email";
        }
    }

    @GetMapping("/reset-password")
    public String initNewPasswordForm(@RequestParam(name = "token") String token, Model model){
        model.addAttribute("changePasswordDTO", new ChangePasswordDTO());
        model.addAttribute("token", token);
        return "reset-password-form";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String token, @Valid ChangePasswordDTO changePasswordDTO, BindingResult result, Model model) {
        if(result.hasErrors() || !changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword())) {
            model.addAttribute("changePasswordDTO", changePasswordDTO);
            if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword())) {
                result.rejectValue("confirmPassword", "error.confirmPassword", "Hasła się różnią, proszę o poprawne powtórzenie hasła");
            }
            model.addAttribute("token", token);
            return "reset-password-form";
        }
        try {
            userService.resetPassword(token, changePasswordDTO.getNewPassword());
            model.addAttribute("successMessage", "Reset hasła zatwierdzony");
            return "login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", "Link resetu hasła wygasł");
            return "login";
        }
    }
}
