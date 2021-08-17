package com.infercidium.safetynet.exception;

import org.apache.commons.lang.NullArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;


@ControllerAdvice
public class ApiExceptionHandler {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<String> handleNullPointerException(
            final NullPointerException e) {
        String message = "Ressource not found";
        LOGGER.error(message, e);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {SQLIntegrityConstraintViolationException.class})
    public ResponseEntity<String> handleIntegrityConstraintViolationException(
            final SQLIntegrityConstraintViolationException e) {
        String message = "Existing resource";
        LOGGER.error(message, e);
        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<String> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException e) {
        String message = "Invalid resource field";
        LOGGER.error(message, e);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NullArgumentException.class})
    public ResponseEntity<String> handleNullArgumentException(final NullArgumentException e) {
        String message = "Link between tables = null";
        LOGGER.error(message, e);
        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }
}
