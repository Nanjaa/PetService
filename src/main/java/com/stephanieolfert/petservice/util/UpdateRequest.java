package com.stephanieolfert.petservice.util;

import java.util.List;

import com.stephanieolfert.petservice.pet.OptionalFieldsPet;

public class UpdateRequest {

    private List<OptionalFieldsPet> pets;

    public List<OptionalFieldsPet> getPets() {
        return pets;
    }

    public void setPets(List<OptionalFieldsPet> pets) {
        this.pets = pets;
    }

    @Override
    public String toString() {
        return "OptionalFieldsPetList [pets=" + pets + "]";
    }

}
