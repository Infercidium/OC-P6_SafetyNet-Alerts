package com.Infercidium.SafetyNet.rest;

import com.infercidium.safetynet.dto.FirestationsDTO;
import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.dto.PersonsDTO;
import com.infercidium.safetynet.dto.StationNumberDTO;
import com.infercidium.safetynet.mapper.FirestationsMapper;
import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.Firestations;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.rest.FirestationsRest;
import com.infercidium.safetynet.service.FirestationsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {FirestationsRest.class})
class FirestationsRestTest {

    @MockBean
    private FirestationsService firestationsS;
    @MockBean
    private FirestationsMapper firestationsM;
    @Autowired
    private FirestationsRest firestationsRest;

    FirestationsDTO firestationsDTO = new FirestationsDTO();
    Firestations firestations = new Firestations(new Address("1 rue du testing"), 1);
    List<Firestations> firestationsList = new ArrayList<>();
    Persons persons = new Persons("Jean", "Bobine", firestations.getAddress(), "Testy", 12345, "456-789-1011", "jbob@email.com");
    List<Persons> personsList = new ArrayList<>();
    StationNumberDTO stationNumberDTO = new StationNumberDTO();
    List<StationNumberDTO> stationNumberDTOList = new ArrayList<>();
    List<FirestationsDTO> firestationsDTOList = new ArrayList<>();
    LocalDate localDate = LocalDate.of(1990, 1, 1);
    MedicalRecords medicalRecords = new MedicalRecords(localDate, new HashSet<>(), new HashSet<>(), persons);
    List<MedicalRecords> medicalRecordsList = new ArrayList<>();
    PersonsAndMedicalRecordsDTO personsAndMedicalRecordsDTO = new PersonsAndMedicalRecordsDTO();
    List<PersonsAndMedicalRecordsDTO> personsAndMedicalRecordsDTOList = new ArrayList<>();

    @BeforeEach
    private void setUpPerTest() {
        firestationsDTO.setStation(1);
        firestationsDTO.setAddress("1 rue du testing");
        firestationsList.add(firestations);
        personsList.add(persons);
        stationNumberDTO.setFirstName(persons.getFirstName());
        stationNumberDTO.setLastName(persons.getLastName());
        stationNumberDTO.setAddress(persons.getAddress().getAddress());
        stationNumberDTO.setPhone(persons.getPhone());
        stationNumberDTOList.add(stationNumberDTO);
        firestationsDTOList.add(firestationsDTO);
        medicalRecordsList.add(medicalRecords);
        personsAndMedicalRecordsDTO.setFirstName(persons.getFirstName());
        personsAndMedicalRecordsDTO.setLastName(persons.getLastName());
        personsAndMedicalRecordsDTO.setMedications(medicalRecords.getMedications());
        personsAndMedicalRecordsDTO.setAllergies(medicalRecords.getAllergies());
        personsAndMedicalRecordsDTO.setAge(medicalRecords.getAge());
        personsAndMedicalRecordsDTO.setPhone(persons.getPhone());
        personsAndMedicalRecordsDTOList.add(personsAndMedicalRecordsDTO);

        when(firestationsM.dtoToModel(firestationsDTO)).thenReturn(firestations);
        when(firestationsM.modelToDto(firestations)).thenReturn(firestationsDTO);
        when(firestationsM.modelToDto(firestationsList)).thenReturn(Collections.singletonList(firestationsDTO));

        when(firestationsS.getFireStationsStation(1)).thenReturn(firestationsList);
        when(firestationsS.getFirestationsAddress(firestationsDTO.getAddress())).thenReturn(firestations);
        when(firestationsS.getFirestationsListToPersonsList(firestationsList)).thenReturn(personsList);
    }

    @Test
    void createStationMap() {
        when(firestationsS.postFirestation(firestations)).thenReturn(firestations);
        ResponseEntity<Void> responseEntity = firestationsRest.createStationMap(firestationsDTO);
        assertEquals("201 CREATED", responseEntity.getStatusCode().toString());
        assertEquals("[Location:\"http://localhost/Address%7B%20id%20=%20null,%20address%20=%20'1%20rue%20du%20testing'%7D\"]", responseEntity.getHeaders().toString());
    }

    @Test
    void editStationMap() {
        ResponseEntity<Void> responseEntity = firestationsRest.editStationMap(firestationsDTO.getAddress(), firestationsDTO);
        verify(firestationsS, times(1)).editFirestation(firestationsDTO.getAddress(), firestations);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
    }

