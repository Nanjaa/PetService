package com.stephanieolfert.petservice.pet;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.stephanieolfert.petservice.data.PetRepository;
import com.stephanieolfert.petservice.util.PetList;
import com.stephanieolfert.petservice.util.PetResponse;

@Service
public class PetService {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PetRepository petRepository;

    public PetResponse searchPets(OptionalFieldsPet search) {

        List<Pet> pets = new ArrayList<Pet>();
        Map<String, String> errors = new HashMap<String, String>();

        if (search == null) {
            petRepository.findAll().forEach(pets::add);
        } else {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Pet> query = builder.createQuery(Pet.class);
            Root<Pet> r = query.from(Pet.class);

            List<Predicate> predicates = new ArrayList<Predicate>();
            if (search.getId() != null) {
                predicates.add(builder.equal(r.get("id"), search.getId().get()));
            }
            if (search.getName() != null) {
                predicates.add(builder.like(builder.lower(r.get("name")), search.getName().get().toLowerCase()));
            }
            if (search.getType() != null) {
                predicates.add(builder.equal(r.get("type"), search.getType().get()));
            }
            if (search.getAge() != null) {
                predicates.add(builder.equal(r.get("age"), search.getAge().get()));
            }
            if (search.getSex() != null) {
                predicates.add(builder.equal(r.get("sex"), search.getSex().get()));
            }
            if (search.getDescription() != null) {
                predicates.add(
                        builder.like(builder.lower(r.get("description")), search.getDescription().get().toLowerCase()));
            }
            if (search.getOwner_email() != null) {
                predicates.add(
                        builder.like(builder.lower(r.get("owner_email")), search.getOwner_email().get().toLowerCase()));
            }
            if (search.getImage_url() != null) {
                predicates.add(
                        builder.like(builder.lower(r.get("image_url")), search.getImage_url().get().toLowerCase()));
            }

            if (predicates.size() > 0) {
                query.where(predicates.toArray(new Predicate[predicates.size()]));
                pets = entityManager.createQuery(query).getResultList();
            } else {
                errors.put(search.toString(), "Search query passed without criteria");
            }
        }

        Map<String, Object> responseBody = new HashMap<String, Object>();
        responseBody.put("pets", pets);
        PetResponse response = new PetResponse(new Date(), HttpStatus.OK, errors, responseBody);
        return response;

    } // searchPets();

    public PetResponse createPets(PetList pets) {

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

    public PetResponse updatePets(List<OptionalFieldsPet> pets) {

        List<Pet> updatedPets = new ArrayList<Pet>();
        Map<String, String> errors = new HashMap<String, String>();

        for (OptionalFieldsPet pet : pets) {
            if (pet.getId() == null) {
                errors.put(pet.toString(), "ID required to update pet");
                continue;
            }

            Long id = pet.getId().get();
            Optional<Pet> fromDb = petRepository.findById(id);
            if (fromDb.isPresent()) {
                Pet existing = fromDb.get();
                Pet updated = new Pet();

                updated.setId(id);
                updated.setName(pet.getName() != null ? pet.getName().get() : existing.getName());
                updated.setType(pet.getType() != null ? pet.getType().get() : existing.getType());
                updated.setAge(pet.getAge() != null ? pet.getAge().get() : existing.getAge());
                updated.setSex(pet.getSex() != null ? pet.getSex().get() : existing.getSex());
                updated.setDescription(
                        pet.getDescription() != null ? pet.getDescription().get() : existing.getDescription());
                updated.setOwner_email(
                        pet.getOwner_email() != null ? pet.getOwner_email().get() : existing.getOwner_email());
                updated.setImage_url(pet.getImage_url() != null ? pet.getImage_url().get() : existing.getImage_url());

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
