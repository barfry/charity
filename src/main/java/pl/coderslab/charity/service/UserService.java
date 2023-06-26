package pl.coderslab.charity.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.exception.UserNotVerifiedException;
import pl.coderslab.charity.model.Donation;
import pl.coderslab.charity.model.User;
import pl.coderslab.charity.model.VerificationToken;
import pl.coderslab.charity.repository.UserRepository;
import pl.coderslab.charity.repository.VerificationTokenRepository;

import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Resource
    RoleService roleService;
    @Resource
    VerificationTokenRepository verificationTokenRepository;

    public UserService(UserRepository userRepository, RoleService roleService, VerificationTokenRepository verificationTokenRepository) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        pl.coderslab.charity.model.User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        boolean isVerified = user.isVerified();

        if (!isVerified) {
            throw new UserNotVerifiedException("User not verified");
        }

        return user;
    }

    public User getCurrentUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public UserService() {
    }

    public pl.coderslab.charity.model.User saveNewUser(pl.coderslab.charity.model.User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        roleService.assignUserRole(user);
        return userRepository.save(user);
    }

    public Boolean checkIfEmailExists(pl.coderslab.charity.model.User user){
        return userRepository.existsByEmail(user.getEmail());
    }

    public void registerUser(pl.coderslab.charity.model.User user) {

        String verificationToken = generateVerificationToken();

        user.setVerificationToken(verificationToken);
        user.setVerified(false);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        roleService.assignUserRole(user);
        userRepository.save(user);

        // Create a verification token entity and associate it with the user
        VerificationToken tokenEntity = new VerificationToken();
        tokenEntity.setToken(verificationToken);
        tokenEntity.setUser(user);
        verificationTokenRepository.save(tokenEntity);

        // Send verification email to the user
        sendVerificationEmail(user.getEmail(), verificationToken);
    }

    private String generateVerificationToken() {
        return RandomStringUtils.randomAlphanumeric(64);
    }

    // Send verification email
    private void sendVerificationEmail(String email, String verificationToken) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.office365.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // SMTP username and password
        String username = "mycharityapp@outlook.com";
        String password = "bl4bl4bl4";


        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("mycharityapp@outlook.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Email Verification");
            String verificationLink = "http://localhost:8080/verify?token=" + verificationToken;
            String messageContent = "Kliknij w link aby zweryfikować swój adres e-mail: <a href='" + verificationLink + "'>" + verificationLink + "</a>";
            message.setContent(messageContent, "text/html");
            message.setHeader("Content-Type", "text/html; charset=UTF-8");

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public pl.coderslab.charity.model.User verifyAccount(String verificationToken) {
        VerificationToken token = verificationTokenRepository.findByToken(verificationToken);

        if (token != null) {
            pl.coderslab.charity.model.User user = token.getUser();
            user.setVerified(true);
            userRepository.save(user);
            return user;
        }

        return null; // Invalid token
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
    }

    public void addNewDonationToUser(Donation donation){
        User user = getCurrentUser();
        user.getDonations().add(donation);
        userRepository.save(user);
    }
}
