package com.Infercidium.SafetyNet.rest;

import com.infercidium.safetynet.dto.MedicalRecordsDTO;
import com.infercidium.safetynet.dto.PersonInfoDTO;
import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.mapper.MedicalRecordsMapper;
import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.rest.MedicalRecordsRest;
import com.infercidium.safetynet.service.MedicalRecordsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {MedicalRecordsRest.class})
class MedicalRecordsRestTest {

    @MockBean
    private MedicalRecordsService medicalRecordsS;
    @MockBean
    private MedicalRecordsMapper medicalRecordsM;
    @Autowired
    private MedicalRecordsRest medicalRecordsRest;

    Persons persons = new Persons("Jean", "Bobine", new Address("1 rue du testing"), "Testy", 12345, "456-789-1011", "jbob@email.com");
    List<Persons> personsList = new ArrayList<>();

    LocalDate localDate = LocalDate.of(1990, 1, 1);
    MedicalRecords medicalRecords = new MedicalRecords(localDate, new HashSet<>(), new HashSet<>(), persons);
    List<MedicalRecords> medicalRecordsList = new ArrayList<>();

    MedicalRecordsDTO medicalRecordsDTO = new MedicalRecordsDTO();
    List<MedicalRecordsDTO> medicalRecordsDTOList = new ArrayList<>();

    @BeforeEach
    private void setUpPerTest() {
        medicalRecordsList.add(medicalRecords);
        medicalRecordsDTO.setFirstName(medicalRecords.getFirstName());
        medicalRecordsDTO.setLastName(medicalRecords.getLastName());
        medicalRecordsDTO.setBirthdate(medicalRecords.getBirthdate());
        medicalRecordsDTO.setMedications(medicalRecords.getMedications());
        medicalRecordsDTO.setAllergies(medicalRecords.getAllergies());
        medicalRecordsDTOList.add(medicalRecordsDTO);
        personsList.add(persons);

        when(medicalRecordsS.getMedicalRecordName(medicalRecords.getFirstName(), medicalRecords.getLastName())).thenReturn(medicalRecords);

        when(medicalRecordsM.dtoToModel(medicalRecordsDTO)).thenReturn(medicalRecords);
    }

    @Test
    void createMedicalRecords() {
        when(medicalRecordsS.postMedicalRecords(medicalRecords, medicalRecords.getFirstName(), medicalRecords.getLastName())).thenReturn(medicalRecords);
        ResponseEntity<Void> responseEntity = medicalRecordsRest.createMedicalRecords(medicalRecordsDTO);
        assertEquals("201 CREATED", responseEntity.getStatusCode().toString());
        assertEquals("[Location:\"http://localhost/Jean/Bobine\"]", responseEntity.getHeaders().toString());
    }

    @Test
    void editMedicalRecords() {
        ResponseEntity<Void> responseEntity = medicalRecordsRest.editMedicalRecords(persons.getFirstName(), persons.getLastName(), medicalRecordsDTO);
        verify(medicalRecordsS, times(1)).editMedicalRecords(persons.getFirstName(), persons.getLastName(), medicalRecords);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
    }

    @Test
    void removeMedicalRecords() {
        ResponseEntity<Void> responseEntity = medicalRecordsRest.removeMedicalRecords(persons.getFirstName(), persons.getLastName());
        verify(medicalRecordsS, times(1)).removeMedicalRecords(persons.getFirstName(), persons.getLastName());
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
    }

    @Test
    void getMedicalRecord() {
        when(medicalRecordsM.modelToDto(medicalRecords)).thenReturn(medicalRecordsDTO);
        MedicalRecordsDTO medicalRecordsDto = medicalRecordsRest.getMedicalRecord(medicalRecords.getFirstName(), medicalRecords.getLastName());
        assertEquals(medicalRecordsDTO.getBirthdate(), medicalRecordsDto.getBirthdate());
    }

    @Test
    void getMedicalRecords() {
        when(medicalRecordsS.getMedicalRecords()).thenReturn(medicalRecordsList);
        when(medicalRecordsM.modelToDto(medicalRecordsList)).thenReturn(medicalRecordsDTOList);
        List<MedicalRecordsDTO> medicalRecordsDtoList = medicalRecordsRest.getMedicalRecords();
        assertEquals(1, medicalRecordsDtoList.size());

    }
}
