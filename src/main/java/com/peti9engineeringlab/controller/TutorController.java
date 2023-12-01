package com.peti9engineeringlab.controller;

import com.peti9engineeringlab.dto.TutorDTO;
import com.peti9engineeringlab.services.TutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tutors")
public class TutorController {

    private final TutorService service;

    public TutorController(TutorService tutorService) {
        this.service = tutorService;
    }

    @PostMapping
    public ResponseEntity<TutorDTO> createTutor(@RequestBody TutorDTO tutor) {
        var tutorResponse = service.createTutor(tutor);
        return new ResponseEntity<>(tutorResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TutorDTO>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<TutorDTO> findTutorById(@PathVariable Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping("/name")
    @ResponseBody
    public ResponseEntity<List<TutorDTO>> findByName(@RequestParam String name) {
        return new ResponseEntity<>(service.findTutorByName(name), HttpStatus.OK);
    }
}
