package com.stephanieolfert.petservice.pet;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    } // searchPets();

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

    } // createPets();

    public PetResponse updatePets(PetsList pets) {

        List<Pet> updatedPets = new ArrayList<Pet>();
        List<Long> invalidIds = new ArrayList<Long>();
        List<Long> noUpdateRequired = new ArrayList<Long>();

        for (Pet pet : pets.getPets()) {
            Optional<Pet> fromDb = petRepository.findById(pet.getId());
            if(fromDb.isPresent()) {
                Pet existing = fromDb.get();
                Pet updated = new Pet();

                updated.setId(pet.getId());
                updated.setName(pet.getName() != null ? pet.getName() : existing.getName());
                updated.setType(pet.getType() != 0 ? pet.getType() : existing.getType());
                updated.setAge(pet.getAge() != existing.getAge() ? pet.getAge() : existing.getAge());
                updated.setSex(pet.getSex() != 0 ? pet.getSex() : existing.getSex());
                updated.setDescription(pet.getDescription() != null ? pet.getDescription() : existing.getDescription());
                updated.setOwner_email(pet.getOwner_email() != null ? pet.getOwner_email() : existing.getOwner_email());
                updated.setImage_url(pet.getImage_url() != null ? pet.getImage_url() : existing.getImage_url());

                if (existing.equals(updated)) {
                    noUpdateRequired.add(pet.getId());
                } else {
                    petRepository.save(updated);
                    updatedPets.add(updated);
                }
            } else {
                invalidIds.add(pet.getId());
            }
        }

        Map<String, Object> responseBody = new HashMap<String, Object>();
        responseBody.put("updatedPets", updatedPets);
        Map<String, String> errors = new HashMap<String, String>();
        if (invalidIds.size() > 0) {
            invalidIds.forEach((id) -> {
                errors.put("ID: " + id, "Invalid id");
            });
        }
        if (noUpdateRequired.size() > 0) {
            noUpdateRequired.forEach((id) -> {
                errors.put("ID: " + id, "No changes made to pet, no update required");
            });
        }

        PetResponse response = new PetResponse(new Date(), HttpStatus.OK, errors, responseBody);
        return response;

    } // updatePets();

    public PetResponse deletePets(List<Long> ids) {

        Iterable<Pet> existing = petRepository.findAllById(ids);
        Map<String, String> errors = new HashMap<String, String>();
        Map<String, Object> responseBody = new HashMap<String, Object>();
        List<Long> deleted = new ArrayList<Long>();

        for (Pet pet : existing) {
            ids.remove(pet.getId());
            deleted.add(pet.getId());
        }
        if (ids.size() > 0) {
            ids.forEach((id) -> {
                errors.put("ID: " + id.toString(), "Invalid id");
            });
        }
        petRepository.deleteAll(existing);
        responseBody.put("deletedIds", deleted);
        PetResponse response = new PetResponse(new Date(), HttpStatus.OK, errors, responseBody);
        return response;

    } // deletePets();
}
