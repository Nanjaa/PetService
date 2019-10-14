package com.stephanieolfert.petservice.pet;

import java.util.ArrayList;
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
    public List<Pet> searchPets() {
        return petService.searchPets(new Pet());
    }

    @PostMapping("/pets")
    public @ResponseBody List<Long> createPets(@RequestBody List<Pet> pets) {
        return petService.createPets(pets);
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
