package com.infercidium.safetynet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.MULTIPLE_CHOICES)
public class MultiplePersonsIdentiteException extends RuntimeException {
    public MultiplePersonsIdentiteException(String s) { super(s); }
}
