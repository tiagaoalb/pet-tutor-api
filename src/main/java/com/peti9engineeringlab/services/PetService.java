package com.peti9engineeringlab.services;

import com.peti9engineeringlab.dto.PetDTO;
import com.peti9engineeringlab.model.Pet;
import com.peti9engineeringlab.repositories.PetRepository;
import com.peti9engineeringlab.services.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private static final String PET_WITH_ID = "Pet with id ";
    private static final String DOES_NOT_EXIST = " does not exist.";

    @Transactional
    public PetDTO createPet(PetDTO petDTO) {
        Pet newPet = new Pet(petDTO);
        Pet savedPet = petRepository.save(newPet);
        return savedPet.toPetDTO();
    }

    @Transactional
    public List<PetDTO> findAll() {
        List<Pet> pets = petRepository.findAll();
        return pets.stream().map(Pet::toPetDTO).toList();
    }

    @Transactional
    public PetDTO findPetById(Long id) {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new DataNotFoundException(HttpStatus.NOT_FOUND, PET_WITH_ID + id + DOES_NOT_EXIST));
        return pet.toPetDTO();
    }

    @Transactional
    public List<PetDTO> findPetByNameContaining(String name) {
        List<Pet> pets = petRepository.findByNameContainingIgnoreCase(name);
        if (pets.isEmpty()) {
            throw new DataNotFoundException(HttpStatus.NOT_FOUND, "Pet with name " + name + " was not found!");
        }
        return pets.stream().map(Pet::toPetDTO).toList();
    }

    @Transactional
    public PetDTO updatePetNameById(Long id, String newName) {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new DataNotFoundException(HttpStatus.NOT_FOUND, PET_WITH_ID + id + DOES_NOT_EXIST));
        pet.setName(newName);

        Pet updatedPet = petRepository.save(pet);
        return updatedPet.toPetDTO();
    }

    @Transactional
    public void deletePetById(Long id) {
        if (!petRepository.existsById(id)) {
            throw new DataNotFoundException(HttpStatus.NOT_FOUND, PET_WITH_ID + id + DOES_NOT_EXIST);
        }
        petRepository.deletePetById(id);
    }
}
