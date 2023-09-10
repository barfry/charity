package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.dto.ChangePasswordDTO;
import pl.coderslab.charity.dto.UserDTO;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    DonationService donationService;

    public UserController(UserService userService, DonationService donationService) {
        this.userService = userService;
        this.donationService = donationService;
    }

    @GetMapping("")
    public String showUserProfilePage(Model model){
        model.addAttribute("userDto", userService.getCurrentUserDto());
        return "user-profile";
    }

    @GetMapping("/edit-profile")
    public String initEditProfileForm(Model model){
        model.addAttribute("userDto", userService.getCurrentUserDto());
        return "edit-profile";
    }

    @PostMapping("/edit-profile")
    public String editProfile(@Valid @ModelAttribute("userDto") UserDTO userDTO, BindingResult result, Model model){
        if(result.hasErrors() || userService.checkIfEmailExistsOnEditUser(userDTO)){
            if(userService.checkIfEmailExistsOnEditUser(userDTO)){
                result.rejectValue("email", "error.email", "Podany e-mail jest już zarejestrowany");
            }
            model.addAttribute("userDto", userDTO);
            return "edit-profile";
        }

        userService.editCurrentUser(userDTO);

        return "redirect:/user";
    }

    @GetMapping("/change-password")
    public String initChangePasswordFormPage(Model model) {
        model.addAttribute("changePasswordDTO", new ChangePasswordDTO());
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@Valid ChangePasswordDTO changePasswordDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("changePasswordDTO", changePasswordDTO);
            return "change-password";
        }

        if (!userService.verifyPassword(changePasswordDTO.getOldPassword())) {
            result.rejectValue("oldPassword", "error.oldPassword", "Niepoprawnie wprowadzone stare hasło");
            model.addAttribute("changePasswordDTO", changePasswordDTO);
            return "change-password";
        }

        if (changePasswordDTO.getOldPassword().equals(changePasswordDTO.getNewPassword())) {
            result.rejectValue("newPassword", "error.newPassword", "Stare hasło i nowe hasło muszą się różnić");
            model.addAttribute("changePasswordDTO", changePasswordDTO);
            return "change-password";
        }

        if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.confirmPassword", "Hasła się różnią, proszę o poprawne powtórzenie hasła");
            model.addAttribute("changePasswordDTO", changePasswordDTO);
            return "change-password";
        }

        userService.changePassword(changePasswordDTO.getNewPassword());

        return "redirect:/user";
    }

    @GetMapping("/donations")
    public String showUserDonationsPage(Model model){
        model.addAttribute("donations", userService.getCurrentUserDonations());

        return "user-donations";
    }

    @PostMapping("/donations/remove-donation")
    public String removeDonation(@RequestParam(name = "id") Long id){
        donationService.removeDonationFromUserByDonationId(id);
        return "redirect:/user/donations";
    }

    @PostMapping("/donations/collect-donation")
    public String collectDonation(@RequestParam(name = "id") Long id){
        donationService.collectDonationById(id);
        return "redirect:/user/donations";
    }
}
