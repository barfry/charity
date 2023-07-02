package pl.coderslab.charity.model;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "donation")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive(message = "Ilość nie może być równa lub mniejsza niż 0")
    @Digits(integer = 2, fraction = 0, message = "Ilość powinna wynosić pomiędzy 1 a 99")
    @NotNull(message = "To pole nie może być puste")
    private Integer quantity;

    @ManyToMany
    @JoinTable(name = "donation_categories", joinColumns =
        @JoinColumn(name = "donation_id"), inverseJoinColumns =
            @JoinColumn(name = "category_id"))
    @NotNull(message = "Należy wybrać co najmniej jedną kategorię")
    private List<Category> categoryList;

    @ManyToOne
    @JoinColumn(name = "institution_id")
    @NotNull(message = "Należy wybrać instytucję")
    private Institution institution;

    @NotNull
    @NotBlank(message = "To pole nie może być puste")
    @Length(min = 2, max = 20, message = "Nazwa ulicy musi zawierać się między 2 a 20 znakami")
    private String street;

    @NotNull
    @NotBlank(message = "To pole nie może być puste")
    @Length(min = 2, max = 20, message = "Nazwa miasta musi zawierać się między 2 a 20 znakami")
    private String city;

    @NotNull
    @NotBlank(message = "To pole nie może być puste")
    @Pattern(regexp = "^[0-9]{2}-[0-9]{3}", message = "Należy wprowadzić format kodu pocztowego np.: 12-345")
    private String zipCode;

    @NotNull(message = "Należy wybrać datę odbioru")
    @FutureOrPresent(message = "Należy wybrać wyłącznie datę dzisiejszą lub przyszłą")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate pickUpDate;

    @NotNull(message = "Należy wybrać godzinę odbioru")
    private LocalTime pickUpTime;

    @NotNull
    @NotBlank(message = "To pole nie może być puste")
    @Pattern(regexp = "^[0-9]{9}$", message = "Numer telefonu powinien zawierać 9 cyfr")
    private String phoneNumber;

    @NotNull
    @NotBlank(message = "To pole nie może być puste")
    @Length(min = 3, max = 50, message = "Komentarz musi zawierać się między 2 a 50 znakami")
    private String pickUpComment;

    private Boolean collected = false;
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

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPickUpComment() {
        return pickUpComment;
    }

    public void setPickUpComment(String pickUpComment) {
        this.pickUpComment = pickUpComment;
    }

    public Boolean getCollected() {
        return collected;
    }

    public void setCollected(Boolean collected) {
        this.collected = collected;
    }

    public Donation(Long id, Integer quantity, List<Category> categoryList, Institution institution, String street, String city, String zipCode, LocalDate pickUpDate, LocalTime pickUpTime, String phoneNumber, String pickUpComment, Boolean collected) {
        this.id = id;
        this.quantity = quantity;
        this.categoryList = categoryList;
        this.institution = institution;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.pickUpDate = pickUpDate;
        this.pickUpTime = pickUpTime;
        this.phoneNumber = phoneNumber;
        this.pickUpComment = pickUpComment;
        this.collected = collected;
    }

    public Donation() {
    }
}
