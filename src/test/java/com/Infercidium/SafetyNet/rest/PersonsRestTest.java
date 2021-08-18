package com.Infercidium.SafetyNet.rest;

import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.rest.PersonsRest;
import com.infercidium.safetynet.service.PersonsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PersonsRestTest {

    @MockBean
    private PersonsService personsService;

    @Autowired
    private PersonsRest personsRest;

    @Test
    void postPerson() {
        Persons persons = new Persons("Jean", "Bobine",
                "2 impasse du bobard", "Boniment", 12345,
                "777-666-1234", "jbob@email.com");
        ResponseEntity<Void> postPerson = personsRest.postPerson(persons);
        System.out.println(postPerson);
    }

    @Test
    void editPerson() {
    }

    @Test
    void removePerson() {
    }

    @Test
    void getPerson() {
    }

    @Test
    void getPersons() {
    }

    @Test
    void getEmailCommunity() {
    }
}