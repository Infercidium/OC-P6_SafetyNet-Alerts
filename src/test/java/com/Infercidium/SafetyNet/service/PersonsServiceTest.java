package com.Infercidium.SafetyNet.service;

import com.infercidium.safetynet.dto.PersonsDTO;
import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.repository.PersonsRepository;
import com.infercidium.safetynet.service.PersonsService;
import com.infercidium.safetynet.service.SecondaryTableService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {PersonsService.class})
class PersonsServiceTest {

    @MockBean
    private PersonsRepository personsR;
    @MockBean
    private SecondaryTableService secondaryTableS;
    @Autowired
    private PersonsService personsService;

    Persons persons = new Persons("Jean", "Bobine", new Address("1 rue du testing"), "Testy", 12345, "456-789-1011", "jbob@email.com");
    List<Persons> personsList = new ArrayList<>();
    PersonsDTO personsDTO = new PersonsDTO();
    List<PersonsDTO> personsDTOList = new ArrayList<>();

    @BeforeEach
    private void setUpPerTest() {
        personsList.add(persons);

        personsDTO.setEmail(persons.getEmail());
        personsDTOList.add(personsDTO);

        when(secondaryTableS.checkAddress(persons.getAddress())).thenReturn(persons.getAddress());
        when(personsR.save(persons)).thenReturn(persons);
        when(personsR.findByFirstNameIgnoreCaseAndLastNameIgnoreCase(persons.getFirstName(), persons.getLastName())).thenReturn(persons);
    }

    @Test
    void postPerson() {
        Persons postpersons = personsService.postPerson(persons);
        assertEquals(persons, postpersons);
    }

    @Test
    void editPerson() {
        personsService.editPerson(persons.getFirstName(), persons.getLastName(), persons);
        verify(personsR, times(1)).save(persons);
    }

    @Test
    void removePerson() {
        personsService.removePerson(persons.getFirstName(), persons.getLastName());
        verify(personsR, times(1)).delete(persons);
    }

    @Test
    void getPersonName() {
        Persons getPersons = personsService.getPersonName(persons.getFirstName(), persons.getLastName());
        assertEquals(persons, getPersons);
    }

    @Test
    void getPersons() {
        when(personsR.findAll()).thenReturn(personsList);
        List<Persons> personsGetList = personsService.getPersons();
        assertEquals(personsList.get(0), personsGetList.get(0));
    }

    @Test
    void getPersonsAddress() {
        when(personsR.findByAddressAddressIgnoreCase(persons.getAddress().getAddress())).thenReturn(personsList);
        List<Persons> personsAddress = personsService.getPersonsAddress(persons.getAddress().getAddress());
        assertEquals(personsList, personsAddress);
    }

    @Test
    void getPersonsCity() {
        when(personsR.findByCityIgnoreCase(persons.getCity())).thenReturn(personsList);
        List<Persons> personsCity = personsService.getPersonsCity(persons.getCity());
        assertEquals(personsList, personsCity);
    }

    @Test
    void personsToPersonsdtoEmail() {
        List<PersonsDTO> personsDtoList = personsService.personsToPersonsdtoEmail(personsList);
        assertEquals(personsDTOList.get(0).getEmail(), personsDtoList.get(0).getEmail());
    }

    @Test
    void personCheck() {
        boolean result = personsService.personCheck(persons.getFirstName(), persons.getLastName());
        assertTrue(result);
    }
}