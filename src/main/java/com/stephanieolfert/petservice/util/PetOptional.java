package com.stephanieolfert.petservice.util;

import java.util.Optional;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.stephanieolfert.petservice.pet.Pet;

public class PetOptional {

    @Positive(message = "ID must be a positive number")
    private Long id;

    private Optional<String> name = Optional.empty();

    private Optional<@Min(value = 1, message = "Type must be greater than 0") 
        @Max(value = Pet.TYPE_FISH, message = "Not a valid type") Integer> type = Optional.empty();

    private Optional<@PositiveOrZero(message = "Age cannot be a negative number") Integer> age = Optional.empty();

    private Optional<@Min(value = Pet.SEX_M, message = "Sex can only be values 1 (M) or 2 (F)") 
        @Max(value = Pet.SEX_F, message = "Sex can only be values 1 (M) or 2 (F)") Integer> sex = Optional.empty();

    private Optional<String> description = Optional.empty();

    private Optional<@Email(message = "Email must be a valid format") String> owner_email = Optional.empty();

    private Optional<@Pattern(regexp = "(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|gif|png)",
            message = "Image URL must be a valid format") String> image_url = Optional.empty();

    public PetOptional() {
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Optional<String> getName() {
        return name;
    }
    public void setName(Optional<String> name) {
        this.name = name;
    }
    public Optional<Integer> getType() {
        return type;
    }
    public void setType(Optional<Integer> type) {
        this.type = type;
    }
    public Optional<Integer> getAge() {
        return age;
    }
    public void setAge(Optional<Integer> age) {
        this.age = age;
    }
    public Optional<Integer> getSex() {
        return sex;
    }
    public void setSex(Optional<Integer> sex) {
        this.sex = sex;
    }
    public Optional<String> getDescription() {
        return description;
    }
    public void setDescription(Optional<String> description) {
        this.description = description;
    }
    public Optional<String> getOwner_email() {
        return owner_email;
    }
    public void setOwner_email(Optional<String> owner_email) {
        this.owner_email = owner_email;
    }
    public Optional<String> getImage_url() {
        return image_url;
    }
    public void setImage_url(Optional<String> image_url) {
        this.image_url = image_url;
    }

    @Override
    public String toString() {
        return "PetOptional [id=" + id + ", name=" + name + ", type=" + type + ", age=" + age + ", sex=" + sex
                + ", description=" + description + ", owner_email=" + owner_email + ", image_url=" + image_url + "]";
    }

}
