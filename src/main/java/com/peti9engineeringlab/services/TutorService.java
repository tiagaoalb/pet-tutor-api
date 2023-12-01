package com.peti9engineeringlab.services;

import com.peti9engineeringlab.dto.TutorDTO;
import com.peti9engineeringlab.model.Tutor;
import com.peti9engineeringlab.repositories.TutorRepository;
import com.peti9engineeringlab.services.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TutorService {

    private final TutorRepository tutorRepository;

    @Transactional
    public TutorDTO createTutor(TutorDTO tutorDTO) {
        var tutorName = tutorDTO.name();
        if (tutorRepository.existsByName(tutorName)) {
            throw new DataNotFoundException(HttpStatus.CONFLICT, "Tutor with name " + tutorName + " already exists.");
        }
        var newTutor = new Tutor(tutorDTO);
        var savedTutor = tutorRepository.save(newTutor);
        return savedTutor.toTutorDTO();
    }

    @Transactional
    public List<TutorDTO> findAll() {
        var tutors = tutorRepository.findAll();
        return tutors.stream().map(Tutor::toTutorDTO).toList();
    }

    @Transactional
    public TutorDTO findById(Long id) {
        var tutor = tutorRepository.findById(id).orElseThrow(() -> new DataNotFoundException(HttpStatus.NOT_FOUND, "Tutor with id " + id + " was not found"));
        return tutor.toTutorDTO();
    }

    @Transactional
    public List<TutorDTO> findTutorByName(String name) {
        var tutors = tutorRepository.findByNameContainingIgnoreCase(name);
        if (tutors.isEmpty()) {
            throw new DataNotFoundException(HttpStatus.NOT_FOUND, "Tutor with name " + name + " was not found");
        }
        return tutors.stream().map(Tutor::toTutorDTO).toList();
    }
}
