package com.Infercidium.SafetyNet.rest;

import com.infercidium.safetynet.dto.PersonsDTO;
import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.rest.PhoneAlertRest;
import com.infercidium.safetynet.service.PhoneAlertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {PhoneAlertRest.class})
class PhoneAlertRestTest {

    @MockBean
    private PhoneAlertService phoneAlertS;
    @Autowired
    private PhoneAlertRest phoneAlertRest;

    String addressString = "1 rue du testing";
    Address address = new Address(addressString);

    Persons persons = new Persons("Jean", "Bobine", address, "Testy", 12345, "456-789-1011", "jbob@email.com");

    PersonsDTO personsDTO = new PersonsDTO();
    List<PersonsDTO> personsDTOList = new ArrayList<>();

    @BeforeEach
    private void setUpPerTest() throws NoSuchMethodException {
        personsDTO.setPhone(persons.getPhone());
        personsDTOList.add(personsDTO);
    }

    @Test
    void getPhoneAlert() {
        when(phoneAlertS.getPersonsPhone(1)).thenReturn(personsDTOList);
        List<PersonsDTO> result = phoneAlertRest.getPhoneAlert(1);
        assertTrue(result.contains(personsDTO));

    }
}