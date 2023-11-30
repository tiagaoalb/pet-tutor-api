package com.peti9engineeringlab.services;

import com.peti9engineeringlab.dto.PetDTO;
import com.peti9engineeringlab.model.Pet;
import com.peti9engineeringlab.repositories.PetRepository;
import com.peti9engineeringlab.services.exceptions.PetNameNotFoundException;
import com.peti9engineeringlab.services.exceptions.PetNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;

    public PetDTO createPet(PetDTO petDTO) {
        Pet newPet = new Pet(petDTO);
        Pet savedPet = petRepository.save(newPet);
        return savedPet.toPetDTO();
    }

    public List<PetDTO> findAll() {
        List<Pet> pets = petRepository.findAll();
        return pets.stream().map(Pet::toPetDTO).toList();
    }

    public PetDTO findPetById(Long id) {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new PetNotFoundException(id));
        return pet.toPetDTO();
    }

    public List<PetDTO> findPetByNameContaining(String name) {
        List<Pet> pets = petRepository.findByNameContainingIgnoreCase(name);
        if (pets.isEmpty()) {
            throw new PetNameNotFoundException(name);
        }
        return pets.stream().map(Pet::toPetDTO).toList();
    }

    public PetDTO updatePetNameById(Long id, String newName) {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new PetNotFoundException(id));
        pet.setName(newName);

        Pet updatedPet = petRepository.save(pet);
        return updatedPet.toPetDTO();
    }

    @Transactional
    public void deletePetById(Long id) {
        if (!petRepository.existsById(id)) {
            throw new PetNotFoundException(id);
        }
        petRepository.deletePetById(id);

    }
}
