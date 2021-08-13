package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.Name;
import com.infercidium.safetynet.model.Persons;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface PersonsI {
    //Post, Put, Delete, Get
    ResponseEntity<Void> createPerson(Persons persons);
    ResponseEntity<Void> editPerson(Name name, Persons persons);
    ResponseEntity<Void> removePerson(Name name);
    Optional<Persons> getPerson(Name name);
    List<Persons> getPersons();

    //URL lié à Persons
    List<Persons> getPersonsAddress(String address);
    String fireAlert(String address);
    String personInfo(Name name);
    List<Persons> getEmailCity(String city);
}
