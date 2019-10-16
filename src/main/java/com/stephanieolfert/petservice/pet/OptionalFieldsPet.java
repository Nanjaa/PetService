package com.stephanieolfert.petservice.pet;

import java.util.Optional;

public class OptionalFieldsPet {

    private Optional<Long> id;
    private Optional<String> name;
    private Optional<Integer> type;
    private Optional<Integer> age;
    private Optional<Integer> sex;
    private Optional<String> description;
    private Optional<String> owner_email;
    private Optional<String> image_url;

    public Optional<Long> getId() {
        return id;
    }

    public void setId(Optional<Long> id) {
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
        return "PetWithOptional [id=" + id + ", name=" + name + ", type=" + type + ", age=" + age + ", sex=" + sex
                + ", description=" + description + ", owner_email=" + owner_email + ", image_url=" + image_url + "]";
    }

}
