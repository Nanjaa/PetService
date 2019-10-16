package com.stephanieolfert.petservice.pet;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.stephanieolfert.petservice.data.PetRepository;
import com.stephanieolfert.petservice.util.PetResponse;
import com.stephanieolfert.petservice.util.PetsList;

@Service
public class PetService {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    private static final Logger LOG = Logger.getLogger(PetService.class.getName());

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
        Map<String, String> errors = new HashMap<String, String>();

        for (Pet pet : pets.getPets()) {
            Optional<Pet> fromDb = petRepository.findById(pet.getId());
            if (fromDb.isPresent()) {
                Pet existing = fromDb.get();
                Pet updated = new Pet();

                updated.setId(pet.getId());
                updated.setName(pet.getName() != null && !pet.getName().isEmpty() ? pet.getName() : existing.getName());
                updated.setType(pet.getType() != 0 ? pet.getType() : existing.getType());
                updated.setAge(pet.getAge() != existing.getAge() ? pet.getAge() : existing.getAge());
                updated.setSex(pet.getSex() != 0 ? pet.getSex() : existing.getSex());
                updated.setDescription(
                        pet.getDescription() != null && !pet.getDescription().isEmpty() ? pet.getDescription()
                                : existing.getDescription());
                updated.setOwner_email(
                        pet.getOwner_email() != null && !pet.getOwner_email().isEmpty() ? pet.getOwner_email()
                                : existing.getOwner_email());
                updated.setImage_url(pet.getImage_url() != null && !pet.getImage_url().isEmpty() ? pet.getImage_url()
                        : existing.getImage_url());

                Set<ConstraintViolation<Pet>> constraintViolations = validator.validate(updated);
                if (constraintViolations.size() > 0) {
                    for (ConstraintViolation<Pet> violation : constraintViolations) {
                        errors.put(pet.toString(), violation.getMessage());
                    }
                } else {
                    if (existing.equals(updated)) {
                        errors.put(pet.toString(), "No changes made to pet, no update required");
                    } else {
                        petRepository.save(updated);
                        updatedPets.add(updated);
                    }
                }
            } else {
                errors.put(pet.toString(), "Invalid id");
            }
        }

        Map<String, Object> responseBody = new HashMap<String, Object>();
        responseBody.put("updatedPets", updatedPets);
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
