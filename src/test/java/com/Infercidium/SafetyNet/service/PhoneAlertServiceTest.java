package com.Infercidium.SafetyNet.service;

import com.infercidium.safetynet.dto.PersonsDTO;
import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.Firestations;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.service.FirestationsI;
import com.infercidium.safetynet.service.PersonsI;
import com.infercidium.safetynet.service.PhoneAlertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {PhoneAlertService.class})
class PhoneAlertServiceTest {

    @MockBean
    private PersonsI personsS;
    @MockBean
    private FirestationsI firestationsService;
    @Autowired
    private PhoneAlertService phoneAlertService;

    String addressString = "1 rue du testing";
    Address address = new Address(addressString);
    Set<Address> addressSet = Collections.singleton(address);

    Firestations firestations = new Firestations();

    Persons persons = new Persons("Jean", "Bobine", address, "Testy", 12345, "456-789-1011", "jbob@email.com");
    List<Persons> personsList = new ArrayList<>();

    PersonsDTO personsDTO = new PersonsDTO();
    List<PersonsDTO> personsDTOList = new ArrayList<>();

    @BeforeEach
    private void setUpPerTest() {
        firestations.setAddress(addressSet);
        firestations.setStation(1);
        personsList.add(persons);
    }

    @Test
    void getPersonsPhone() {
        when(firestationsService.getFirestationsStation(firestations.getStation())).thenReturn(firestations);
        when(personsS.addressSetToPersonsList(addressSet)).thenReturn(personsList);
        personsDTO.setPhone(persons.getPhone());
        personsDTOList.add(personsDTO);
        List<PersonsDTO> personsDto = phoneAlertService.getPersonsPhone(firestations.getStation());
        assertEquals(persons.getPhone(), personsDto.get(0).getPhone());
    }
}