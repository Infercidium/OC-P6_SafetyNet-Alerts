package com.infercidium.safetynet.rest;

import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.repository.PersonsRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonsRest {

    private final PersonsRepository personR;

    public PersonsRest(PersonsRepository personR) {
        this.personR = personR;
    }

    @PostMapping(value = "/person")
    public void addPerson(@RequestBody Persons persons){
        this.personR.save(persons);
    }

    @PutMapping(value = "/person/{firstName}/{lastName}")
    public void editPerson(@PathVariable String firstName, String lastName){

    }
    @GetMapping(value = "/persons")
    public List<Persons> getPersons(){
        return this.personR.findAll();
    }
}
