package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.repository.PersonsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PersonsService implements PersonsI {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonsService.class);
    private final PersonsRepository personsR;
    public PersonsService(final PersonsRepository personsRe) {
        this.personsR = personsRe;
    }

    //Post, Put, Delete
    @Override
    public Persons createPerson(final Persons persons) {
        Persons result = this.personsR.save(persons);
        return result;
    }

    @Override
    public void editPerson(final String firstName, final String lastName, final Persons persons) {
        Persons basicPerson = getPerson(firstName, lastName).get(0);
        System.out.println(basicPerson);
        Persons personChanged = persons;
        personChanged.setId(basicPerson.getId());
        Persons person = personVerification(basicPerson, personChanged);
        this.personsR.save(person);
    }

    @Override
    public void removePerson(final String firstName, final String lastName) {
        List<Persons> person = getPerson(firstName, lastName);
        this.personsR.delete(person.get(0));

    }

    //Get
    @Override
    public List<Persons> getPerson(final String firstName, final String lastName) {
        Optional<Persons> person = this.personsR.findByFirstNameIgnoreCaseAndLastNameIgnoreCase(firstName, lastName);
        if (person.isPresent()) {
            List<Persons> personList = new ArrayList<>();
            personList.add(person.get());
            return personList;
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public List<Persons> getPersons() {
        List<Persons> persons = this.personsR.findAll();
        if (persons.size() != 0) {
            return persons;
        } else {
            throw new NullPointerException();
        }
    }

    //URL lié à Persons
    @Override
    public List<Persons> getEmailCommnunity(final String city) {
        List<Persons> persons = this.personsR.findByCityIgnoreCase(city);
        if (persons.size() != 0) {
            return persons;
        } else {
            throw new NullPointerException();
        }
    }

    //Méthode tiers
    private Persons personVerification(final Persons basicPerson, final Persons personChanged) {
        personChanged.setFirstName(basicPerson.getFirstName());
        personChanged.setLastName(basicPerson.getLastName());
        if (personChanged.getCity() == null) {
            personChanged.setCity(basicPerson.getCity());
        } else if (!personChanged.getCity().equals(basicPerson.getCity())) {
            LOGGER.debug("City modification");
        }
        if (personChanged.getAddress() == null) {
            personChanged.setAddress(basicPerson.getAddress());
        } else if (!personChanged.getAddress()
                .equals(basicPerson.getAddress())) {
            LOGGER.debug("Address modification");
        }
        if (personChanged.getZip() < 1) {
            personChanged.setZip(basicPerson.getZip());
        } else if (personChanged.getZip() != basicPerson.getZip()) {
            LOGGER.debug("Zip modification");
        }
        if (personChanged.getPhone() == null) {
            personChanged.setPhone(basicPerson.getPhone());
        } else if (!personChanged.getPhone().equals(basicPerson.getPhone())) {
            LOGGER.debug("Phone modification");
        }
        if (personChanged.getEmail() == null) {
            personChanged.setEmail(basicPerson.getEmail());
        } else if (!personChanged.getEmail().equals(basicPerson.getEmail())) {
            LOGGER.debug("Email modification");
        }
        return personChanged;
    }

    public boolean personCheck(final String firstName, final String lastName) {
        return personsR.findByFirstNameIgnoreCaseAndLastNameIgnoreCase(
                firstName, lastName).isPresent();
    }
}
