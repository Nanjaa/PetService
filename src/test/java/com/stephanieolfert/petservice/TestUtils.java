package com.stephanieolfert.petservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stephanieolfert.petservice.pet.Pet;

public class TestUtils {

    public static List<Pet> newValidPetList() {

        List<Pet> pets = new ArrayList<Pet>();
        pets.add(TestUtils.newCat());
        pets.add(TestUtils.newDog());
        pets.add(TestUtils.newBird());
        pets.add(TestUtils.newFish());

        return pets;
    }

    public static List<Pet> newInvalidPetList() {

        List<Pet> pets = new ArrayList<Pet>();
        pets.add(TestUtils.invalidName());
        pets.add(TestUtils.invalidType());
        pets.add(TestUtils.invalidAge());
        pets.add(TestUtils.invalidSex());
        pets.add(TestUtils.invalidDescription());
        pets.add(TestUtils.invalidOwner_email());
        pets.add(TestUtils.invalidImage_url());
        pets.add(TestUtils.newCat());

        return pets;
    }

    public static Pet newCat() {
        return new Pet("kitty", Pet.TYPE_CAT, 1, Pet.SEX_M, "The cutest cat ever!", "stephanie.r.olfert@gmail.com",
                "http://stephanieolfert.com/testimg.png");
    }

    public static Pet newDog() {
        return new Pet("doggy", Pet.TYPE_DOG, 2, Pet.SEX_F, "The cutest dog ever!", "stephanie.r.olfert@gmail.com",
                "http://stephanieolfert.com/testimg.png");
    }

    public static Pet newBird() {
        return new Pet("birdy", Pet.TYPE_BIRD, 3, Pet.SEX_M, "The cutest cat ever!", "stephanie.r.olfert@gmail.com",
                "http://stephanieolfert.com/testimg.png");
    }

    public static Pet newFish() {
        return new Pet("fishy", Pet.TYPE_FISH, 4, Pet.SEX_F, "The cutest fish ever!", "stephanie.r.olfert@gmail.com",
                "http://stephanieolfert.com/testimg.png");
    }

    public static Pet invalidName() {
        return new Pet("", Pet.TYPE_CAT, 1, Pet.SEX_M, "The cutest cat ever!", "stephanie.r.olfert@gmail.com",
                "http://stephanieolfert.com/testimg.png");
    }

    public static Pet invalidType() {
        return new Pet("kitty", 8, 1, Pet.SEX_M, "The cutest cat ever!", "stephanie.r.olfert@gmail.com",
                "http://stephanieolfert.com/testimg.png");
    }

    public static Pet invalidAge() {
        return new Pet("kitty", Pet.TYPE_CAT, -1, Pet.SEX_M, "The cutest cat ever!", "stephanie.r.olfert@gmail.com",
                "http://stephanieolfert.com/testimg.png");
    }

    public static Pet invalidSex() {
        return new Pet("kitty", Pet.TYPE_CAT, 1, 3, "The cutest cat ever!", "stephanie.r.olfert@gmail.com",
                "http://stephanieolfert.com/testimg.png");
    }

    public static Pet invalidDescription() {
        return new Pet("kitty", Pet.TYPE_CAT, 1, Pet.SEX_M, null, "stephanie.r.olfert@gmail.com",
                "http://stephanieolfert.com/testimg.png");
    }

    public static Pet invalidOwner_email() {
        return new Pet("kitty", Pet.TYPE_CAT, 1, Pet.SEX_M, "The cutest cat ever!", "stephanie.r.olfert@",
                "http://stephanieolfert.com/testimg.png");
    }

    public static Pet invalidImage_url() {
        return new Pet("kitty", Pet.TYPE_CAT, 1, Pet.SEX_M, "The cutest cat ever!", "stephanie.r.olfert@gmail.com",
                "http://stephanieolfert.com/testimg");
    }

    public static byte[] toJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return mapper.writeValueAsBytes(object);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new byte[0];
    }
}
