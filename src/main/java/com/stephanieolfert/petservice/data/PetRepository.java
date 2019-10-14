package com.stephanieolfert.petservice.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stephanieolfert.petservice.pet.Pet;

@Repository
public interface PetRepository extends CrudRepository<Pet, Long> {

}
