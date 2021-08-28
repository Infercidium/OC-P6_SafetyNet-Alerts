package com.infercidium.safetynet.service;

import com.infercidium.safetynet.dto.PersonsDTO;
import com.infercidium.safetynet.model.Persons;

import java.util.List;

public interface PersonsI {
    //Post, Put, Delete
    Persons postPerson(Persons persons);
    void editPerson(String firstName, String lastName, Persons persons);
    void removePerson(String firstName, String lastName);

    //Get
    Persons getPersonName(String firstName, String lastName);
    List<Persons> getPersons();

    //URL lié à Persons
    List<Persons> getPersonsAddress(String address);
    List<Persons> getPersonsCity(String city);
    List<PersonsDTO> personsToPersonsdtoEmail(List<Persons> persons);

    //Methods Tiers
    boolean personCheck(String firstName, String lastName);
}
