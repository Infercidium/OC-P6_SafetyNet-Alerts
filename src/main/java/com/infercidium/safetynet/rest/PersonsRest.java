package com.infercidium.safetynet.rest;

import com.infercidium.safetynet.dto.PersonsDTO;
import com.infercidium.safetynet.mapper.PersonsMapper;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.service.PersonsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
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

@RestController
public class PersonsRest {

    /**
     * Instantiation of LOGGER in order to inform in console.
     */
    private static final Logger LOGGER
            = LoggerFactory.getLogger(PersonsRest.class);

    /**
     * Instantiation of PersonsService.
     */
    private final PersonsService personsS;

    /**
     * Instantiation of PersonsMapper.
     */
    private final PersonsMapper personsM;

    /**
     * Class constructor.
     * @param personsSe this is PersonsService.
     * @param personsMa this is PersonsMapper.
     */
    public PersonsRest(final PersonsService personsSe,
                       final PersonsMapper personsMa) {
        this.personsS = personsSe;
        this.personsM = personsMa;
    }

    //Post, Put, Delete

    /**
     * Endpoint allowing to post a Person.
     * @param personsDTO this is the information entered by the user.
     * @return 201 Created if successful,
     * 409 conflict if already exist or 400 bad request if bad field.
     */
    @PostMapping(value = "/person")
    public ResponseEntity<Void> createPerson(
            @Valid @RequestBody final PersonsDTO personsDTO) {
        Persons persons = personsM.dtoToModel(personsDTO);
        Persons postPerson = personsS.postPerson(persons);

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
    @PutMapping(value = "/person/{firstName}/{lastName}")
    public ResponseEntity<Void> editPerson(
            @PathVariable final String firstName,
            @PathVariable final String lastName,
            @RequestBody final PersonsDTO personsDTO) {
        Persons persons = personsM.dtoToModel(personsDTO);
        personsS.editPerson(firstName, lastName, persons);
        LOGGER.info("Person " + firstName + " " + lastName + " modification");
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint allowing to delete a Person.
     * @param firstName : allows you to find the resource in the database.
     * @param lastName : allows you to find the resource in the database.
     * @return 200 Ok if successful, 404 not found if it does not exist.
     */
    @DeleteMapping(value = "/person/{firstName}/{lastName}")
    public ResponseEntity<Void> removePerson(
            @PathVariable final String firstName,
            @PathVariable final String lastName) {
        personsS.removePerson(firstName, lastName);
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
    @GetMapping(value = "/person/{firstName}/{lastName}")
    public PersonsDTO getPerson(
            @PathVariable final String firstName,
            @PathVariable final String lastName) {
        Persons person = personsS.getPersonName(firstName, lastName);
        PersonsDTO personDTO = personsM.modelToDto(person);
        LOGGER.info("Person found");
        return personDTO;
    }

    /**
     * Endpoint to get Persons.
     * @return List of person and 200 Ok if successful
     * or 404 not found if it does not exist.
     */
    @GetMapping(value = "/persons")
    public List<PersonsDTO> getPersons() {
        List<Persons> persons = personsS.getPersons();
        List<PersonsDTO> personDTOS = personsM.modelToDto(persons);
        LOGGER.info("List of Persons displayed");
        return personDTOS;
    }

    //URL lié à Persons

    /**
     * This url allows you to have the list of emails
     * of the inhabitants of a city.
     * @param city : allows you to find the resource in the database.
     * @return List of personsDTO and 200 Ok if successful
     * or 404 not found if it does not exist.
     */
    @GetMapping(value = "/communityEmail")
    public List<PersonsDTO> getCommunityEmail(@RequestParam final String city) {
        List<Persons> persons = personsS.getPersonsCity(city);
        List<PersonsDTO> personsDTO
                = personsS.personsToPersonsdtoEmail(persons);
        LOGGER.info("List of emails from " + city + " residents found");
        return personsDTO;
    }
}
