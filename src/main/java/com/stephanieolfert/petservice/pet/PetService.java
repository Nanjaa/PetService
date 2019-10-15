package com.stephanieolfert.petservice.pet;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.stephanieolfert.petservice.data.PetRepository;
import com.stephanieolfert.petservice.util.PetResponse;
import com.stephanieolfert.petservice.util.PetsList;

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

    public PetResponse createPets(PetsList pets) {
        List<Long> ids = new ArrayList<Long>();
        Map<String, Object> responseBody = new HashMap<String, Object>();
        
        Iterable<Pet> savedPets = petRepository.saveAll(pets.getPets());
        savedPets.forEach((pet) -> {
            ids.add(pet.getId());
        });
        responseBody.put("ids", ids);

        PetResponse response = new PetResponse(new Date(), HttpStatus.OK, null, responseBody);
        return response;
    }

    public List<Pet> updatePets(PetsList pets) {
        List<Pet> updatedPets = new ArrayList<Pet>();

        for (Pet pet : pets.getPets()) {
            Pet existing = petRepository.findById(pet.getId()).get();
            if(existing != null) {
                // TODO: Add error handling
                // TODO: Update this to use clone instead of manually copying object
                Pet updated = new Pet(existing.getName(), existing.getType(), existing.getAge(), existing.getSex(), existing.getDescription(),
                        existing.getOwner_email(), existing.getImage_url());
                updated.setId(pet.getId());

                // TODO: Make sure that these conditionals would actually work
                if (!pet.getName().isEmpty()) {
                    updated.setName(pet.getName());
                }
                if (pet.getType() > 0) {
                    updated.setType(pet.getType());
                }
                if (pet.getAge() != 0) {
                    updated.setAge(pet.getAge());
                }
                if (pet.getSex() != 0) {
                    updated.setSex(pet.getSex());
                }
                if (!pet.getDescription().isEmpty()) {
                    updated.setDescription(pet.getDescription());
                }
                if (!pet.getOwner_email().isEmpty()) {
                    updated.setOwner_email(pet.getOwner_email());
                }
                if (!pet.getImage_url().isEmpty()) {
                    updated.setImage_url(pet.getImage_url());
                }
                
                if (existing.equals(updated)) {
                    LOG.info("No changes made to pet - pet does not require update");
                    // TODO: Flesh out this error handling 
                } else {
                    petRepository.save(updated);
                }

            } else {
                // TODO: Return that this is an invalid pet
            }
        }

        return updatedPets;
    }

    public boolean deletePets(List<Long> ids) {
       // TODO: Add error handling
        Iterable<Pet> existing = petRepository.findAllById(ids);
        petRepository.deleteAll(existing);
        return true;
    }
}
