package com.stephanieolfert.petservice.pet;

import java.util.logging.Logger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Pet {

    private static final Logger LOG = Logger.getLogger( Pet.class.getName() );

    public enum Sex {
        M(1),
        F(2);

        private int value;
        private Sex(int value) {
            this.value = value;
        }
    }
    public enum Type {
        CAT(1),
        DOG(2),
        BIRD(3),
        FISH(4);

        private int value;
        private Type(int value) {
            this.value = value;
        }
    }

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Type type;
    private int age;
    private Sex sex;
    private String description;
    private String owner_email;
    private String image_url;

    public Pet() {
    }

    public Pet(String name, Type type, int age, Sex sex, String description, String owner_email, String image_url) {
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
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public Sex getSex() {
        return sex;
    }
    public void setSex(Sex sex) {
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


    public boolean validateFields() {
        boolean isValid = true;

        // TODO: This enum validation (and below on sex) is very brute force - FIX!
        if (type != Type.BIRD && type != Type.CAT && type != Type.DOG && type != Type.FISH) {
            LOG.warning("Type of pet is invalid");
            isValid = false;
        }
        if (age < 0) {
            LOG.warning("Age cannot be less than 0");
            isValid = false;
        }
        if (sex != Sex.F && sex != Sex.M) {
            LOG.warning("Sex must be either 'm' or 'f'");
            isValid = false;
        }
        if (description == null || description.length() == 0) {
            LOG.warning("Must include a description");
            isValid = false;
        }
        if (owner_email == null || owner_email.length() == 0 && owner_email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
            LOG.warning("Must include owner email");
            isValid = false;
        }
        if (image_url == null || image_url.length() == 0) {
            LOG.warning("Must include image url");
            isValid = false;
        }

        return isValid;
    }

    @Override
    public String toString() {
        return "Pet [id=" + id + ", name=" + name + ", type=" + type + ", age=" + age + ", sex=" + sex
                + ", description=" + description + ", owner_email=" + owner_email + ", image_url=" + image_url + "]";
    }





}
