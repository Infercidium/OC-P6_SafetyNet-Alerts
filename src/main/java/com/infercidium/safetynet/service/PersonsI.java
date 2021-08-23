package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.Persons;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.util.List;

public interface PersonsI {

    //Post, Put, Delete
    Persons createPerson(Persons persons);
    void editPerson(String firstName, String lastName, Persons persons);
    void removePerson(String firstName, String lastName);

    //Get
    List<Persons> getPerson(String firstName, String lastName);
    List<Persons> getPersons();

    //URL lié à Persons
    List<Persons> getEmailCommnunity(String city);
}
