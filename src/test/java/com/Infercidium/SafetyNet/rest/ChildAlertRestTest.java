package com.Infercidium.SafetyNet.rest;

import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.rest.ChildAlertRest;
import com.infercidium.safetynet.service.MedicalRecordsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {ChildAlertRest.class})
class ChildAlertRestTest {
    @MockBean
    private MedicalRecordsService medicalRecordsS;
    @Autowired
    private ChildAlertRest childAlertRest;

    Persons persons = new Persons("Jean", "Bobine", new Address("1 rue du testing"), "Testy", 12345, "456-789-1011", "jbob@email.com");

    LocalDate localDate = LocalDate.of(1990, 1, 1);
    MedicalRecords medicalRecords = new MedicalRecords(localDate, new HashSet<>(), new HashSet<>(), persons);

    PersonsAndMedicalRecordsDTO personsAndMedicalRecordsDTO = new PersonsAndMedicalRecordsDTO();
    List<PersonsAndMedicalRecordsDTO> personsAndMedicalRecordsDTOList = new ArrayList<>();

    Map<String, List<PersonsAndMedicalRecordsDTO>> childAlertResult = new HashMap<>();

    @BeforeEach
    private void setUpPerTest() {
        personsAndMedicalRecordsDTO.setFirstName(persons.getFirstName());
        personsAndMedicalRecordsDTO.setLastName(persons.getLastName());
        personsAndMedicalRecordsDTO.setMedications(medicalRecords.getMedications());
        personsAndMedicalRecordsDTO.setAllergies(medicalRecords.getAllergies());
        personsAndMedicalRecordsDTO.setAge(medicalRecords.getAge());
        personsAndMedicalRecordsDTO.setPhone(persons.getPhone());
        personsAndMedicalRecordsDTOList.add(personsAndMedicalRecordsDTO);
    }
    @Test
    void getChildAlert() {
        when(medicalRecordsS.childAlert(persons.getAddress().getAddress())).thenReturn(childAlertResult);
        Map<String, List<PersonsAndMedicalRecordsDTO>> result = childAlertRest.getChildAlert(persons.getAddress().getAddress());
        assertTrue(result.isEmpty());
    }
}