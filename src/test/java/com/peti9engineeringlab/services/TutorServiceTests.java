package com.peti9engineeringlab.services;

import com.peti9engineeringlab.dto.PetDTO;
import com.peti9engineeringlab.dto.TutorDTO;
import com.peti9engineeringlab.model.Tutor;
import com.peti9engineeringlab.repositories.FakeTutorRepository;
import com.peti9engineeringlab.repositories.TutorRepository;
import com.peti9engineeringlab.services.exceptions.TutorNameNotFoundException;
import com.peti9engineeringlab.services.exceptions.TutorNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TutorServiceTests {

    @Test
    @DisplayName("Tutor should be created")
    void should_save_tutor() {
        var tutor = new Tutor(new TutorDTO(1L, "Paul", "Johnny", LocalDate.now(), Collections.singletonList(new PetDTO(
                1L, "Buddy", "Labrador", LocalDate.now(), "Brown", 25.5, LocalDate.now(), "Rabies", "Paul"
        ))));
        var tutorRepository = mock(TutorRepository.class);

        when(tutorRepository.save(any(Tutor.class))).thenReturn(tutor);
        var tutorService = new TutorService(tutorRepository);

        tutorService.createTutor(tutor.toTutorDTO());

        verify(tutorRepository, times(1)).save(tutor);
    }

    @Test
    @DisplayName("Should find all tutors")
    void findAllTutors() {
        List<Tutor> tutors = new ArrayList<Tutor>();
        var tutorOne = new Tutor(new TutorDTO(1L, "Paul", "Johnny", LocalDate.now(), Collections.singletonList(new PetDTO(
                1L, "Buddy", "Labrador", LocalDate.now(), "Brown", 25.5, LocalDate.now(), "Rabies", "Paul"
        ))));
        var tutorTwo = new Tutor(new TutorDTO(1L, "Mary", "Marie", LocalDate.now(), Collections.singletonList(new PetDTO(
                1L, "Axe", "Cutter", LocalDate.now(), "Yellow", 10.5, LocalDate.now(), "Rabies", "Mary"
        ))));

        var tutorRepository = mock(TutorRepository.class);
        tutors.add(tutorOne);
        tutors.add(tutorTwo);

        when(tutorRepository.findAll()).thenReturn(tutors);

        List<Tutor> tutorList = tutorRepository.findAll();

        assertEquals(2, tutorList.size());
        verify(tutorRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should find a tutor by its id")
    void findTutorById_ShouldReturnTutor() {
        var tutorDTO = new TutorDTO(1L, "Paul", "Johnny", LocalDate.now(), Collections.singletonList(new PetDTO(
                1L, "Buddy", "Labrador", LocalDate.now(), "Brown", 25.5, LocalDate.now(), "Rabies", "Paul"
        )));

        var tutorRepository = mock(TutorRepository.class);

        when(tutorRepository.findById(tutorDTO.id())).thenReturn(Optional.of(new Tutor(tutorDTO)));

        Optional<Tutor> foundTutor = tutorRepository.findById(tutorDTO.id());

        assertTrue(foundTutor.isPresent());
        assertEquals(tutorDTO.id(), foundTutor.get().getId());
        verify(tutorRepository, times(1)).findById(tutorDTO.id());
    }

    @Test
    @DisplayName("Throws 404 NOT_FOUND when a tutor id is not found")
    void findTutorById_WhenTutorDoesNotExist_ShouldThrowTutorNotFoundException() {
        Long tutorId = 1L;
        var tutorRepository = mock(TutorRepository.class);
        var tutorService = new TutorService(tutorRepository);

        when(tutorRepository.findById(tutorId)).thenReturn(Optional.empty());

        TutorNotFoundException exception = assertThrows(TutorNotFoundException.class,
                () -> tutorService.findById(tutorId));

        assertEquals("404 NOT_FOUND \"Tutor not found with id 1\"", exception.getMessage());
    }

    @Test
    @DisplayName("Should find a tutor by any part of its name")
    void findTutorByNameContaining_ShouldReturnTutor() {
        var name = "pa";
        var tutorRepository = new FakeTutorRepository();

        tutorRepository.addTutor(new Tutor(new TutorDTO(1L, "Paul", "Johnny", LocalDate.now(), Collections.singletonList(new PetDTO(
                1L, "Buddy", "Labrador", LocalDate.now(), "Brown", 25.5, LocalDate.now(), "Rabies", "Paul"
        )))));
        tutorRepository.addTutor(new Tutor(new TutorDTO(1L, "Patricia", "Marie", LocalDate.now(), Collections.singletonList(new PetDTO(
                1L, "Axe", "Cutter", LocalDate.now(), "Yellow", 10.5, LocalDate.now(), "Rabies", "Mary"
        )))));

        var tutorService = new TutorService(tutorRepository);

        var result = tutorService.findTutorByName(name);

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Should return 404 NOT_FOUND when tutor name is not found")
    void findPetByNameContaining_WhenNoPetsFound_ShouldThrowPetNameNotFoundException() {
        var name = "Bill";
        var tutorRepository = mock(TutorRepository.class);

        when(tutorRepository.findByNameContainingIgnoreCase(name)).thenReturn(List.of());

        var tutorService = new TutorService(tutorRepository);

        var exception = assertThrows(TutorNameNotFoundException.class,
                () -> tutorService.findTutorByName(name));

        assertEquals("404 NOT_FOUND \"Tutor with name Bill was not found!\"", exception.getMessage());
    }
}