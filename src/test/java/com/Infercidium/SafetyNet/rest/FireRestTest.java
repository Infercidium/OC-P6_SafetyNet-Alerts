package com.Infercidium.SafetyNet.rest;

import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.mapper.FireAndFloodMapper;
import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.rest.FireRest;
import com.infercidium.safetynet.service.FireService;
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
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {FireRest.class})
class FireRestTest {

    @MockBean
    private FireService fireS;
    @MockBean
    private FireAndFloodMapper fireM;
    @Autowired
    private FireRest fireRest;

    String addressString = "1 rue du testing";
    Address address = new Address(addressString);

    Persons persons = new Persons("Jean", "Bobine", address, "Testy", 12345, "456-789-1011", "jbob@email.com");
    List<Persons> personsList = new ArrayList<>();

    LocalDate localDate = LocalDate.of(1990, 1, 1);
    MedicalRecords medicalRecords = new MedicalRecords(localDate, new HashSet<>(), new HashSet<>(), persons);
    List<MedicalRecords> medicalRecordsList = new ArrayList<>();

    PersonsAndMedicalRecordsDTO personsAndMedicalRecordsDTO = new PersonsAndMedicalRecordsDTO();
    List<PersonsAndMedicalRecordsDTO> personsAndMedicalRecordsDTOList = new ArrayList<>();

    Map<String, Object> fireMap = new HashMap<>();

    @BeforeEach
    private void setUpPerTest() {
        personsList.add(persons);
        medicalRecordsList.add(medicalRecords);

        personsAndMedicalRecordsDTO.setFirstName(persons.getFirstName());
        personsAndMedicalRecordsDTO.setLastName(persons.getLastName());
        personsAndMedicalRecordsDTO.setMedications(medicalRecords.getMedications());
        personsAndMedicalRecordsDTO.setAllergies(medicalRecords.getAllergies());
        personsAndMedicalRecordsDTO.setAge(medicalRecords.getAge());
        personsAndMedicalRecordsDTO.setPhone(persons.getPhone());
        personsAndMedicalRecordsDTOList.add(personsAndMedicalRecordsDTO);

        fireMap.put("Station", 1);
        fireMap.put("Resident", personsAndMedicalRecordsDTOList);
    }

    @Test
    void getFire() {
        when(fireS.getFireMedicalRecords(addressString)).thenReturn(medicalRecordsList);
        when(fireM.modelToDto(medicalRecordsList)).thenReturn(personsAndMedicalRecordsDTOList);
        when(fireS.getFireResult(addressString, personsAndMedicalRecordsDTOList)).thenReturn(fireMap);

        Map<String, Object> result = fireRest.getFire(persons.getAddress().getAddress());
        assertEquals(result.get("Resident"), personsAndMedicalRecordsDTOList);
        assertEquals(1, result.get("Station"));
    }
}