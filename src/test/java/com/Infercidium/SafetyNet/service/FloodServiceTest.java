package com.Infercidium.SafetyNet.service;

import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.Firestations;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.service.FirestationsService;
import com.infercidium.safetynet.service.FloodService;
import com.infercidium.safetynet.service.MedicalRecordsService;
import com.infercidium.safetynet.service.PersonsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {FloodService.class})
class FloodServiceTest {

    @MockBean
    private PersonsService personsS;
    @MockBean
    private MedicalRecordsService medicalRecordsS;
    @MockBean
    private FirestationsService firestationsS;
    @Autowired
    private FloodService floodService;

    String addressString = "1 rue du testing";
    Address address = new Address(addressString);
    Set<Address> addressSet = Collections.singleton(address);

    Firestations firestations = new Firestations();

    Persons persons = new Persons("Jean", "Bobine", address, "Testy", 12345, "456-789-1011", "jbob@email.com");
    List<Persons> personsList = new ArrayList<>();

    LocalDate localDate = LocalDate.of(1990, 1, 1);
    MedicalRecords medicalRecords = new MedicalRecords(localDate, new HashSet<>(), new HashSet<>(), persons);
    List<MedicalRecords> medicalRecordsList = new ArrayList<>();

    PersonsAndMedicalRecordsDTO personsAndMedicalRecordsDTO = new PersonsAndMedicalRecordsDTO();
    List<PersonsAndMedicalRecordsDTO> personsAndMedicalRecordsDTOList = new ArrayList<>();

    @BeforeEach
    private void setUpPerTest() {
        firestations.setAddress(addressSet);
        firestations.setStation(1);
        personsList.add(persons);
        medicalRecordsList.add(medicalRecords);

        personsAndMedicalRecordsDTO.setFirstName(persons.getFirstName());
        personsAndMedicalRecordsDTO.setLastName(persons.getLastName());
        personsAndMedicalRecordsDTO.setMedications(medicalRecords.getMedications());
        personsAndMedicalRecordsDTO.setAllergies(medicalRecords.getAllergies());
        personsAndMedicalRecordsDTO.setAge(medicalRecords.getAge());
        personsAndMedicalRecordsDTO.setPhone(persons.getPhone());
        personsAndMedicalRecordsDTO.setAddress(addressString);
        personsAndMedicalRecordsDTOList.add(personsAndMedicalRecordsDTO);
    }

    @Test
    void getFloodMedicalRecords() {
        when(firestationsS.getFirestationsStation(firestations.getStation())).thenReturn(firestations);
        when(personsS.addressSetToPersonsList(addressSet)).thenReturn(personsList);
        when(medicalRecordsS.personsListToMedicalRecordsList(personsList)).thenReturn(medicalRecordsList);
        List<MedicalRecords> medicalRecordsFireList = floodService.getFloodMedicalRecords(firestations.getStation());
        assertEquals(medicalRecords, medicalRecordsFireList.get(0));
    }

    @Test
    void getFloodResult() {
        Map<String, List<PersonsAndMedicalRecordsDTO>> result = floodService.getFloodResult(personsAndMedicalRecordsDTOList);
        assertTrue(result.containsKey(addressString));
        assertEquals(personsAndMedicalRecordsDTOList, result.get(addressString));
    }
}