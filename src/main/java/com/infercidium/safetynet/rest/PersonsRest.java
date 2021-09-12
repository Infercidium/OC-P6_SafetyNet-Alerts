package com.infercidium.safetynet.rest;

import com.infercidium.safetynet.dto.PersonsDTO;
import com.infercidium.safetynet.mapper.PersonsMapper;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.service.PersonsI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/person")
public class PersonsRest {

    /**
     * Instantiation of LOGGER in order to inform in console.
     */
    private static final Logger LOGGER
            = LoggerFactory.getLogger(PersonsRest.class);

    /**
     * Instantiation of PersonsService.
     */
    private final PersonsI personsI;

    /**
     * Instantiation of PersonsMapper.
     */
    private final PersonsMapper personsM;

    /**
     * Class constructor.
     * @param personsIn this is PersonsService.
     * @param personsMa this is PersonsMapper.
     */
    public PersonsRest(final PersonsI personsIn,
                       final PersonsMapper personsMa) {
        this.personsI = personsIn;
        this.personsM = personsMa;
    }

    //Post, Put, Delete

    /**
     * Endpoint allowing to post a Person.
     * @param personsDTO this is the information entered by the user.
     * @return 201 Created if successful,
     * 409 conflict if already exist or 400 bad request if bad field.
     */
    @PostMapping
    public ResponseEntity<Void> createPerson(
            @Valid @RequestBody final PersonsDTO personsDTO) {
        Persons persons = personsM.dtoToModel(personsDTO);
        Persons postPerson = personsI.postPerson(persons);

        URI locate = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{firstName}/{lastName}")
                .buildAndExpand(postPerson
                        .getFirstName(), postPerson.getLastName())
                .toUri();

        LOGGER.info("Saving " + persons.getFirstName() + " "
                + persons.getLastName() + " in the Persons table");
        return ResponseEntity.created(locate).build();
    }

    /**
     * Endpoint allowing to edit a Person.
     * @param firstName : allows you to find the resource in the database.
     * @param lastName : allows you to find the resource in the database.
     * @param personsDTO this is the information entered by the user.
     * @return 200 Ok if successful,
     * 404 not found if non-existent or 400 bad request if bad field.
     */
    @PutMapping(value = "/{firstName}/{lastName}")
    public ResponseEntity<Void> editPerson(
            @PathVariable final String firstName,
            @PathVariable final String lastName,
            @RequestBody final PersonsDTO personsDTO) {
        Persons persons = personsM.dtoToModel(personsDTO);
        personsI.editPerson(firstName, lastName, persons);
        LOGGER.info("Person " + firstName + " " + lastName + " modification");
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint allowing to delete a Person.
     * @param firstName : allows you to find the resource in the database.
     * @param lastName : allows you to find the resource in the database.
     * @return 200 Ok if successful, 404 not found if it does not exist.
     */
    @DeleteMapping(value = "/{firstName}/{lastName}")
    public ResponseEntity<Void> removePerson(
            @PathVariable final String firstName,
            @PathVariable final String lastName) {
        personsI.removePerson(firstName, lastName);
        LOGGER.info("Person " + firstName + " " + lastName + " deletion");
        return ResponseEntity.ok().build();
    }

    //Get

    /**
     * Endpoint to get Person.
     * @param firstName : allows you to find the resource in the database.
     * @param lastName : allows you to find the resource in the database.
     * @return person and 200 Ok if successful
     * or 404 not found if it does not exist.
     */
    @GetMapping(value = "/{firstName}/{lastName}")
    public PersonsDTO getPerson(
            @PathVariable final String firstName,
            @PathVariable final String lastName) {
        Persons person = personsI.getPersonName(firstName, lastName);
        PersonsDTO personDTO = personsM.modelToDto(person);
        LOGGER.info("Person found");
        return personDTO;
    }

    /**
     * Endpoint to get Persons.
     * @return List of person and 200 Ok if successful
     * or 404 not found if it does not exist.
     */
    @GetMapping(value = "/")
    public List<PersonsDTO> getPersons() {
        List<Persons> persons = personsI.getPersons();
        List<PersonsDTO> personDTOS = personsM.modelToDto(persons);
        LOGGER.info("List of Persons displayed");
        return personDTOS;
    }
}
