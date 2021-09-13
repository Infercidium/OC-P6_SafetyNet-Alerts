package com.Infercidium.SafetyNet.service;

import com.infercidium.safetynet.dto.FirestationsDTO;
import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.Firestations;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.service.FireService;
import com.infercidium.safetynet.service.FirestationsService;
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
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {FireService.class})
class FireServiceTest {

    @MockBean
    private PersonsService personsS;
    @MockBean
    private MedicalRecordsService medicalRecordsS;
    @MockBean
    private FirestationsService firestationsS;
    @Autowired
    private FireService fireService;

    String addressString = "1 rue du testing";
    Address address = new Address(addressString);
    Set<Address> addressSet = Collections.singleton(address);
    Set<String> addressStringSet = Collections.singleton(addressString);

    Firestations firestations = new Firestations();
    List<Firestations> firestationsList = new ArrayList<>();

    Persons persons = new Persons("Jean", "Bobine", address, "Testy", 12345, "456-789-1011", "jbob@email.com");
    List<Persons> personsList = new ArrayList<>();

    LocalDate localDate = LocalDate.of(1990, 1, 1);
    MedicalRecords medicalRecords = new MedicalRecords(localDate, new HashSet<>(), new HashSet<>(), persons);
    List<MedicalRecords> medicalRecordsList = new ArrayList<>();

    FirestationsDTO firestationsDTO = new FirestationsDTO();
    List<FirestationsDTO> firestationsDTOList = new ArrayList<>();

    PersonsAndMedicalRecordsDTO personsAndMedicalRecordsDTO = new PersonsAndMedicalRecordsDTO();
    List<PersonsAndMedicalRecordsDTO> personsAndMedicalRecordsDTOList = new ArrayList<>();

    @BeforeEach
    private void setUpPerTest() {
        firestations.setAddress(addressSet);
        firestations.setStation(1);
        firestationsList.add(firestations);
        personsList.add(persons);
        medicalRecordsList.add(medicalRecords);

        firestationsDTO.setStation(1);
        firestationsDTO.setAddress(addressStringSet);
        firestationsDTOList.add(firestationsDTO);

        personsAndMedicalRecordsDTO.setFirstName(persons.getFirstName());
        personsAndMedicalRecordsDTO.setLastName(persons.getLastName());
        personsAndMedicalRecordsDTO.setMedications(medicalRecords.getMedications());
        personsAndMedicalRecordsDTO.setAllergies(medicalRecords.getAllergies());
        personsAndMedicalRecordsDTO.setAge(medicalRecords.getAge());
        personsAndMedicalRecordsDTO.setPhone(persons.getPhone());
        personsAndMedicalRecordsDTOList.add(personsAndMedicalRecordsDTO);
    }

    @Test
    void getFireMedicalRecords() {
        when(personsS.addressSetToPersonsList(addressSet)).thenReturn(personsList);
        when(medicalRecordsS.personsListToMedicalRecordsList(personsList)).thenReturn(medicalRecordsList);
        List<MedicalRecords> medicalRecordsFireList = fireService.getFireMedicalRecords(addressString);
        assertEquals(medicalRecords, medicalRecordsFireList.get(0));
    }

    @Test
    void getFireResult() {
        when(firestationsS.getFirestationsAddress(addressString)).thenReturn(firestationsList);
        Map<String, Object> result = fireService.getFireResult(addressString, personsAndMedicalRecordsDTOList);
        assertEquals("[" + firestationsDTO.getStation() + "]", result.get("Station").toString());
        assertEquals(personsAndMedicalRecordsDTOList, result.get("Resident"));
    }
}