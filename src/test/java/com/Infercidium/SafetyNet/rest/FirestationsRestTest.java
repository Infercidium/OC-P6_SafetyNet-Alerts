package com.Infercidium.SafetyNet.rest;

import com.infercidium.safetynet.dto.FirestationsAddressDTO;
import com.infercidium.safetynet.dto.FirestationsDTO;
import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.dto.PersonsDTO;
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
import org.springframework.test.context.event.annotation.PrepareTestInstance;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {FirestationsRest.class})
class FirestationsRestTest {

    @MockBean
    private FirestationsService firestationsS;
    @MockBean
    private FirestationsMapper firestationsM;
    @Autowired
    private FirestationsRest firestationsRest;

    String addressString = "1 rue du testing";
    Address address = new Address(addressString);
    Set<Address> addressSet = new HashSet<>();
    Set<String> addressStringSet = new HashSet<>();

    FirestationsAddressDTO firestationsAddressDTO = new FirestationsAddressDTO();
    FirestationsDTO firestationsDTO = new FirestationsDTO();
    List<FirestationsDTO> firestationsDTOList = new ArrayList<>();

    Firestations firestations = new Firestations(addressSet, 1);
    List<Firestations> firestationsList = new ArrayList<>();

    Persons persons = new Persons("Jean", "Bobine", address, "Testy", 12345, "456-789-1011", "jbob@email.com");
    List<Persons> personsList = new ArrayList<>();

    LocalDate localDate = LocalDate.of(1990, 1, 1);
    MedicalRecords medicalRecords = new MedicalRecords(localDate, new HashSet<>(), new HashSet<>(), persons);
    List<MedicalRecords> medicalRecordsList = new ArrayList<>();

    PersonsAndMedicalRecordsDTO personsAndMedicalRecordsDTO = new PersonsAndMedicalRecordsDTO();
    List<PersonsAndMedicalRecordsDTO> personsAndMedicalRecordsDTOList = new ArrayList<>();

    PersonsDTO stationNumberDTO = new PersonsDTO();
    List<PersonsDTO> stationNumberDTOList = new ArrayList<>();

    @BeforeEach
    private void setUpPerTest() {
        addressSet.add(address);
        addressStringSet.add(addressString);
        firestations.setAddress(addressSet);
        personsList.add(persons);
        medicalRecordsList.add(medicalRecords);
        firestationsList.add(firestations);

        firestationsAddressDTO.setStation(1);
        firestationsAddressDTO.setAddress(addressString);

        firestationsDTO.setStation(1);
        firestationsDTO.setAddress(addressStringSet);
        firestationsDTOList.add(firestationsDTO);

        stationNumberDTO.setFirstName(persons.getFirstName());
        stationNumberDTO.setLastName(persons.getLastName());
        stationNumberDTO.setAddress(persons.getAddress().getAddress());
        stationNumberDTO.setPhone(persons.getPhone());
        stationNumberDTOList.add(stationNumberDTO);

        personsAndMedicalRecordsDTO.setFirstName(persons.getFirstName());
        personsAndMedicalRecordsDTO.setLastName(persons.getLastName());
        personsAndMedicalRecordsDTO.setMedications(medicalRecords.getMedications());
        personsAndMedicalRecordsDTO.setAllergies(medicalRecords.getAllergies());
        personsAndMedicalRecordsDTO.setAge(medicalRecords.getAge());
        personsAndMedicalRecordsDTO.setPhone(persons.getPhone());
        personsAndMedicalRecordsDTOList.add(personsAndMedicalRecordsDTO);

        when(firestationsM.dtoToModel(any(FirestationsDTO.class))).thenReturn(firestations);
        when(firestationsM.modelToDto(firestations)).thenReturn(firestationsDTO);
        when(firestationsM.modelToDto(firestationsList)).thenReturn(Collections.singletonList(firestationsDTO));

        when(firestationsS.getFirestationsStation(1)).thenReturn(firestations);
        when(firestationsS.getFirestationsAddress(addressString)).thenReturn(firestationsList);
    }

    @Test
    void createStationMap() throws SQLIntegrityConstraintViolationException {
        when(firestationsS.createMapage(address, firestations)).thenReturn(firestations);
        ResponseEntity<Void> responseEntity = firestationsRest.createStationMap(firestationsAddressDTO);
        assertEquals("201 CREATED", responseEntity.getStatusCode().toString());
        assertEquals("[Location:\"http://localhost/%5BAddress%7B%20id%20=%20null,%20address%20=%20'1%20rue%20du%20testing'%7D%5D\"]", responseEntity.getHeaders().toString());
    }

    @Test
    void editStationMap() throws SQLIntegrityConstraintViolationException {
        ResponseEntity<Void> responseEntity = firestationsRest.editStationMap(firestationsAddressDTO.getAddress(), firestationsAddressDTO.getStation(), firestationsAddressDTO);
        //verify(firestationsS, times(1)).editFirestation(firestationsAddressDTO.getAddress(), firestationsAddressDTO.getStation(), firestations);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
    }


    @Test
    void removeStation() {
        ResponseEntity<Void> responseEntity = firestationsRest.removeStation(1);
        verify(firestationsS, times(1)).removeStation(1);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
    }

    @Test
    void removeAddress() {
        ResponseEntity<Void> responseEntity = firestationsRest.removeAddress(firestationsAddressDTO.getAddress(), 1);
        verify(firestationsS, times(1)).removeAddress(firestationsAddressDTO.getAddress(), 1);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
    }

    @Test
    void removeAddressAll() {
        ResponseEntity<Void> responseEntity = firestationsRest.removeAddress(firestationsAddressDTO.getAddress(), 0);
        verify(firestationsS, times(1)).removeAddress(firestationsAddressDTO.getAddress());
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
    }

    @Test
    void getAddress() {
        List<FirestationsDTO> firestationsDto = firestationsRest.getAddress(addressString);
        assertEquals(1, firestationsDto.get(0).getStation());
        assertTrue(firestationsDto.get(0).getAddress().contains(addressString));
    }

    @Test
    void getStation() {
        FirestationsDTO firestationsDto = firestationsRest.getStation(1);
        assertEquals(1, firestationsDto.getStation());
    }
}
