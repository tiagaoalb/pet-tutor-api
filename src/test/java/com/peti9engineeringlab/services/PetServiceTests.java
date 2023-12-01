package com.peti9engineeringlab.services;

import com.peti9engineeringlab.dto.PetDTO;
import com.peti9engineeringlab.model.Pet;
import com.peti9engineeringlab.repositories.PetRepository;
import com.peti9engineeringlab.services.exceptions.DataInfoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PetServiceTests {

    @Test
    @DisplayName("Pet should be created")
    void should_create_pet() {
        var pet = new Pet(new PetDTO(1L, "Buddy", "Labrador", LocalDate.parse("2020-10-01"), "Brown", 25.5, LocalDate.parse("2020-10-01"), "Rabies", "Paul"));
        var petRepository = mock(PetRepository.class);

        when(petRepository.save(any(Pet.class))).thenReturn(pet);
        var petService = new PetService(petRepository);

        petService.createPet(pet.toPetDTO());

        verify(petRepository, times(1)).save(pet);
    }

    @Test
    @DisplayName("Should find all pets")
    void findAllPets_ShouldReturnAllPets() {
        var pets = new ArrayList<Pet>();
        var petOne = new Pet(new PetDTO(1L, "Buddy", "Labrador", LocalDate.parse("2020-10-01"), "Brown", 25.5, LocalDate.parse("2020-10-01"), "Rabies", "Paul"));
        var petTwo = new Pet(new PetDTO(1L, "Max", "Golden Retriever", LocalDate.parse("2020-10-01"), "Yellow", 30.0, LocalDate.parse("2020-10-01"), "Distemper", "Mary"));

        var petRepository = mock(PetRepository.class);
        pets.add(petOne);
        pets.add(petTwo);

        when(petRepository.findAll()).thenReturn(pets);

        var petList = petRepository.findAll();

        assertEquals(2, petList.size());
        verify(petRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should find a pet by its id")
    void findPetById_ShouldReturnPet() {
        var petDTO = new PetDTO(1L, "Buddy", "Labrador", LocalDate.parse("2020-10-01"), "Brown", 25.5, LocalDate.parse("2020-10-01"), "Rabies", "Paul");

        var petRepository = mock(PetRepository.class);

        when(petRepository.findById(petDTO.id())).thenReturn(Optional.of(new Pet(petDTO)));

        var foundPet = petRepository.findById(petDTO.id());

        assertTrue(foundPet.isPresent());
        assertEquals(petDTO.id(), foundPet.get().getId());
        verify(petRepository, times(1)).findById(petDTO.id());
    }

    @Test
    @DisplayName("Throws 404 NOT_FOUND when a pet id is not found")
    void findPetById_WhenPetDoesNotExist_ShouldThrowPetNotFoundException() {
        var petId = 1L;
        var petRepository = mock(PetRepository.class);

        when(petRepository.findById(petId)).thenReturn(Optional.empty());

        var petService = new PetService(petRepository);

        var exception = assertThrows(DataInfoException.class,
                () -> petService.findPetById(petId));

        assertEquals("404 NOT_FOUND \"Pet with id 1 does not exist.\"", exception.getMessage());
    }

    @Test
    @DisplayName("Should find a pet by any part of its name")
    void findPetByNameContaining_ShouldReturnPet() {
        var name = "bud";
        var petRepository = mock(PetRepository.class);

        var pets = new ArrayList<Pet>();
        var pet1 = (new Pet(new PetDTO(1L, "Buddy", "Labrador", LocalDate.now(), "Brown", 25.5, LocalDate.now(), "Rabies", "Paul")));
        var pet2 = (new Pet(new PetDTO(2L, "Budweiser", "Golden Retriever", LocalDate.now(), "Golden", 30.0, LocalDate.now(), "Distemper", "Mary")));

        pets.add(pet1);
        pets.add(pet2);

        when(petRepository.findByNameContainingIgnoreCase(name)).thenReturn(
                pets.stream().filter(pet -> pet.getName().toLowerCase().contains(name.toLowerCase())).toList()
        );

        var petService = new PetService(petRepository);

        var result = petService.findPetByNameContaining(name);

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(pet -> pet.name().equalsIgnoreCase("Buddy")));
        assertTrue(result.stream().anyMatch(pet -> pet.name().equalsIgnoreCase("Budweiser")));
    }

    @Test
    @DisplayName("Should return 404 NOT_FOUND when pet name is not found")
    void findPetByNameContaining_WhenNoPetsFound_ShouldThrowPetNameNotFoundException() {
        var name = "Sven";
        var petRepository = mock(PetRepository.class);

        when(petRepository.findByNameContainingIgnoreCase(name)).thenReturn(List.of());

        var petService = new PetService(petRepository);

        var exception = assertThrows(DataInfoException.class,
                () -> petService.findPetByNameContaining(name));

        assertEquals("404 NOT_FOUND \"Pet with name Sven was not found!\"", exception.getMessage());
    }

    @Test
    @DisplayName("Should update pet name by its id")
    void updatePetNameById_WhenPetExists_ShouldUpdatePet() {
        var petId = 1L;
        var newPetName = "Rex";
        var petRepository = mock(PetRepository.class);

        var existingPet = new Pet(new PetDTO(1L, "Buddy", "Labrador", LocalDate.now(), "Brown", 25.5, LocalDate.now(), "Rabies", "John Doe"));
        when(petRepository.findById(petId)).thenReturn(Optional.of(existingPet));

        var updatedPet = new Pet(new PetDTO(1L, "Rex", "Labrador", LocalDate.now(), "Brown", 25.5, LocalDate.now(), "Rabies", "John Doe"));
        when(petRepository.save(existingPet)).thenReturn(updatedPet);

        var petService = new PetService(petRepository);

        var result = petService.updatePetNameById(petId, newPetName);

        assertNotNull(result);
        assertEquals(newPetName, result.name());

        verify(petRepository, times(1)).findById(petId);
        verify(petRepository, times(1)).save(existingPet);
    }

    @Test
    void updatePetNameById_WhenPetDoesNotExist_ShouldThrowBadRequestException() {
        var petId = 1L;
        var newPetName = "Rex";
        var petRepository = mock(PetRepository.class);

        when(petRepository.findById(petId)).thenReturn(Optional.empty());

        var petService = new PetService(petRepository);

        var exception = assertThrows(DataInfoException.class,
                () -> petService.updatePetNameById(petId, newPetName));

        assertEquals("404 NOT_FOUND \"Pet with id 1 does not exist.\"", exception.getMessage());
        assertEquals("Pet with id 1 does not exist.", exception.getReason());

        verify(petRepository, times(1)).findById(petId);
        verify(petRepository, never()).save(any());
    }

    @Test
    @DisplayName("Delete a pet when it exists")
    void deletePetById_WhenPetExists_ShouldDeletePet() {
        var petId = 1L;
        var petRepository = mock(PetRepository.class);

        when(petRepository.existsById(petId)).thenReturn(true);

        var petService = new PetService(petRepository);

        assertDoesNotThrow(() -> petService.deletePetById(petId));

        verify(petRepository, times(1)).deletePetById(petId);
    }

    @Test
    @DisplayName("Shows 404 NOt_FOUND when pet id is not found")
    void deletePetById_WhenPetDoesNotExist_ShouldThrowNotFoundException() {
        var petId = 1L;
        var petRepository = mock(PetRepository.class);

        when(petRepository.existsById(petId)).thenReturn(false);

        var petService = new PetService(petRepository);

        var exception = assertThrows(DataInfoException.class,
                () -> petService.deletePetById(petId));

        assertEquals("404 NOT_FOUND \"Pet with id 1 does not exist.\"", exception.getMessage());
        assertEquals("Pet with id " + petId + " does not exist.", exception.getReason());

        verify(petRepository, never()).deletePetById(petId);
    }
}