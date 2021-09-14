package com.Infercidium.SafetyNet.service;

import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.Firestations;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.service.FirestationsService;
import com.infercidium.safetynet.service.MedicalRecordsService;
import com.infercidium.safetynet.service.PersonsService;
import com.infercidium.safetynet.service.StationNumberService;
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

@SpringBootTest(classes = {StationNumberService.class})
class StationNumberServiceTest {

    @MockBean
    private PersonsService personsS;
    @MockBean
    private FirestationsService firestationsS;
    @MockBean
    private MedicalRecordsService medicalRecordsS;
    @Autowired
    private StationNumberService stationNumberService;

    String addressString = "1 rue du testing";
    Address address = new Address(addressString);
    Set<Address> addressSet = Collections.singleton(address);

    Firestations firestations = new Firestations();

    Persons persons = new Persons("Jean", "Bobine", address, "Testy", 12345, "456-789-1011", "jbob@email.com");
    List<Persons> personsList = new ArrayList<>();

    LocalDate localDate = LocalDate.of(1990, 1, 1);
    MedicalRecords medicalRecords = new MedicalRecords(localDate, new HashSet<>(), new HashSet<>(), persons);

    @BeforeEach
    private void setUpPerTest() {
        firestations.setAddress(addressSet);
        firestations.setStation(1);
        personsList.add(persons);

        when(firestationsS.getFirestationsStation(1)).thenReturn(firestations);
        when(personsS.addressSetToPersonsList(firestations.getAddress())).thenReturn(personsList);
        when(medicalRecordsS.getMedicalRecordName(medicalRecords.getFirstName(), medicalRecords.getLastName())).thenReturn(medicalRecords);
    }

    @Test
    void stationNumber() {
        Map<String, Object> result = stationNumberService.stationNumber(1);
        assertEquals("1", result.get("Adult").toString());
        assertEquals("0", result.get("Child").toString());
    }
}