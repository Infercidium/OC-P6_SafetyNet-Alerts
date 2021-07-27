package com.infercidium.safetynet.rest;

import com.infercidium.safetynet.dao.PersonDAO;
import com.infercidium.safetynet.model.Person;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonRest {

    @PostMapping(value = "/person")
    public void addPerson(@RequestBody Person person){
        PersonDAO.add(person);
    }

    @PutMapping(value = "/person/{firstName}/{lastName}")
    public void editPerson(@PathVariable String firstName, String lastName){

    }
}
