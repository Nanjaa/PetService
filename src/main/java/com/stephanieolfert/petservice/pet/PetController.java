package com.stephanieolfert.petservice.pet;

import java.util.Date;
import java.util.HashMap;
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

import com.stephanieolfert.petservice.util.CreateRequest;
import com.stephanieolfert.petservice.util.DeleteRequest;
import com.stephanieolfert.petservice.util.Response;
import com.stephanieolfert.petservice.util.SearchRequest;
import com.stephanieolfert.petservice.util.UpdateRequest;

@RestController
public class PetController {

    @Autowired
    private PetService petService;

    @GetMapping("/pets")
    public @ResponseBody Response searchPets(@RequestBody(required = false) SearchRequest search) {
        return petService.searchPets(search != null ? search.getSearch() : null);
    }

    @PostMapping("/pets")
    public @ResponseBody Response createPets(@Valid @RequestBody CreateRequest pets) {
        return petService.createPets(pets.getPets());
    }

    @PutMapping("/pets")
    public @ResponseBody Response updatePets(@RequestBody UpdateRequest pets) {
        return petService.updatePets(pets.getPets());
    }

    @DeleteMapping("/pets")
    public @ResponseBody Response deletePets(@RequestBody DeleteRequest petIds) {
        return petService.deletePets(petIds.getIds());
    }

    // Exception Handlers
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<String, String>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        Response response = new Response(new Date(), HttpStatus.BAD_REQUEST, errors, null);
        return response;
    }

}
