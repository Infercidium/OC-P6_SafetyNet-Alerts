package com.infercidium.safetynet.rest;

import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.service.PersonsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PersonsRest {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(PersonsRest.class);
    private final PersonsService personsS;

    public PersonsRest(final PersonsService personsSe) {
        this.personsS = personsSe;
    }

    //Post, Put, Delete
    @PostMapping(value = "/person")
    public ResponseEntity<Void> postPerson(
            @Valid @RequestBody final Persons persons) {
        ResponseEntity<Void> result = personsS.createPerson(persons);
        LOGGER.info("Saving " + persons.getFirstName()
                + " " + persons.getLastName() + " in the Persons table");
        return result;
    }

    @PutMapping(value = "/person/{firstName}/{lastName}")
    public ResponseEntity<Void> editPerson(
            @PathVariable final String firstName,
            @PathVariable final String lastName,
            @Valid @RequestBody final Persons persons) {
        ResponseEntity<Void> result
                = personsS.editPerson(firstName, lastName, persons);
        LOGGER.info("Person " + firstName
                + " " + lastName + " modification");
        return result;
    }

    @DeleteMapping(value = "/person/{firstName}/{lastName}")
    public ResponseEntity<Void> removePerson(
            @PathVariable final String firstName,
            @PathVariable final String lastName) {
        ResponseEntity<Void> result
                = personsS.removePerson(firstName, lastName);
        LOGGER.info("Person " + firstName + " " + lastName + " deletion");
        return result;
    }

    //Get
    @GetMapping(value = "/person/{firstName}/{lastName}")
    public MappingJacksonValue getPerson(@PathVariable final String firstName,
                                         @PathVariable final String lastName) {
        List<Persons> person = personsS.getPerson(firstName, lastName);
        MappingJacksonValue personFilter = personsS.personFilterNull(person);
        LOGGER.info("Person found");
        return personFilter;
    }

    @GetMapping(value = "/persons")
    public MappingJacksonValue getPersons() {
        List<Persons> persons = personsS.getPersons();
        MappingJacksonValue personsFilter = personsS.personFilterNull(persons);
        LOGGER.info("List of Persons displayed");
        return personsFilter;
    }

    //URL lié à Persons
    @GetMapping(value = "/communityEmail")
    public MappingJacksonValue getEmailCommunity(@RequestParam final String city) {
        MappingJacksonValue result = personsS.getEmailCommnunity(city);
        LOGGER.info("List of emails from residents of the town of " + city);
        return result;
    }
}
