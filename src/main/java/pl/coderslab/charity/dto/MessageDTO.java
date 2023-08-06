package pl.coderslab.charity.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MessageDTO {

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
    @Length(min = 2, max = 100, message = "Opis musi zawierać się między 2 a 100 znakami")
    private String message;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageDTO(String firstName, String lastName, String message) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.message = message;
    }

    public MessageDTO() {
    }
}
