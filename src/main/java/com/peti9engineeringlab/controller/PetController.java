package com.peti9engineeringlab.controller;

import com.peti9engineeringlab.dto.PetDTO;
import com.peti9engineeringlab.services.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService service;

    public PetController(PetService petService) {
        this.service = petService;
    }

    @PostMapping
    public ResponseEntity<PetDTO> createPet(@RequestBody PetDTO pet) {
        return new ResponseEntity<>(service.createPet(pet), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PetDTO>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PetDTO> findPetById(@PathVariable Long id) {
        return new ResponseEntity<>(service.findPetById(id), HttpStatus.OK);
    }

    @GetMapping("/name")
    @ResponseBody
    public ResponseEntity<List<PetDTO>> findByName(@RequestParam String name) {
        return new ResponseEntity<>(service.findPetByNameContaining(name), HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}/name")
    public ResponseEntity<PetDTO> updatePetNameById(@PathVariable Long id, @RequestParam String newName) {
        PetDTO updatedPetDTO = service.updatePetNameById(id, newName);
        return new ResponseEntity<>(updatedPetDTO, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletePetById(@PathVariable Long id) {
        service.deletePetById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
