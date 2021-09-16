package com.infercidium.safetynet.exception;

import org.apache.commons.lang.NullArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * Exception manager.
 */
@ControllerAdvice
public class ApiExceptionHandler {

    /**
     * Instantiation of LOGGER in order to inform in console.
     */
    private static final Logger LOGGER
            = LoggerFactory.getLogger(ApiExceptionHandler.class);

    /**
     * Exception handling method: NullPointerException.
     * It triggers when a method returns null.
     * @param e represents the exception raised by the API
     *          and raised by the manager.
     * @return the response "resource not found" and a http 404 status.
     */
    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<String> handleNullPointerException(
            final NullPointerException e) {
        String message = "Resource not found";
        LOGGER.error(message, e);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handling method: SQLIntegrityConstraintViolationException.
     * It is raised when an existing unique key is found in duplicate.
     * @param e represents the exception raised by the API
     *          and raised by the manager.
     * @return the response "existing resource" and a http 409 status.
     */
    @ExceptionHandler(value = {SQLIntegrityConstraintViolationException.class})
    public ResponseEntity<String> handleIntegrityConstraintViolationException(
            final SQLIntegrityConstraintViolationException e) {
        String message = "Existing resource";
        LOGGER.error(message, e);
        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

    /**
     * Exception handling method: MethodArgumentNotValidException.
     * It is triggered if the attribute of a DTO or a model is not correct.
     * @param e represents the exception raised by the API
     *          and raised by the manager.
     * @return the response "invalid resource field" and a http 400 status.
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class,
            ConstraintViolationException.class,
            HttpMessageNotReadableException.class})
    public ResponseEntity<String> handleInvalidRessourceException(
            final Exception e) {
        String message = "Invalid resource field";
        LOGGER.error(message, e);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handling method: NullArgumentException.
     * Specific to the link linking MedicalRecords to Persons,
     * is raised if no Persons can be linked to a MedicalRecords
     * attempting to be registered.
     * @param e represents the exception raised by the API
     *          and raised by the manager.
     * @return the response "Link between tables = null" and a http 409 status.
     */
    @ExceptionHandler(value = {NullArgumentException.class})
    public ResponseEntity<String> handleNullArgumentException(
            final NullArgumentException e) {
        String message = "Link between tables = null";
        LOGGER.error(message, e);
        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

    /**
     * Exception handling method: MissingServletRequestParameterException.
     * It is triggered if the attribute of a DTO or a model is not correct.
     * @param e represents the exception raised by the API
     *      *          and raised by the manager.
     * @return the response "Invalid request parameter" and a http 400 status.
     */
    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseEntity<String> handleMissingServletRequestParameterException(
            final MissingServletRequestParameterException e) {
        String message = "Invalid request parameter";
        LOGGER.error(message, e);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
