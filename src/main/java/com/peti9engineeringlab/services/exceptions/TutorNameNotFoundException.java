package com.peti9engineeringlab.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TutorNameNotFoundException extends ResponseStatusException {

    public TutorNameNotFoundException(String name) {
        super(HttpStatus.NOT_FOUND, "Tutor with name " + name + " was not found!");
    }
}
