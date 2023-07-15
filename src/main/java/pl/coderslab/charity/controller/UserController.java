package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.dto.UserDTO;
import pl.coderslab.charity.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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


}
