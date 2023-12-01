package com.peti9engineeringlab.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DataInfoException extends ResponseStatusException {
    public DataInfoException(HttpStatus http, String msg) {
        super(http, msg);
    }
}
