package com.stephanieolfert.petservice.pet;

import java.util.ArrayList;
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

    @Autowired
    private PetRepository petRepository;

    
    public List<Pet> searchPets(Pet params) {
        // TODO: For now, just returning all - refine with specific search
        List<Pet> pets = new ArrayList<Pet>();
        petRepository.findAll().forEach(pets::add);
        return pets;
    }

    @PostMapping("/pets")
    public List<Long> createPets(List<Pet> pets) {
        List<Long> ids = new ArrayList<Long>();
        for (Pet pet : pets) {
            Pet savedPet = petRepository.save(pet);
            if (savedPet != null && savedPet.getId() > 0) {
                ids.add(savedPet.getId());
            } else {
                LOG.warning("Following pet could not be saved: ");
            }
        }

        return ids;
    }

    @PutMapping("/pets")
    public List<Pet> updatePets(List<Pet> pets) {
        List<Pet> updatedPets = new ArrayList<Pet>();

        for (Pet pet : pets) {
         // TODO: This is where we'll update them in the db
            updatedPets.add(pet);
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
