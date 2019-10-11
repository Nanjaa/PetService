package com.stephanieolfert.petservice.pet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Service
public class PetService {
	
	List<Pet> pets = Arrays.asList(
			new Pet("kitty", Pet.Type.CAT, 1, Pet.Sex.M, "The cutest cat ever!", "stephanie.r.olfert@gmail.com", "stephanieolfert.com/testimg.png"),
			new Pet("doggy", Pet.Type.DOG, 2, Pet.Sex.F, "The cutest dog ever!", "stephanie.r.olfert@gmail.com", "stephanieolfert.com/testimg.png"),
			new Pet("birdy", Pet.Type.BIRD, 3, Pet.Sex.M, "The cutest bird ever!", "stephanie.r.olfert@gmail.com", "stephanieolfert.com/testimg.png"),
			new Pet("fishy", Pet.Type.FISH, 4, Pet.Sex.F, "The cutest fish ever!", "stephanie.r.olfert@gmail.com", "stephanieolfert.com/testimg.png")
			);
	
	public List<Pet> searchPets() {
		return pets;
	}
	
	@PostMapping("/pets")
	public List<Long> createPets() {
		return new ArrayList<Long>();
	}
	
	@PutMapping("/pets")
	public List<Pet> updatePets() {
		return pets;
	}
	
	@DeleteMapping("/pets")
	public boolean deletePets() {
		return true;
	}
}
