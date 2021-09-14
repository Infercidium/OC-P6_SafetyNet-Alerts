package com.Infercidium.SafetyNet.service;

import com.infercidium.safetynet.dto.FirestationsDTO;
import com.infercidium.safetynet.dto.MedicalRecordsDTO;
import com.infercidium.safetynet.dto.PersonsDTO;
import com.infercidium.safetynet.mapper.FirestationsMapper;
import com.infercidium.safetynet.mapper.MedicalRecordsMapper;
import com.infercidium.safetynet.mapper.PersonsMapper;
import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.Allergies;
import com.infercidium.safetynet.model.Firestations;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Medications;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.service.DataBaseInitService;
import com.infercidium.safetynet.service.FirestationsI;
import com.infercidium.safetynet.service.PersonsI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {DataBaseInitService.class})
class DataBaseInitServiceTest {

    @MockBean
    private PersonsMapper personsM;
    @MockBean
    private PersonsI personsS;
    @MockBean
    private FirestationsMapper firestationsM;
    @MockBean
    private FirestationsI firestationsS;
    @MockBean
    private MedicalRecordsMapper medicalRecordsM;
    @Autowired
    private DataBaseInitService dataBaseInitService;

    String data = "{    \"persons\": [        { \"firstName\":\"John\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6512\", \"email\":\"jaboyd@email.com\" }\t],     \"firestations\": [\t    { \"address\":\"1509 Culver St\", \"station\":\"3\" }        ],    \"medicalrecords\": [        { \"firstName\":\"John\", \"lastName\":\"Boyd\", \"birthdate\":\"03/06/1984\", \"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"], \"allergies\":[\"nillacilan\"] }        ]}";
    Map<String, Object> dataMap = new HashMap<>();

    PersonsDTO personsDTO = new PersonsDTO();
    Persons persons = new Persons("John", "Boyd", new Address("1509 Culver St"), "Culver", 97451, "841-874-6512", "jaboyd@email.com");

    FirestationsDTO firestationsDTO = new FirestationsDTO();
    Firestations firestations = new Firestations(Collections.singleton(new Address("1509 Culver St")), 3);

    MedicalRecordsDTO medicalRecordsDTO = new MedicalRecordsDTO();
    Set<Allergies> allergiesSet = new HashSet<>();
    Set<Medications> medicationsSet = new HashSet<>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    MedicalRecords medicalRecords = new MedicalRecords(LocalDate.of(1984, 3, 6), medicationsSet, allergiesSet, persons);

    @BeforeEach
    private void setUpPerTest() {
        dataMap = dataBaseInitService.deserializeStringToMap(data);
        personsDTO.setFirstName("John");
        personsDTO.setLastName("Boyd");
        personsDTO.setAddress("1509 Culver St");
        personsDTO.setCity("Culver");
        personsDTO.setZip(97451);
        personsDTO.setPhone("841-874-6512");
        personsDTO.setEmail("jaboyd@email.com");

        firestationsDTO.setStation(3);
        firestationsDTO.setAddress(Collections.singleton("1509 Culver St"));

        allergiesSet.add(new Allergies("nillacilan"));
        medicationsSet.add(new Medications("aznol:350mg"));
        medicationsSet.add(new Medications("hydrapermazol:100mg"));
        medicalRecords.setMedications(medicationsSet);
        medicalRecords.setAllergies(allergiesSet);

        medicalRecordsDTO.setFirstName("John");
        medicalRecordsDTO.setLastName("Boyd");
        medicalRecordsDTO.setBirthdate(LocalDate.of(1984, 3, 6));
        medicalRecordsDTO.setMedications(medicationsSet);
        medicalRecordsDTO.setAllergies(allergiesSet);
    }

    @Test
    void convertFileToString() {
        String result = dataBaseInitService.convertFileToString("src/test/resources/dataTest.json");
        assertEquals(data, result);
    }

    @Test
    void convertMaptoList() {
        List<Map<String, String>> result = dataBaseInitService.convertMaptoList(dataMap, "persons");
        assertEquals(personsDTO.getFirstName(), result.get(0).get("firstName"));
        assertEquals(personsDTO.getLastName(), result.get(0).get("lastName"));
        assertEquals(personsDTO.getPhone(), result.get(0).get("phone"));
        assertEquals(personsDTO.getAddress(), result.get(0).get("address"));
        assertEquals(personsDTO.getCity(), result.get(0).get("city"));
        assertEquals(personsDTO.getZip(), Integer.parseInt(result.get(0).get("zip")));
        assertEquals(personsDTO.getEmail(), result.get(0).get("email"));
    }

    @Test
    void convertMedicalRecordsMaptoList() {
        List<Map<String, Object>> result = dataBaseInitService.convertMedicalRecordsMaptoList(dataMap);
        assertEquals(medicalRecordsDTO.getFirstName(), result.get(0).get("firstName"));
        assertEquals(medicalRecordsDTO.getLastName(), result.get(0).get("lastName"));
        assertEquals(medicalRecordsDTO.getBirthdate().format(formatter), result.get(0).get("birthdate"));
    }

    @Test
    void instanciateListFirestations() throws SQLIntegrityConstraintViolationException {
        List<Map<String, String>> mapList = new ArrayList<>();
        Map firestationsMap = new HashMap();
        firestationsMap.put("station", "3");
        firestationsMap.put("address", "1509 Culver St");
        mapList.add(firestationsMap);
        when(firestationsM.dtoToModel(any(FirestationsDTO.class))).thenReturn(firestations);
        when(firestationsS.mapageCheck("1509 Culver St", 3)).thenReturn(false);
        dataBaseInitService.instanciateListFirestations(mapList);
        verify(firestationsM, times(1)).dtoToModel(any(FirestationsDTO.class));
    }

    @Test
    void instanciateListPersons() {
        when(personsM.dtoToModel(any(PersonsDTO.class))).thenReturn(persons);
        when(personsS.personCheck(personsDTO.getFirstName(), personsDTO.getLastName())).thenReturn(true);
        dataBaseInitService.instanciateListPersons(dataBaseInitService.convertMaptoList(dataMap, "persons"));
        verify(personsM, times(1)).dtoToModel(any(PersonsDTO.class));
    }

    @Test
    void instanciateListMedicalRecords() {
        when(medicalRecordsM.dtoToModel(medicalRecordsDTO)).thenReturn(medicalRecords);
        dataBaseInitService.instanciateListMedicalRecords(dataBaseInitService.convertMedicalRecordsMaptoList(dataMap));
        verify(medicalRecordsM, times(1)).dtoToModel(any(MedicalRecordsDTO.class));
    }

    @Test
    void instanciateListMedications() {
        List<String> medications = new ArrayList<>();
        medications.add("hydrapermazol:100mg");
        medications.add("aznol:350mg");
        Set<Medications> result = dataBaseInitService.instanciateListMedications(medications);
        assertEquals(medicationsSet.size(), result.size());

    }

    @Test
    void instanciateListAllergies() {
        List<String> allergies = new ArrayList<>();
        allergies.add("nillacilan");
        Set<Allergies> result = dataBaseInitService.instanciateListAllergies(allergies);
        assertEquals(allergiesSet.toString(), result.toString());
    }
}