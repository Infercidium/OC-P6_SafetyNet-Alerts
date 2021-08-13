package com.infercidium.safetynet.rest;

import com.infercidium.safetynet.model.Name;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.repository.PersonsRepository;
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
import java.util.Optional;

@RestController
public class PersonsRest {

    private final PersonsRepository personR;
    public PersonsRest(final PersonsRepository personRe) {
        this.personR = personRe;
    }

    @PostMapping(value = "/person")
    public ResponseEntity<Void> createPerson(@Valid @RequestBody final Persons persons) {
        if(!this.personR.existsById(persons.getName())) {
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

    @PutMapping(value = "/person/{firstName}/{lastName}")
    public ResponseEntity<Void> editPerson(@PathVariable final String firstName,
                                           @PathVariable final String lastName,
                                           @Valid @RequestBody final Persons persons) {
        Name name = new Name(firstName, lastName);
        Persons person = persons;
        person.setName(name);
        if(this.personR.existsById(name)) {
            this.personR.save(person);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }//TODO : Trouver un raccourci pour faire un put
    }

    @DeleteMapping(value = "/person/{firstName}/{lastName}")
    public ResponseEntity<Void> removePerson(@PathVariable final String firstName,
                                             @PathVariable final String lastName) {
        Name name = new Name(firstName, lastName);
        Optional<Persons> person = getPerson(firstName, lastName);
        this.personR.delete(person.get());
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/person/{firstName}/{lastName}")
    public Optional<Persons> getPerson(@PathVariable final String firstName,
                                       @PathVariable final String lastName) {
        Name name = new Name(firstName, lastName);
        Optional<Persons> person = this.personR.findById(name);
        return person;
    }

    @GetMapping(value = "/persons")
    public List<Persons> getPersons() {
        List<Persons> persons = this.personR.findAll();
        return persons;
    }

    @GetMapping(value = "/persons/{address}")
    public List<Persons> getPersonsAddress(@PathVariable final String address) {
        List<Persons> persons = this.personR.findByAddressIgnoreCase(address);
        return persons;
    }

    //7. http://localhost:8080/communityEmail?city=<city>
    // TODO : Filtrer pour ne voir que les EMail
    @GetMapping(value = "/communityEmail")
    public List<Persons> getEmailCity(@RequestParam final String city) {
        List<Persons> persons = this.personR.findByCityIgnoreCase(city);
        return persons;
    }
}