    @Test
    void removeStation() {
        ResponseEntity<Void> responseEntity = firestationsRest.removeStation(1);
        verify(firestationsS, times(1)).removeStationMapping(1);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
    }

    @Test
    void removeAddress() {
        ResponseEntity<Void> responseEntity = firestationsRest.removeAddress(firestationsDTO.getAddress());
        verify(firestationsS, times(1)).removeAddressMapping(firestationsDTO.getAddress());
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
    }

    @Test
    void getAddress() {
        FirestationsDTO firestationsDto = firestationsRest.getAddress(firestationsDTO.getAddress());
        assertEquals(1, firestationsDto.getStation());
        assertEquals("1 rue du testing", firestationsDto.getAddress());
    }

    @Test
    void getStation() {
        List<FirestationsDTO> firestationsDto = firestationsRest.getStation(1);
        assertEquals(1, firestationsDto.size());
    }

    @Test
    void getStationNumber() {
        when(firestationsM.personsModelToStationNumberDTO(personsList)).thenReturn(stationNumberDTOList);
        Map<String, Object> stationNumberMap = new HashMap<>();
        stationNumberMap.put("Resident", stationNumberDTOList);
        stationNumberMap.put("Adult", 1);
        stationNumberMap.put("Child", 0);
        when(firestationsS.getStationNumberCount(stationNumberDTOList)).thenReturn(stationNumberMap);
        Map<String, Object> result = firestationsRest.getStationNumber(1);
        assertEquals(result.get("Resident"), stationNumberDTOList);
        assertEquals(1, result.get("Adult"));
        assertEquals(0, result.get("Child"));
    }

    @Test
    void getPhoneAlert() {
        PersonsDTO personsDTO = new PersonsDTO();
        personsDTO.setPhone(persons.getPhone());
        List<PersonsDTO> personsDTOList = new ArrayList<>();
        personsDTOList.add(personsDTO);
        when(firestationsS.personsToPersonsdtoPhone(personsList)).thenReturn(personsDTOList);
        List<PersonsDTO> result = firestationsRest.getPhoneAlert(1);
        assertTrue(result.contains(personsDTO));
    }


    @Test
    void getFire() {
        when(firestationsS.getFireResidents(firestationsDTO.getAddress())).thenReturn(personsList);
        when(firestationsS.getFireMedicalRecords(personsList)).thenReturn(medicalRecordsList);
        when(firestationsM.personsAndMedicalRecordsModelToPersonsAndMedicalRecordsDTO(medicalRecordsList)).thenReturn(personsAndMedicalRecordsDTOList);
        Map<String, Object> fireMap = new HashMap<>();
        fireMap.put("Station", 1);
        fireMap.put("Resident", personsAndMedicalRecordsDTOList);
        when(firestationsS.getFireResult(1, personsAndMedicalRecordsDTOList)).thenReturn(fireMap);
        Map<String, Object> result = firestationsRest.getFire(persons.getAddress().getAddress());
        assertEquals(result.get("Resident"), personsAndMedicalRecordsDTOList);
        assertEquals(1, result.get("Station"));
    }

    @Test
    void getFlood() {
        Map<String, List<Persons>> personsAddressMap = new HashMap<>();
        personsAddressMap.put(persons.getAddress().getAddress(), personsList);
        when(firestationsS.getFloodResidents(firestationsDTOList)).thenReturn(personsAddressMap);
        Map<String, List<MedicalRecords>> medicalRecordsMap = new HashMap<>();
        medicalRecordsMap.put(persons.getAddress().getAddress(), medicalRecordsList);
        when(firestationsS.getFloodMedicalRecords(personsAddressMap)).thenReturn(medicalRecordsMap);
        Map<String, Object> floodMap = new HashMap<>();
        floodMap.put(persons.getAddress().getAddress(), personsAndMedicalRecordsDTOList);
        when(firestationsM.personsAndMedicalRecordsModelToFloodDTO(medicalRecordsMap)).thenReturn(floodMap);
        Map<String, Object> result = firestationsRest.getFlood(1);
        assertEquals(result.get(persons.getAddress().getAddress()), personsAndMedicalRecordsDTOList);
        assertTrue(result.containsKey(persons.getAddress().getAddress()));
    }
}
