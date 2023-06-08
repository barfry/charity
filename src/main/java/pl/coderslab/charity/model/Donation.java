package pl.coderslab.charity.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Entity
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Positive(message = "Quantity can't be less or equal 0")
    @Digits(integer = 2, fraction = 0, message = "Quantity should be between a number between 1 and 99")
    @NotNull
    private Integer quantity;

    @ManyToMany
    @JoinTable(name = "donation_categories", joinColumns =
        @JoinColumn(name = "donation_id"), inverseJoinColumns =
            @JoinColumn(name = "category_id"))
    @NotNull
    private Set<Category> categorySet;

    @ManyToOne
    @JoinColumn(name = "institution_id")
    @NotNull
    private Institution institution;

    @NotNull
    @NotBlank(message = "Street field can't be empty")
    @Length(min = 2, max = 20, message = "Street name has to fit between 2 and 20 characters")
    private String street;

    @NotNull
    @NotBlank(message = "City field can't be empty")
    @Length(min = 2, max = 20, message = "City name has to fit between 2 and 20 characters")
    private String city;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[0-9]{2}-[0-9]{3}")
    private String zipCode;

    @NotNull(message = "Pick up date must be chosen")
    @FutureOrPresent(message = "Field allows only present or future day")
    private LocalDate pickUpDate;

    @NotNull(message = "Pick up time must be chosen")
    private LocalTime pickUpTime;

    @NotNull(message = "This field can't be empty")
    @Length(min = 3, max = 50, message = "Comment has to fit between 3 and 50 characters")
    private String pickUpComment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Set<Category> getCategorySet() {
        return categorySet;
    }

    public void setCategorySet(Set<Category> categorySet) {
        this.categorySet = categorySet;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public LocalDate getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(LocalDate pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public LocalTime getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(LocalTime pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public String getPickUpComment() {
        return pickUpComment;
    }

    public void setPickUpComment(String pickUpComment) {
        this.pickUpComment = pickUpComment;
    }

    public Donation(Long id, Integer quantity, Set<Category> categorySet, Institution institution, String street, String city, String zipCode, LocalDate pickUpDate, LocalTime pickUpTime, String pickUpComment) {
        this.id = id;
        this.quantity = quantity;
        this.categorySet = categorySet;
        this.institution = institution;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.pickUpDate = pickUpDate;
        this.pickUpTime = pickUpTime;
        this.pickUpComment = pickUpComment;
    }

    public Donation() {
    }
}
