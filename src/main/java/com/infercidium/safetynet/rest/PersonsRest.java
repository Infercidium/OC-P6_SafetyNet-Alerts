package com.infercidium.safetynet.rest;

import com.infercidium.safetynet.filter.PersonsFilter;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.service.PersonsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
public class PersonsRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonsRest.class);
    private final PersonsService personsS;
    private final PersonsFilter personsF;

    public PersonsRest(final PersonsService personsSe, PersonsFilter personsFe) {
        this.personsS = personsSe;
        this.personsF = personsFe;
    }

    //Post, Put, Delete
    @PostMapping(value = "/person")
    public ResponseEntity<Void> postPerson(@Valid @RequestBody final Persons persons) {
        Persons result = personsS.createPerson(persons);

        URI locate = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{firstName}/{lastName}")
                .buildAndExpand(result.getFirstName(), result.getLastName())
                .toUri();

        LOGGER.info("Saving " + persons.getFirstName() + " " + persons.getLastName() + " in the Persons table");
        return ResponseEntity.created(locate).build();
    }

    @PutMapping(value = "/person/{firstName}/{lastName}")
    public ResponseEntity<Void> editPerson(
            @PathVariable final String firstName, @PathVariable final String lastName, @RequestBody final Persons persons) {
        personsS.editPerson(firstName, lastName, persons);
        LOGGER.info("Person " + firstName + " " + lastName + " modification");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/person/{firstName}/{lastName}")
    public ResponseEntity<Void> removePerson(@PathVariable final String firstName, @PathVariable final String lastName) {
        personsS.removePerson(firstName, lastName);
        LOGGER.info("Person " + firstName + " " + lastName + " deletion");
        return ResponseEntity.ok().build();
    }

    //Get
    @GetMapping(value = "/person/{firstName}/{lastName}")
    public MappingJacksonValue getPerson(@PathVariable final String firstName, @PathVariable final String lastName) {
        List<Persons> person = personsS.getPerson(firstName, lastName);
        MappingJacksonValue personFilter = personsF.personsNullFilter(person);
        LOGGER.info("Person found");
        return personFilter;
    }

    @GetMapping(value = "/persons")
    public MappingJacksonValue getPersons() {
        List<Persons> persons = personsS.getPersons();
        MappingJacksonValue personsFilter = personsF.personsNullFilter(persons);
        LOGGER.info("List of Persons displayed");
        return personsFilter;
    }

    //URL lié à Persons
    @GetMapping(value = "/communityEmail")
    public MappingJacksonValue getEmailCommunity(@RequestParam final String city) {
        List<Persons> persons = personsS.getEmailCommnunity(city);
        Set<String> attribute = personsF.newFilterSet("email");
        MappingJacksonValue personsFilter = personsF.personsFilterAdd(persons, attribute);
        LOGGER.info("List of emails from residents of the town of " + city);
        return personsFilter;
    }
}
