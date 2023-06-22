package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.charity.model.User;
import pl.coderslab.charity.service.UserService;

@Controller
public class VerificationController {

    @Autowired
    private UserService userService;

    @GetMapping("/verify")
    public String verifyAccount(@RequestParam("token") String verificationToken) {
        User user = userService.verifyAccount(verificationToken);

        if (user != null) {
            // Account verified successfully
            return "redirect:/login?verified";
        } else {
            // Invalid verification token
            return "redirect:/login?tokenInvalid";
        }
    }

    @GetMapping("/user-not-verified")
    public String showNotVerifiedPage(Model model){
        model.addAttribute("verifiedError", true);
        return "login";
    }
}
