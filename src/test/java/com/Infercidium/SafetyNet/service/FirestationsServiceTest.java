package com.Infercidium.SafetyNet.service;

import com.infercidium.safetynet.dto.FirestationsDTO;
import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.dto.PersonsDTO;
import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.Firestations;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.repository.FirestationsRepository;
import com.infercidium.safetynet.service.AddressI;
import com.infercidium.safetynet.service.FirestationsService;
import com.infercidium.safetynet.service.MedicalRecordsI;
import com.infercidium.safetynet.service.PersonsI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.internal.stubbing.answers.CallsRealMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {FirestationsService.class})
class FirestationsServiceTest {


    @MockBean
    private FirestationsRepository firestationsR;
    @MockBean
    private AddressI addressS;
    @MockBean
    private PersonsI personsS;
    @MockBean
    private MedicalRecordsI medicalRecordsS;
    @Autowired
    private FirestationsService firestationsService;

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

    PersonsDTO personsDTO = new PersonsDTO();
    List<PersonsDTO> personsDTOList = new ArrayList<>();

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

        when(firestationsR.save(firestations)).thenReturn(firestations);
        when(firestationsR.findByAddressAddressIgnoreCase(addressString)).thenReturn(firestationsList);
        when(firestationsR.findByStation(firestationsDTO.getStation())).thenReturn(firestations);
        when(firestationsR.findByAddressAddressIgnoreCaseAndStation(addressString,firestationsDTO.getStation())).thenReturn(firestations);
        when(medicalRecordsS.getMedicalRecordName(medicalRecords.getFirstName(), medicalRecords.getLastName())).thenReturn(medicalRecords);
    }


    /*@Test //TODO à faire
    void createMapage() throws SQLIntegrityConstraintViolationException {
        when(firestationsR.findByAddressAddressIgnoreCaseAndStation(addressString,firestationsDTO.getStation())).thenReturn(null);
        when(addressS.checkAddress(address)).thenReturn(address);
        doAnswer(new CallsRealMethods()).when(firestations).addAddress(address);
        Firestations postFirestations = firestationsService.createMapage(address, firestations);
        assertEquals(firestations, postFirestations);
    }*/

    /*@Test //TODO impossible
    void editFirestation() throws SQLIntegrityConstraintViolationException {
        firestations.setId(1L);
        firestationsService.editFirestation(addressString, firestationsDTO.getStation(),  firestations);
        verify(firestationsService, times(2)).mapageCheck(addressString, firestationsDTO.getStation());
    }*/

    @Test
    void removeAddress() {
        firestationsService.removeAddress(addressString, firestationsDTO.getStation());
        verify(firestationsR, times(1)).save(firestations);
    }

    @Test
    void removeStation() {
        firestationsService.removeStation(firestationsDTO.getStation());
        verify(firestationsR, times(1)).delete(firestations);
    }

    @Test
    void testRemoveAddress() {
        when(firestationsService.getFirestationsAddress(addressString)).thenReturn(firestationsList);
        firestationsService.removeAddress(addressString);
    }

    @Test
    void getFirestationsAddress() {
        List<Firestations> firestation = firestationsService.getFirestationsAddress(addressString);
        assertEquals(firestationsList, firestation);
    }

    @Test
    void getFirestationsAddressAll() {
        when(firestationsR.findAll()).thenReturn(firestationsList);
        List<Firestations> firestation = firestationsService.getFirestationsAddress("null");
        assertEquals(firestationsList, firestation);
    }

    @Test
    void getFirestationsStation() {
        Firestations firestation = firestationsService.getFirestationsStation(firestationsDTO.getStation());
        assertEquals(firestations, firestation);

    }

    @Test
    void mapageCheck() {
        boolean result = firestationsService.mapageCheck(addressString, firestationsDTO.getStation());
        assertTrue(result);
    }

    @Test
    void addressCheck() {
        boolean result = firestationsService.addressCheck(addressString);
        assertTrue(result);
    }

    @Test
    void stationCheck() {
        boolean result = firestationsService.stationCheck(firestationsDTO.getStation());
        assertTrue(result);
    }
}