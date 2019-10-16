package com.stephanieolfert.petservice.pet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "pets")
public class Pet {

    // TODO: Double check best practices for underscores - looks funny to have camel
    // and underscores together such as getOwner_email

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "First name required")
    private String name;

    @Column(name = "type", nullable = false)
    @Min(value = 1, message = "Type must be greater than 0")
    @Max(value = TYPE_FISH, message = "Not a valid type")
    private int type;

    @Column(name = "age", nullable = false)
    @PositiveOrZero(message = "Age cannot be a negative number")
    private int age;

    @Column(name = "sex", nullable = false)
    @Min(value = SEX_M, message = "Sex can only be values 1 (M) or 2 (F)")
    @Max(value = SEX_F, message = "Sex can only be values 1 (M) or 2 (F)")
    private int sex;

    @Column(name = "description", nullable = false)
    @NotBlank(message = "Description required")
    private String description;

    @Column(name = "owner_email", nullable = false)
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid format")
    private String owner_email;

    @Column(name = "image_url", nullable = false)
    @Pattern(regexp = "(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|gif|png)", message = "Image URL must be a valid format")
    private String image_url;

    // Constants
    public static final int TYPE_CAT = 1;
    public static final int TYPE_DOG = 2;
    public static final int TYPE_BIRD = 3;
    public static final int TYPE_FISH = 4;

    public static final int SEX_M = 1;
    public static final int SEX_F = 2;

    public Pet() {
    }

    public Pet(String name, int type, int age, int sex, String description, String owner_email, String image_url) {
        super();
        this.name = name;
        this.type = type;
        this.age = age;
        this.sex = sex;
        this.description = description;
        this.owner_email = owner_email;
        this.image_url = image_url;
    }

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner_email() {
        return owner_email;
    }

    public void setOwner_email(String owner_email) {
        this.owner_email = owner_email;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pet other = (Pet) o;

        return id == other.id && name.equals(other.name) && type == other.type && age == other.age && sex == other.sex
                && description.equals(other.description) && owner_email.equals(other.owner_email)
                && image_url.equals(other.image_url);
    }

    @Override
    public String toString() {
        return "Pet [id=" + id + ", name=" + name + ", type=" + type + ", age=" + age + ", sex=" + sex
                + ", description=" + description + ", owner_email=" + owner_email + ", image_url=" + image_url + "]";
    }

}
