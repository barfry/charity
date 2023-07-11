package pl.coderslab.charity.dto;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserDTO {

    Long id;

    @NotNull
    @NotBlank(message = "To pole nie może być puste")
    @Length(min = 2, max = 30, message = "Imię musi zawierać się między 2 a 30 znakami")
    private String firstName;

    @NotNull
    @NotBlank(message = "To pole nie może być puste")
    @Length(min = 2, max = 30, message = "Nazwisko musi zawierać się między 2 a 30 znakami")
    private String lastName;

    @NotNull
    @NotBlank(message = "To pole nie może być puste")
    @Email(message = "To pole musi być w formacie e-mail np.: adres@domena.pl")
    @Length(min = 5, max = 50, message = "E-mail musi zawierać się między 5 a 50 znakami")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserDTO(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public UserDTO() {
    }
}
