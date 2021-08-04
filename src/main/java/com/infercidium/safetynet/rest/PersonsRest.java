package com.infercidium.safetynet.rest;

import com.infercidium.safetynet.exceptions.MultiplePersonsIdentiteException;
import com.infercidium.safetynet.exceptions.NoExistPersonsException;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.repository.PersonsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class PersonsRest {

    private final PersonsRepository personR;
    public PersonsRest(PersonsRepository personR) { this.personR = personR; }

    @PostMapping(value = "/person")
    public ResponseEntity<Void> addPerson(@RequestBody Persons persons){

        Persons verify = this.personR.save(persons);

        if(verify == null) return ResponseEntity.noContent().build(); //TODO : Utilité ?

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{firstName}/{lastName}")
                .buildAndExpand(verify.getFirstName(), verify.getLastName()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/person/{firstName}/{lastName}")
    public void putPerson(@PathVariable String firstName, @PathVariable String lastName, @RequestBody Persons persons) {
        //TODO : Trouver un raccourci pour faire un put
    }

    @DeleteMapping(value = "/person/{firstName}/{lastName}")
    public ResponseEntity<Object> dellPerson(@PathVariable String firstName, @PathVariable String lastName){
        List<Persons> verify = getPerson(firstName, lastName);
        if(verify.size() > 1) {
            throw new MultiplePersonsIdentiteException("More than one person has this identity : " + verify + " Added the ID of the person referred to below: /<ID>");
        } else {
            this.personR.delete(verify.get(0));
            return ResponseEntity.ok().build();
        }
    }

    @DeleteMapping(value = "/person/{firstName}/{lastName}/{id}")
    public ResponseEntity<Object> dellPerson(@PathVariable String firstName, @PathVariable String lastName, @PathVariable Long id){
        Optional<Persons> verify = this.personR.findById(id);
        if(!verify.isPresent()) {
            throw new NoExistPersonsException("The id : " + id + " is not assigned.");
        } else if(!verify.get().getFirstName().equals(firstName) || !verify.get().getLastName().equals(lastName)) {
            throw new NoExistPersonsException("The id : " + id + " does not correspond with the first name : " + firstName + " and the last name : " + lastName + ".");
        } else {
            this.personR.delete(verify.get());
            return ResponseEntity.ok().build();
        }
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

    //7. http://localhost:8080/communityEmail?city=<city> //TODO : Filtrer pour ne voir que les EMail
    @GetMapping(value = "/communityEmail")
    public List<Persons> getEmailCity(@RequestParam String city) throws NoExistPersonsException {

        List<Persons> verify = personR.findByCity(city);
        if(verify.isEmpty()) throw new NoExistPersonsException("No email from a person found or existing in this city.");
        return verify;
    }
}
