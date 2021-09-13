package com.Infercidium.SafetyNet.rest;

import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.mapper.FireAndFloodMapper;
import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.rest.FloodRest;
import com.infercidium.safetynet.service.FloodService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {FloodRest.class})
class FloodRestTest {

    @MockBean
    private FloodService floodS;
    @MockBean
    private FireAndFloodMapper floodM;
    @Autowired
    private FloodRest floodRest;

    String addressString = "1 rue du testing";
    Address address = new Address(addressString);

    Persons persons = new Persons("Jean", "Bobine", address, "Testy", 12345, "456-789-1011", "jbob@email.com");

    LocalDate localDate = LocalDate.of(1990, 1, 1);
    MedicalRecords medicalRecords = new MedicalRecords(localDate, new HashSet<>(), new HashSet<>(), persons);
    List<MedicalRecords> medicalRecordsList = new ArrayList<>();

    PersonsAndMedicalRecordsDTO personsAndMedicalRecordsDTO = new PersonsAndMedicalRecordsDTO();
    List<PersonsAndMedicalRecordsDTO> personsAndMedicalRecordsDTOList = new ArrayList<>();

    Map<String, List<PersonsAndMedicalRecordsDTO>> floodMap = new HashMap<>();

    @BeforeEach
    private void setUpPerTest() {
        medicalRecordsList.add(medicalRecords);

        personsAndMedicalRecordsDTO.setFirstName(persons.getFirstName());
        personsAndMedicalRecordsDTO.setLastName(persons.getLastName());
        personsAndMedicalRecordsDTO.setMedications(medicalRecords.getMedications());
        personsAndMedicalRecordsDTO.setAllergies(medicalRecords.getAllergies());
        personsAndMedicalRecordsDTO.setAge(medicalRecords.getAge());
        personsAndMedicalRecordsDTO.setPhone(persons.getPhone());
        personsAndMedicalRecordsDTOList.add(personsAndMedicalRecordsDTO);

        floodMap.put(persons.getAddress().getAddress(), personsAndMedicalRecordsDTOList);
    }

    @Test
    void getFlood() {
        when(floodS.getFloodMedicalRecords(1)).thenReturn(medicalRecordsList);
        when(floodM.modelToDto(medicalRecordsList)).thenReturn(personsAndMedicalRecordsDTOList);
        when(floodS.getFloodResult(personsAndMedicalRecordsDTOList)).thenReturn(floodMap);
        Map<String, List<PersonsAndMedicalRecordsDTO>> result = floodRest.getFlood(1);
        assertEquals(result.get(persons.getAddress().getAddress()), personsAndMedicalRecordsDTOList);
        assertTrue(result.containsKey(persons.getAddress().getAddress()));
    }
}
