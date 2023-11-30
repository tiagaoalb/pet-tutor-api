package com.peti9engineeringlab.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TutorNotFoundException extends ResponseStatusException {

    public TutorNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "Tutor not found with id " + id);
    }
}
