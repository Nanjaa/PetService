package com.stephanieolfert.petservice.util;

import java.util.List;

import javax.validation.Valid;

import com.stephanieolfert.petservice.pet.Pet;

public class PetList {
    
    @Valid
    private List<Pet> pets;

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    @Override
    public String toString() {
        return "PetsList [pets=" + pets + "]";
    }

}
