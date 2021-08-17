package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.Persons;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.util.List;

public interface PersonsI {
    //Post, Put, Delete
    ResponseEntity<Void> createPerson(Persons persons);
    ResponseEntity<Void> editPerson(String firstName,
                                    String lastName,
                                    Persons persons);
    ResponseEntity<Void> removePerson(String firstName,
                                      String lastName);
    //Get
    List<Persons> getPerson(String firstName, String lastName);
    List<Persons> getPersons();

    //URL lié à Persons
    MappingJacksonValue getEmailCommnunity(String city);
}
