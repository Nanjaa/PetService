package com.stephanieolfert.petservice.pet;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.stephanieolfert.petservice.util.PetResponse;
import com.stephanieolfert.petservice.util.PetList;

@RestController
public class PetController {

    @Autowired
    private PetService petService;

    @GetMapping("/pets")
    public @ResponseBody List<Pet> searchPets() {
        return petService.searchPets();
    }

    @PostMapping("/pets")
    public @ResponseBody PetResponse createPets(@Valid @RequestBody PetList pets) {
        return petService.createPets(pets);
    }

    @PutMapping("/pets")
    public @ResponseBody PetResponse updatePets(@RequestBody PetList pets) {
        return petService.updatePets(pets);
    }

    @DeleteMapping("/pets")
    public @ResponseBody PetResponse deletePets(@RequestBody List<Long> petIds) {
        return petService.deletePets(petIds);
    }

    // Exception Handlers
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public PetResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<String, String>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        PetResponse response = new PetResponse(new Date(), HttpStatus.BAD_REQUEST, errors, null);
        return response;
    }

}
