package com.peti9engineeringlab.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PetNotFoundException extends ResponseStatusException {

    public PetNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "Pet with id " + id + " does not exist.");
    }
}
