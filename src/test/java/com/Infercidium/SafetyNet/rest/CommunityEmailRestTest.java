package com.Infercidium.SafetyNet.rest;

import com.infercidium.safetynet.dto.PersonsDTO;
import com.infercidium.safetynet.mapper.PersonsMapper;
import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.rest.CommunityEmailRest;
import com.infercidium.safetynet.service.PersonsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {CommunityEmailRest.class})
class CommunityEmailRestTest {
    @MockBean
    private PersonsService personsS;
    @Autowired
    private CommunityEmailRest communityEmailRest;

    Persons persons = new Persons("Jean", "Bobine", new Address("1 rue du testing"), "Testy", 12345, "456-789-1011", "jbob@email.com");

    PersonsDTO personsDTOmail = new PersonsDTO();
    List<PersonsDTO> personsDTOList = new ArrayList<>();

    @BeforeEach
    private void setUpPerTest() {
        personsDTOmail.setEmail(persons.getEmail());
        personsDTOList.add(personsDTOmail);
    }
    @Test
    void getCommunityEmail() {
        when(personsS.getPersonsEmail(persons.getCity())).thenReturn(personsDTOList);
        List<PersonsDTO> personsDtoList = communityEmailRest.getCommunityEmail(persons.getCity());
        assertEquals(personsDTOmail.getEmail(), personsDtoList.get(0).getEmail());
    }
}