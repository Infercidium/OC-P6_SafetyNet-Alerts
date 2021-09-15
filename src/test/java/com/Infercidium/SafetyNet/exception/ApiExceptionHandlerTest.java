package com.Infercidium.SafetyNet.exception;

import com.infercidium.safetynet.exception.ApiExceptionHandler;
import com.infercidium.safetynet.model.Firestations;
import org.apache.commons.lang.NullArgumentException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {ApiExceptionHandler.class})
class ApiExceptionHandlerTest {

    @Autowired
    private ApiExceptionHandler apiExceptionHandler;

    String ressourceNotFound = "Resource not found";
    String existingRessource = "Existing resource";
    String linkNull = "Link between tables = null";
    String invalidRessourceField = "Invalid resource field";
    String invalidRequestParam = "Invalid request parameter";

    @Test
    void handleNullPointerException() {
        ResponseEntity<String> responseEntity = apiExceptionHandler.handleNullPointerException(new NullPointerException());
        assertEquals(404, responseEntity.getStatusCodeValue());
        assertEquals(ressourceNotFound, responseEntity.getBody());
    }

    @Test
    void handleIntegrityConstraintViolationException() {
        ResponseEntity<String> responseEntity = apiExceptionHandler.handleIntegrityConstraintViolationException(new SQLIntegrityConstraintViolationException());
        assertEquals(409, responseEntity.getStatusCodeValue());
        assertEquals(existingRessource, responseEntity.getBody());
    }

    @Test
    void handleNullArgumentException() {
        ResponseEntity<String> responseEntity = apiExceptionHandler.handleNullArgumentException(new NullArgumentException("Argument"));
        assertEquals(409, responseEntity.getStatusCodeValue());
        assertEquals(linkNull, responseEntity.getBody());
    }

    @Test
    void handleHttpMessageNotReadableException() {
        ResponseEntity<String> responseEntity = apiExceptionHandler.handleInvalidRessourceException(new HttpMessageNotReadableException("test"));
        assertEquals(400, responseEntity.getStatusCodeValue());
        assertEquals(invalidRessourceField, responseEntity.getBody());
    }

    @Test
    void handleMissingServletRequestParameterException() {
        ResponseEntity<String> responseEntity = apiExceptionHandler.handleMissingServletRequestParameterException(new MissingServletRequestParameterException("test", "test"));
        assertEquals(400, responseEntity.getStatusCodeValue());
        assertEquals(invalidRequestParam, responseEntity.getBody());
    }

    @Test
    void handleConstraintViolationException() {
        Set<ConstraintViolation<Firestations>> test = new HashSet<>();
        ResponseEntity<String> responseEntity = apiExceptionHandler.handleInvalidRessourceException(new ConstraintViolationException("test", test));
        assertEquals(400, responseEntity.getStatusCodeValue());
        assertEquals(invalidRessourceField, responseEntity.getBody());
    }
}
