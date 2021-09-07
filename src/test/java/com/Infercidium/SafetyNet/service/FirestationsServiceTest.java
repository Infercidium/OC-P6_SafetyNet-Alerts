package com.Infercidium.SafetyNet.service;

import com.infercidium.safetynet.dto.FirestationsDTO;
import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.dto.PersonsDTO;
import com.infercidium.safetynet.dto.StationNumberDTO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {FirestationsService.class}) //todo réactiver
class FirestationsServiceTest {

   /* @MockBean
    private FirestationsRepository firestationsR;
    @MockBean
    private AddressI addressS;
    @MockBean
    private PersonsI personsS;
    @MockBean
    private MedicalRecordsI medicalRecordsS;
    @Autowired
    private FirestationsService firestationsService;

    Firestations firestations = new Firestations(new Address("1 rue du testing"), 1);
    List<Firestations> firestationsList = new ArrayList<>();

    Persons persons = new Persons("Jean", "Bobine", firestations.getAddress(), "Testy", 12345, "456-789-1011", "jbob@email.com");
    List<Persons> personsList = new ArrayList<>();

    LocalDate localDate = LocalDate.of(1990, 1, 1);
    MedicalRecords medicalRecords = new MedicalRecords(localDate, new HashSet<>(), new HashSet<>(), persons);
    List<MedicalRecords> medicalRecordsList = new ArrayList<>();

    FirestationsDTO firestationsDTO = new FirestationsDTO();
    List<FirestationsDTO> firestationsDTOList = new ArrayList<>();

    PersonsDTO personsDTO = new PersonsDTO();
    List<PersonsDTO> personsDTOList = new ArrayList<>();

    StationNumberDTO stationNumberDTO = new StationNumberDTO();
    List<StationNumberDTO> stationNumberDTOList = new ArrayList<>();

    PersonsAndMedicalRecordsDTO personsAndMedicalRecordsDTO = new PersonsAndMedicalRecordsDTO();
    List<PersonsAndMedicalRecordsDTO> personsAndMedicalRecordsDTOList = new ArrayList<>();

    @BeforeEach
    private void setUpPerTest() {
        firestationsList.add(firestations);
        personsList.add(persons);
        medicalRecordsList.add(medicalRecords);

        firestationsDTO.setStation(1);
        firestationsDTO.setAddress("1 rue du testing");
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

        when(firestationsR.save(firestations)).thenReturn(firestations);
        when(firestationsR.findByAddressAddressIgnoreCase(firestationsDTO.getAddress())).thenReturn(firestationsList);
        when(firestationsR.findByStation(firestationsDTO.getStation())).thenReturn(firestationsList);
        when(firestationsR.findByAddressAddressIgnoreCaseAndStation(firestationsDTO.getAddress(),firestationsDTO.getStation())).thenReturn(firestations);
        when(personsS.getPersonsAddress(firestationsDTO.getAddress())).thenReturn(personsList);
        when(medicalRecordsS.getMedicalRecordName(medicalRecords.getFirstName(), medicalRecords.getLastName())).thenReturn(medicalRecords);
    }

    @Test
    void postFirestation() throws SQLIntegrityConstraintViolationException {
        when(firestationsR.findByAddressAddressIgnoreCaseAndStation(firestationsDTO.getAddress(),firestationsDTO.getStation())).thenReturn(null);
        Address address = new Address(firestationsDTO.getAddress());
        when(addressS.checkAddress(firestations.getAddress())).thenReturn(address);
        Firestations postFirestations = firestationsService.postFirestation(firestations);
        assertEquals(firestations, postFirestations);
    }

    @Test
    void editFirestation() {
        firestations.setId(1L);
        firestationsService.editFirestation(firestationsDTO.getAddress(), firestationsDTO.getStation(),  firestations);
        verify(firestationsR, times(1)).save(firestations);
    }

    @Test
    void removeStationMapping() {
        firestationsService.removeStationMapping(firestationsDTO.getStation());
        verify(firestationsR, times(1)).deleteAll(firestationsList);
    }

    @Test
    void removeAddressMapping() {
        firestationsService.removeAddressMapping(firestationsDTO.getAddress());
        verify(firestationsR, times(1)).deleteAll(firestationsList);
    }

    @Test
    void getFirestationsAddress() {
        List<Firestations> firestation = firestationsService.getFirestationsAddress(firestationsDTO.getAddress());
        assertEquals(firestationsList, firestation);
    }

    @Test
    void getFireStationsStation() {
        when(firestationsR.findAll()).thenReturn(firestationsList);
        List<Firestations> firestationsGetList = firestationsService.getFireStationsStation(0);
        assertEquals(firestationsList.get(0), firestationsGetList.get(0));
    }

    @Test
    void getFirestationsListToPersonsList() {
        List<Persons> personsGetList = firestationsService.getFirestationsListToPersonsList(firestationsList);
        assertEquals(personsList.get(0), personsGetList.get(0));
    }

    @Test
    void getStationNumberCount() {
        when(medicalRecordsS.checkMajority(stationNumberDTO.getFirstName(), stationNumberDTO.getLastName())).thenReturn(true);
        Map<String, Object> result = firestationsService.getStationNumberCount(stationNumberDTOList);
        assertEquals(result.get("Resident"), stationNumberDTOList);
        assertEquals(1, result.get("Adult"));
        assertEquals(0, result.get("Child"));
    }

    @Test
    void personsToPersonsdtoPhone() {
        personsDTO.setPhone(persons.getPhone());
        personsDTOList.add(personsDTO);
        List<PersonsDTO> personsDto = firestationsService.personsToPersonsdtoPhone(personsList);
        assertEquals(persons.getPhone(), personsDto.get(0).getPhone());
    }

    @Test
    void getFireResidents() {
        List<Persons> personsFireList = firestationsService.getFireResidents(firestationsDTO.getAddress());
        assertEquals(persons, personsFireList.get(0));
    }

    @Test
    void getFireMedicalRecords() {
        List<MedicalRecords> medicalRecordsFireList = firestationsService.getFireMedicalRecords(personsList);
        assertEquals(medicalRecords, medicalRecordsFireList.get(0));
    }

    @Test
    void getFireResult() {
        Map<String, Object> result = firestationsService.getFireResult(Collections.singletonList(firestationsDTO.getStation()), personsAndMedicalRecordsDTOList);
        System.out.println(result);
        assertEquals("[" + firestationsDTO.getStation() + "]", result.get("Station").toString());
        assertEquals(personsAndMedicalRecordsDTOList, result.get("Resident"));
    }

    @Test
    void getFloodResidents() {
        Map<String, List<Persons>> result = firestationsService.getFloodResidents(firestationsDTOList);
        assertTrue(result.containsKey(firestationsDTO.getAddress()));
        assertEquals(personsList, result.get(firestationsDTO.getAddress()));
    }

    @Test
    void getFloodMedicalRecords() {
        Map<String, List<Persons>> personsMap = firestationsService.getFloodResidents(firestationsDTOList);
        Map<String, List<MedicalRecords>> result = firestationsService.getFloodMedicalRecords(personsMap);
        assertTrue(result.containsKey(firestationsDTO.getAddress()));
        assertEquals(medicalRecordsList, result.get(firestationsDTO.getAddress()));
    }*/
}