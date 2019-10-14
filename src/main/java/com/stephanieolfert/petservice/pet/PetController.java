package com.stephanieolfert.petservice.pet;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PetController {

    @Autowired
    private PetService petService;

    @GetMapping("/pets")
    public @ResponseBody List<Pet> searchPets() {
        return petService.searchPets();
    }

    @PostMapping("/pets")
    public @ResponseBody List<Long> createPets(@RequestBody Pet pets) {
        return petService.createPets(pets);
    }

    @PutMapping("/pets")
    public @ResponseBody List<Pet> updatePets(@RequestBody Collection<Pet> pets) {
        return petService.updatePets(pets);
    }

    @DeleteMapping("/pets")
    public boolean deletePets(@RequestBody Collection<Long> petIds) {
        return petService.deletePets();
    }

}
