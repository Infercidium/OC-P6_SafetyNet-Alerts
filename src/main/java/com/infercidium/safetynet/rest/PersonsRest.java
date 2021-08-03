package com.infercidium.safetynet.rest;

import com.infercidium.safetynet.exceptions.NoExistPersonsException;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.repository.PersonsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class PersonsRest {

    private final PersonsRepository personR;
    public PersonsRest(PersonsRepository personR) { this.personR = personR; }

    @PostMapping(value = "/person")
    public ResponseEntity<Void> addPerson(@RequestBody Persons persons){

        Persons verify = this.personR.save(persons);

        if(verify == null) return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{firstName}/{lastName}")
                .buildAndExpand(verify.getFirstName(), verify.getLastName()).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/person/{firstName}/{lastName}")
    public List<Persons> getPerson(@PathVariable String firstName, @PathVariable String lastName) throws NoExistPersonsException {

        List<Persons> verify = this.personR.findByFirstNameAndLastName(firstName, lastName);
        if(verify.isEmpty()) throw new NoExistPersonsException("The person " + firstName + " " + lastName + " cannot be found or does not exist.");
        return verify;
    }

    @GetMapping(value = "/persons")
    public List<Persons> getPersons() throws NoExistPersonsException {

        List<Persons> verify = this.personR.findAll();
        if(verify.isEmpty()) throw new NoExistPersonsException("No person findable or existing.");
        return verify;
        }

    //7. http://localhost:8080/communityEmail?city=<city>
    @GetMapping(value = "/communityEmail")
    public List<Persons> getEmailCity(@RequestParam String city) throws NoExistPersonsException {

        List<Persons> verify = personR.findByCity(city);
        if(verify.isEmpty()) throw new NoExistPersonsException("No email from a person found or existing in this city.");
        return verify;
    }
}
