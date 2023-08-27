package pl.coderslab.charity.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.dto.MessageDTO;
import pl.coderslab.charity.dto.UserDTO;
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
import javax.transaction.Transactional;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

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

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Value("${spring.mail.host}")
    private String smtpHost;

    @Value("${spring.mail.port}")
    private int smtpPort;

    @Value("${spring.mail.username}")
    private String emailUsername;

    @Value("${spring.mail.password}")
    private String emailPassword;


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

    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public UserService() {
    }

    public Boolean checkIfEmailExists(pl.coderslab.charity.model.User user) {
        return userRepository.existsByEmail(user.getEmail());
    }

    public Boolean checkIfEmailExistsOnEditUser(UserDTO userDTO) {
        return userRepository.existsByEmail(userDTO.getEmail());
    }

    public void registerUser(pl.coderslab.charity.model.User user) {

        String verificationToken = generateVerificationToken();

        user.setVerificationToken(verificationToken);
        user.setVerified(false);
        user.setEnabled(true);

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        roleService.assignUserRole(user);
        userRepository.save(user);

        VerificationToken tokenEntity = new VerificationToken();
        tokenEntity.setToken(verificationToken);
        tokenEntity.setUser(user);
        verificationTokenRepository.save(tokenEntity);

        sendVerificationEmail(user.getEmail(), verificationToken);
    }

    private String generateVerificationToken() {
        return RandomStringUtils.randomAlphanumeric(64);
    }

    private void sendVerificationEmail(String email, String verificationToken) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(emailUsername, emailPassword);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailUsername));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Weryfikacja e-mail");
            String verificationLink = "http://localhost:8080/verify?token=" + verificationToken;
            String messageContent = "Kliknij w link aby zweryfikować swój adres e-mail: <a href='" + verificationLink + "'>" + verificationLink + "</a>";
            message.setContent(messageContent, "text/html");
            message.setHeader("Content-Type", "text/html;charset=UTF-8");

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

    public void addNewDonationToUser(Donation donation) {
        User user = userRepository.findUserById(getCurrentUser().getId());
        user.getDonations().add(donation);
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findUserById(id);
    }

    public UserDTO getUserDtoByUserId(Long id) {
        User user = getUserById(id);
        UserDTO userDto = new UserDTO();

        userDto.setId(id);
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());

        return userDto;
    }

    public UserDTO getCurrentUserDto() {
        User user = getCurrentUser();
        UserDTO userDto = new UserDTO();

        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());

        return userDto;
    }

    public User editUser(UserDTO userDTO) {
        User user = getUserById(userDTO.getId());

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());

        return userRepository.save(user);
    }

    public void editCurrentUser(UserDTO userDTO) {
        User user = getUserById(userDTO.getId());

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());

        userRepository.save(user);

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        UserDetails updatedUserDetails = userRepository.findByEmail(currentUsername);

        Authentication currentAuth = new UsernamePasswordAuthenticationToken(updatedUserDetails, updatedUserDetails.getPassword(), updatedUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(currentAuth);

    }

    public void blockUserById(Long id) {
        User user = getUserById(id);
        user.setEnabled(false);
        userRepository.save(user);
    }

    public void unblockUserById(Long id) {
        User user = getUserById(id);
        user.setEnabled(true);
        userRepository.save(user);
    }

    public void removeUserById(Long id) {
        VerificationToken verificationToken = userRepository.findVerificationTokenByUserId(id);
        if (verificationToken != null) {
            verificationTokenRepository.delete(verificationToken);
        }
        userRepository.deleteById(id);
    }

    public List<User> getAllUserByRole(String roleName) {
        return userRepository.findByRoles_Name(roleName);
    }

    public void addNewAdmin(pl.coderslab.charity.model.User user) {

        String verificationToken = generateVerificationToken();

        user.setVerificationToken(verificationToken);
        user.setVerified(false);
        user.setEnabled(true);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        roleService.assignAdminRole(user);
        userRepository.save(user);

        VerificationToken tokenEntity = new VerificationToken();
        tokenEntity.setToken(verificationToken);
        tokenEntity.setUser(user);
        verificationTokenRepository.save(tokenEntity);

        sendVerificationEmail(user.getEmail(), verificationToken);
    }

    public Boolean verifyPassword(String oldPassword) {
        User user = getCurrentUser();
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    public void changePassword(String newPassword) {
        User user = getCurrentUser();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }

    public List<Donation> getCurrentUserDonations() {
        return userRepository.findUserById(getCurrentUser().getId()).getDonations();
    }

    public void removeDonationFromUser(Donation donation) {
        User user = userRepository.findUserByDonationId(donation.getId());
        if (user != null) {
            user.getDonations().remove(donation);
            userRepository.save(user);
        }
    }

    @Transactional
    public void initiatePasswordReset(@NotBlank @Email String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User with provided email not found.");
        }

        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);
        userRepository.save(user);

        sendPasswordResetEmail(user.getEmail(), token);
    }

    private void sendPasswordResetEmail(String email, String token) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(emailUsername, emailPassword);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("mycharityapp@outlook.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Reset hasła - CharityApp");
            String verificationLink = "http://localhost:8080/reset-password?token=" + token;
            String messageContent = "Kliknij w link aby zresetować swoje hasło: <a href='" + verificationLink + "'>" + verificationLink + "</a>";
            message.setContent(messageContent, "text/html");
            message.setHeader("Content-Type", "text/html;charset=UTF-8");

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void resetPassword(String token, @NotBlank String newPassword) {
        User user = userRepository.findByVerificationToken(token);
        if (user == null) {
            throw new IllegalArgumentException("Invalid token.");
        }

        // Set the new password and remove the reset token (use verificationToken property)
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setVerificationToken(null);
        userRepository.save(user);
    }


    public void sendMessageFromUser(MessageDTO messageDTO) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(emailUsername, emailPassword);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailUsername));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailUsername));
            message.setSubject("Message from: " + messageDTO.getFirstName() + " " + messageDTO.getLastName());
            message.setText(messageDTO.getMessage());

            Transport.send(message);

            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
