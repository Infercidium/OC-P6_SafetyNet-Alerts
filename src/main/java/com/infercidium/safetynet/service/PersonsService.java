package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.Name;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.repository.PersonsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

public class PersonsService implements PersonsI{

    private final PersonsRepository personR;

    public PersonsService(final PersonsRepository personRe) {
        this.personR = personRe;
    }


    @Override
    public ResponseEntity<Void> createPerson(Persons persons) {
        if(!personR.existsById(persons.getName())) {
            Persons verify = this.personR.save(persons);

            URI locate = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{firstName}/{lastName}")
                    .buildAndExpand(verify.getFirstName(), verify.getLastName())
                    .toUri();

            return ResponseEntity.created(locate).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Void> editPerson(Name name, Persons persons) {
        Persons person = persons;
        person.setName(name);
        if(personR.existsById(name)) {
            this.personR.save(person);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Void> removePerson(Name name) {
        Optional<Persons> person = getPerson(name);
        this.personR.delete(person.get());
        return ResponseEntity.ok().build();
    }

    @Override
    public Optional<Persons> getPerson(Name name) {
        Optional<Persons> person = this.personR.findById(name);
        return person;
    }

    @Override
    public List<Persons> getPersons() {
       List<Persons> persons = this.personR.findAll();
        return persons;
    }

    @Override
    public List<Persons> getPersonsAddress(String address) {
        return null;
    }

    @Override
    public String fireAlert(String address) {
        return null;
    }

    @Override
    public String personInfo(Name name) {
        return null;
    }

    @Override
    public List<Persons> getEmailCity(String city) {
        List<Persons> persons = personR.findByCityIgnoreCase(city);
        return persons;
    }
}
