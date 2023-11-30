package com.peti9engineeringlab.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PetNameNotFoundException extends ResponseStatusException {

    public PetNameNotFoundException(String name) {
        super(HttpStatus.NOT_FOUND, "Pet with name " + name + " was not found!");
    }
}
