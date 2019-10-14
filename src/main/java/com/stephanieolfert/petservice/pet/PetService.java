package com.stephanieolfert.petservice.pet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stephanieolfert.petservice.data.PetRepository;

@Service
public class PetService {

    private static final Logger LOG = Logger.getLogger( PetService.class.getName() );

    @Autowired
    private PetRepository petRepository;

    
    public List<Pet> searchPets() {
        // TODO: For now, just returning all - refine with specific search
        List<Pet> pets = new ArrayList<Pet>();
        petRepository.findAll().forEach(pets::add);
        return pets;
    }

//    public List<Long> createPets(Collection<Pet> pets) {
//        List<Long> ids = new ArrayList<Long>();
//        for (Pet pet : pets) {
//            Pet savedPet = petRepository.save(pet);
//            if (savedPet != null && savedPet.getId() > 0) {
//                ids.add(savedPet.getId());
//            } else {
//                LOG.warning("Following pet could not be saved: ");
//            }
//        }
//
//        return ids;
//    }
    
    public List<Long> createPets(Pet pet) {
        List<Long> ids = new ArrayList<Long>();
        Pet savedPet = petRepository.save(pet);
        if (savedPet != null && savedPet.getId() > 0) {
            ids.add(savedPet.getId());
        } else {
            LOG.warning("Following pet could not be saved: ");
        }

        return ids;
    }

    public List<Pet> updatePets(Collection<Pet> pets) {
        List<Pet> updatedPets = new ArrayList<Pet>();

        for (Pet pet : pets) {
         // TODO: This is where we'll update them in the db
            updatedPets.add(pet);
        }

        return updatedPets;
    }

    public boolean deletePets() {
        boolean deleted = false;

        // TODO: this is where we'll delete it from the db

        return deleted;
    }
}
