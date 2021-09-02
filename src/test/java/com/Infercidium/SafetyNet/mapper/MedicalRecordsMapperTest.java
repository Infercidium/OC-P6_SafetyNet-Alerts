package com.Infercidium.SafetyNet.mapper;

import com.infercidium.safetynet.dto.MedicalRecordsDTO;
import com.infercidium.safetynet.dto.PersonInfoDTO;
import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.mapper.MedicalRecordsMapper;
import com.infercidium.safetynet.mapper.MedicalRecordsMapperImpl;
import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {MedicalRecordsMapperImpl.class})
class MedicalRecordsMapperTest {

    @Autowired
    private MedicalRecordsMapper medicalRecordsMapperImpl;

    Persons persons = new Persons("Jean", "Bobine", new Address("1 rue du testing"), "Testy", 12345, "456-789-1011", "jbob@email.com");
    List<Persons> personsList = new ArrayList<>();

    LocalDate localDate = LocalDate.of(1990, 1, 1);
    MedicalRecords medicalRecords = new MedicalRecords(localDate, new HashSet<>(), new HashSet<>(), persons);
    List<MedicalRecords> medicalRecordsList = new ArrayList<>();

    MedicalRecordsDTO medicalRecordsDTO = new MedicalRecordsDTO();
    List<MedicalRecordsDTO> medicalRecordsDTOList = new ArrayList<>();

    @BeforeEach
    private void setUpPerTest() {
        medicalRecordsDTO.setFirstName(medicalRecords.getFirstName());
        medicalRecordsDTO.setLastName(medicalRecords.getLastName());
        medicalRecordsDTO.setBirthdate(medicalRecords.getBirthdate());
        medicalRecordsDTO.setMedications(medicalRecords.getMedications());
        medicalRecordsDTO.setAllergies(medicalRecords.getAllergies());
        medicalRecordsDTOList.add(medicalRecordsDTO);

        personsList.add(persons);
        medicalRecordsList.add(medicalRecords);
    }

    @Test
    void dtoToModel() {
        MedicalRecords medicalRecord = medicalRecordsMapperImpl.dtoToModel(medicalRecordsDTO);
        medicalRecord.setPersons(persons);
        assertTrue(medicalRecords.toString().equals(medicalRecord.toString()));
    }

    @Test
    void modelToDto() {
        MedicalRecordsDTO medicalRecordsDto = medicalRecordsMapperImpl.modelToDto(medicalRecords);
        assertEquals(medicalRecords.getFirstName(), medicalRecordsDto.getFirstName());
        assertEquals(medicalRecords.getLastName(), medicalRecordsDto.getLastName());
        assertEquals(medicalRecords.getBirthdate(), medicalRecordsDto.getBirthdate());
    }

    @Test
    void testModelToDto() {
        List<MedicalRecordsDTO> medicalRecordsDtoList = medicalRecordsMapperImpl.modelToDto(medicalRecordsList);
        assertFalse(medicalRecordsDtoList.isEmpty());
        assertEquals(medicalRecords.getBirthdate(), medicalRecordsDtoList.get(0).getBirthdate());
    }

    @Test
    void personsModelToChildAlertAndFireDTO() {
        List<PersonsAndMedicalRecordsDTO> personsAndMedicalrecordsDto = medicalRecordsMapperImpl.personsModelToChildAlertAndFireDTO(personsList);
        assertFalse(personsAndMedicalrecordsDto.isEmpty());
        assertEquals(persons.getFirstName(), personsAndMedicalrecordsDto.get(0).getFirstName());
        assertEquals(persons.getLastName(), personsAndMedicalrecordsDto.get(0).getLastName());
    }

    @Test
    void modelToPersonInfoDTO() {
        PersonInfoDTO personInfoDto = medicalRecordsMapperImpl.modelToPersonInfoDTO(medicalRecords);
        assertEquals(persons.getFirstName(), personInfoDto.getFirstName());
        assertEquals(persons.getLastName(), personInfoDto.getLastName());
        assertEquals(persons.getAddress().getAddress() ,personInfoDto.getAddress());
        assertEquals(persons.getEmail(), personInfoDto.getEmail());
        assertEquals(medicalRecords.getAge(), personInfoDto.getAge());
    }
}