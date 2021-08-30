package com.Infercidium.SafetyNet.rest;

import com.infercidium.safetynet.dto.PersonsDTO;
import com.infercidium.safetynet.mapper.PersonsMapper;
import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.rest.MedicalRecordsRest;
import com.infercidium.safetynet.rest.PersonsRest;
import com.infercidium.safetynet.service.PersonsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {PersonsRest.class})
class PersonsRestTest {

    @MockBean
    private PersonsService personsS;
    @MockBean
    private PersonsMapper personsM;
    @Autowired
    private PersonsRest personsRest;

    Persons persons = new Persons("Jean", "Bobine", new Address("1 rue du testing"), "Testy", 12345, "456-789-1011", "jbob@email.com");
    List<Persons> personsList = new ArrayList<>();
    PersonsDTO personsDTO = new PersonsDTO();
    PersonsDTO personsDTOmail = new PersonsDTO();
    List<PersonsDTO> personsDTOList = new ArrayList<>();

    @BeforeEach
    private void setUpPerTest() {
        personsDTO.setFirstName(persons.getFirstName());
        personsDTO.setLastName(persons.getLastName());
        personsDTO.setAddress(persons.getAddress().getAddress());
        personsDTO.setCity(persons.getCity());
        personsDTO.setZip(persons.getZip());
        personsDTO.setPhone(persons.getPhone());
        personsDTO.setEmail(persons.getEmail());
        personsDTOmail.setEmail(persons.getEmail());
        personsList.add(persons);
        personsDTOList.add(personsDTO);

        when(personsM.dtoToModel(personsDTO)).thenReturn(persons);
    }

    @Test
    void createPerson() {
        when(personsS.postPerson(persons)).thenReturn(persons);
        ResponseEntity<Void> responseEntity = personsRest.createPerson(personsDTO);
        assertEquals("201 CREATED", responseEntity.getStatusCode().toString());
        assertEquals("[Location:\"http://localhost/Jean/Bobine\"]", responseEntity.getHeaders().toString());
    }

    @Test
    void editPerson() {
        ResponseEntity<Void> responseEntity = personsRest.editPerson(persons.getFirstName(), persons.getLastName(), personsDTO);
        verify(personsS, times(1)).editPerson(persons.getFirstName(), persons.getLastName(), persons);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
    }

    @Test
    void removePerson() {
        ResponseEntity<Void> responseEntity = personsRest.removePerson(persons.getFirstName(), persons.getLastName());
        verify(personsS, times(1)).removePerson(persons.getFirstName(), persons.getLastName());
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
    }

    @Test
    void getPerson() {
        when(personsS.getPersonName(persons.getFirstName(), persons.getLastName())).thenReturn(persons);
        when(personsM.modelToDto(persons)).thenReturn(personsDTO);
        PersonsDTO personsDto = personsRest.getPerson(persons.getFirstName(), persons.getLastName());
        assertEquals(personsDTO.getFirstName(), personsDto.getFirstName());
        assertEquals(personsDTO.getLastName(), personsDto.getLastName());
        assertEquals(personsDTO.getAddress(), personsDto.getAddress());
        assertEquals(personsDTO.getCity(), personsDto.getCity());
        assertEquals(personsDTO.getZip(), personsDto.getZip());
        assertEquals(personsDTO.getPhone(), personsDto.getPhone());
        assertEquals(personsDTO.getEmail(), personsDto.getEmail());
    }

    @Test
    void getPersons() {
        when(personsS.getPersons()).thenReturn(personsList);
        when(personsM.modelToDto(personsList)).thenReturn(personsDTOList);
        List<PersonsDTO> personsDtoList = personsRest.getPersons();
        assertEquals(1, personsDtoList.size());
    }

    @Test
    void getCommunityEmail() {
        personsDTOList.clear();
        personsDTOList.add(personsDTOmail);
        when(personsS.getPersonsCity(personsDTO.getCity())).thenReturn(personsList);
        when(personsS.personsToPersonsdtoEmail(personsList)).thenReturn(personsDTOList);
        List<PersonsDTO> personsDtoList = personsRest.getCommunityEmail(persons.getCity());
        assertEquals(personsDTOmail.getEmail(), personsDtoList.get(0).getEmail());
    }
}