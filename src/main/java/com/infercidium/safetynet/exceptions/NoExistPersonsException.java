package com.infercidium.safetynet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoExistPersonsException extends RuntimeException {
    public NoExistPersonsException(String s) {
        super(s);
    }
}
