package com.stephanieolfert.petservice.util;

import java.util.List;

import javax.validation.Valid;

public class PetOptionalList {

    @Valid
    private List<PetOptional> pets;

    public List<PetOptional> getPets() {
        return pets;
    }

    public void setPets(List<PetOptional> pets) {
        this.pets = pets;
    }

    @Override
    public String toString() {
        return "PetOptionalList [pets=" + pets + "]";
    }

}
