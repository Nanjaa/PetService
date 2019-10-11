package com.stephanieolfert.petservice.pets;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PetsController {
	
	@GetMapping("/pets")
	public List<Pet> searchPets() {
		return new ArrayList<Pet>();
	}
	
	@PostMapping("/pets")
	public List<Long> createPets() {
		return new ArrayList<Long>();
	}
	
	@PutMapping("/pets")
	public List<Pet> updatePets() {
		return new ArrayList<Pet>();
	}
	
	@DeleteMapping("/pets")
	public boolean deletePets() {
		return true;
	}
	
}
