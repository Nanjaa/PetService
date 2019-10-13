package com.stephanieolfert.petservice.pet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.stephanieolfert.petservice.data.PetRepository;

@Service
public class PetService {

    private static final Logger LOG = Logger.getLogger( PetService.class.getName() );

    List<Pet> examplePets = Arrays.asList(
            new Pet("kitty", Pet.Type.CAT, 1, Pet.Sex.M, "The cutest cat ever!", "stephanie.r.olfert@gmail.com", "stephanieolfert.com/testimg.png"),
            new Pet("doggy", Pet.Type.DOG, 2, Pet.Sex.F, "The cutest dog ever!", "stephanie.r.olfert@gmail.com", "stephanieolfert.com/testimg.png"),
            new Pet("birdy", Pet.Type.BIRD, 3, Pet.Sex.M, "The cutest bird ever!", "stephanie.r.olfert@gmail.com", "stephanieolfert.com/testimg.png"),
            new Pet("fishy", Pet.Type.FISH, 4, Pet.Sex.F, "The cutest fish ever!", "stephanie.r.olfert@gmail.com", "stephanieolfert.com/testimg.png")
            );
    
    @Autowired
    private PetRepository petRepository;

    
    public List<Pet> searchPets(Pet params) {
        List<Pet> pets = new ArrayList<Pet>();
        
        // TODO: Build the query using dbutils
        // TODO: This is where we'll query the db

        return examplePets;
    }

    @PostMapping("/pets")
    public List<Long> createPets(List<Pet> pets) {
        List<Long> ids = new ArrayList<Long>();

        for (Pet pet : pets) {
            if (pet.validateFields()) {
                // TODO: This is where we'll add them to the db
            } else {
                LOG.warning("This pet had invalid fields and could not be created: " + pet.toString());
            }
        }

        return ids;
    }

    @PutMapping("/pets")
    public List<Pet> updatePets(List<Pet> pets) {
        List<Pet> updatedPets = new ArrayList<Pet>();

        for (Pet pet : pets) {
            if (pet.validateFields()) {
                // TODO: This is where we'll update them in the db
                updatedPets.add(pet);
            } else {
                LOG.warning("This pet had invalid fields and could not be updated: " + pet.toString());
            }
        }

        return updatedPets;
    }

    @DeleteMapping("/pets")
    public boolean deletePets() {
        boolean deleted = false;

        // TODO: this is where we'll delete it from the db

        return deleted;
    }
}
