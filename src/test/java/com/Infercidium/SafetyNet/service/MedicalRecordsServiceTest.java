package com.Infercidium.SafetyNet.service;

import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.repository.MedicalrecordsRepository;
import com.infercidium.safetynet.service.AllergiesI;
import com.infercidium.safetynet.service.MedicalRecordsService;
import com.infercidium.safetynet.service.MedicationsI;
import com.infercidium.safetynet.service.PersonsI;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {MedicalRecordsService.class})
class MedicalRecordsServiceTest {

    @MockBean
    private PersonsI personsS;
    @MockBean
    private MedicalrecordsRepository medicalRecordsR;
    @MockBean
    private MedicationsI medicationsS;
    @MockBean
    private AllergiesI allergiesS;
    @Autowired
    private MedicalRecordsService medicalRecordsService;

    String addressString = "1 rue du testing";
    Address address = new Address(addressString);

    Persons persons = new Persons("Jean", "Bobine", new Address("1 rue du testing"), "Testy", 12345, "456-789-1011", "jbob@email.com");
    List<Persons> personsList = new ArrayList<>();

    LocalDate localDate = LocalDate.of(1990, 1, 1);
    MedicalRecords medicalRecords = new MedicalRecords(localDate, new HashSet<>(), new HashSet<>(), persons);
    List<MedicalRecords> medicalRecordsList = new ArrayList<>();

    PersonsAndMedicalRecordsDTO personsAndMedicalRecordsDTO = new PersonsAndMedicalRecordsDTO();
    List<PersonsAndMedicalRecordsDTO> personsAndMedicalRecordsDTOList = new ArrayList<>();

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

        when(allergiesS.checkAllergies(medicalRecords.getAllergies())).thenReturn(medicalRecords.getAllergies());
        when(medicationsS.checkMedications(medicalRecords.getMedications())).thenReturn(medicalRecords.getMedications());

        when(medicalRecordsR.save(medicalRecords)).thenReturn(medicalRecords);
        when(medicalRecordsR.findByPersonsFirstNameIgnoreCaseAndPersonsLastNameIgnoreCase(persons.getFirstName(), persons.getLastName())).thenReturn(medicalRecords);

    }

    @Test
    void postMedicalRecords() {
        when(personsS.personCheck(persons.getFirstName(), persons.getLastName())).thenReturn(true);
        when(personsS.getPersonName(persons.getFirstName(), persons.getLastName())).thenReturn(persons);
        MedicalRecords postMedicalRecords = medicalRecordsService.postMedicalRecords(medicalRecords, persons.getFirstName(), persons.getLastName());
        assertEquals(medicalRecords, postMedicalRecords);
    }

    @Test
    void editMedicalRecords() {
        medicalRecordsService.editMedicalRecords(persons.getFirstName(), persons.getLastName(), medicalRecords);
        verify(medicalRecordsR, times(1)).save(medicalRecords);
    }

    @Test
    void removeMedicalRecords() {
        medicalRecordsService.removeMedicalRecords(persons.getFirstName(), persons.getLastName());
        verify(medicalRecordsR, times(1)).delete(medicalRecords);
    }

    @Test
    void getMedicalRecordName() {
        MedicalRecords getMedicalRecords = medicalRecordsService.getMedicalRecordName(persons.getFirstName(), persons.getLastName());
        assertEquals(medicalRecords, getMedicalRecords);
    }

    @Test
    void getMedicalRecords() {
        when(medicalRecordsR.findAll()).thenReturn(medicalRecordsList);
        List<MedicalRecords> medicalRecordsGetList = medicalRecordsService.getMedicalRecords();
        assertEquals(medicalRecordsList.get(0), medicalRecordsGetList.get(0));
    }

    @Test
    void medicalRecordCheck() {
        when(medicalRecordsR.findByPersonsFirstNameIgnoreCaseAndPersonsLastNameIgnoreCase(persons.getFirstName(), persons.getLastName())).thenReturn(medicalRecords);
        boolean result = medicalRecordsService.medicalRecordCheck(persons.getFirstName(), persons.getLastName());
        assertTrue(result);
    }

    @Test
    void childAlert() {
        when(medicalRecordsR.findByPersonsAddressAddressIgnoreCase(addressString)).thenReturn(medicalRecordsList);
        Map<String, List<PersonsAndMedicalRecordsDTO>> result = medicalRecordsService.childAlert(addressString);
        assertTrue(result.isEmpty());
    }

    @Test
    void childAlertchild() {
        LocalDate localDate = LocalDate.now().minusYears(2);
        medicalRecords.setBirthdate(localDate);
        when(medicalRecordsR.findByPersonsAddressAddressIgnoreCase(addressString)).thenReturn(medicalRecordsList);
        Map<String, List<PersonsAndMedicalRecordsDTO>> result = medicalRecordsService.childAlert(addressString);
        assertTrue(result.get("Adult").isEmpty());
        assertFalse(result.get("Child").isEmpty());
    }

    @Test
    void personsListToMedicalRecordsList() {
        List<MedicalRecords> result = medicalRecordsService.personsListToMedicalRecordsList(personsList);
        assertEquals(medicalRecordsList, result);
    }
}