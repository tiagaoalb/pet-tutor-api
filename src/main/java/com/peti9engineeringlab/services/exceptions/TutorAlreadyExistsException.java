package com.peti9engineeringlab.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TutorAlreadyExistsException extends ResponseStatusException {

    public TutorAlreadyExistsException(String name) {
        super(HttpStatus.CONFLICT, "Tutor with name " + name + " already exists.");
    }
}
