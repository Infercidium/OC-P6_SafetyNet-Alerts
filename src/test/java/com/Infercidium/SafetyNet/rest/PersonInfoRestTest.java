package com.Infercidium.SafetyNet.rest;

import com.infercidium.safetynet.dto.PersonInfoDTO;
import com.infercidium.safetynet.mapper.PersonInfoMapperImpl;
import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.rest.PersonInfoRest;
import com.infercidium.safetynet.service.MedicalRecordsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {PersonInfoRest.class})
class PersonInfoRestTest {
    @MockBean
    private MedicalRecordsService medicalRecordsS;
    @MockBean
    private PersonInfoMapperImpl personInfoM;
    @Autowired
    private PersonInfoRest personInfoRest;

    Persons persons = new Persons("Jean", "Bobine", new Address("1 rue du testing"), "Testy", 12345, "456-789-1011", "jbob@email.com");

    LocalDate localDate = LocalDate.of(1990, 1, 1);
    MedicalRecords medicalRecords = new MedicalRecords(localDate, new HashSet<>(), new HashSet<>(), persons);

    PersonInfoDTO personInfoDTO = new PersonInfoDTO();

    @BeforeEach
    private void setUpPerTest() {
        personInfoDTO.setFirstName(persons.getFirstName());
        personInfoDTO.setLastName(persons.getLastName());
        personInfoDTO.setMedications(medicalRecords.getMedications());
        personInfoDTO.setAllergies(medicalRecords.getAllergies());
        personInfoDTO.setAge(medicalRecords.getAge());
        personInfoDTO.setAddress(persons.getAddress().getAddress());
        personInfoDTO.setEmail(persons.getEmail());
    }

    @Test
    void getPersonInfo() {
        when(personInfoM.modelToDto(medicalRecords)).thenReturn(personInfoDTO);
        when(medicalRecordsS.getMedicalRecordName(medicalRecords.getFirstName(), medicalRecords.getLastName())).thenReturn(medicalRecords);
        PersonInfoDTO personInfoDto = personInfoRest.getPersonInfo(persons.getFirstName(), persons.getLastName());
        assertEquals(personInfoDTO.getFirstName(), personInfoDto.getFirstName());
        assertEquals(personInfoDTO.getLastName(), personInfoDto.getLastName());
        assertEquals(personInfoDTO.getAge(), personInfoDto.getAge());
        assertEquals(personInfoDTO.getAddress(), personInfoDto.getAddress());
        assertEquals(personInfoDTO.getEmail(), personInfoDto.getEmail());
    }
}