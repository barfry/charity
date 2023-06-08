package pl.coderslab.charity.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Length(min = 2, max = 30, message = "Name should")
    private String name;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Length(min = 2, max = 100, message = "Description must be between 2 and 100 characters")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Institution(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Institution() {
    }
}
