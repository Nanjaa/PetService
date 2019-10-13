package com.stephanieolfert.petservice.data;

import org.springframework.data.repository.CrudRepository;

import com.stephanieolfert.petservice.pet.Pet;

public interface PetRepository extends CrudRepository<Pet, Long> {
    // TODO: This will be where we write out the data connections
}
