package com.Infercidium.SafetyNet.mapper;

import com.infercidium.safetynet.dto.MedicalRecordsDTO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(classes = {MedicalRecordsMapperImpl.class})
class MedicalRecordsMapperTest {

    @Autowired
    private MedicalRecordsMapper medicalRecordsMapper;

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
        MedicalRecords medicalRecord = medicalRecordsMapper.dtoToModel(medicalRecordsDTO);
        medicalRecord.setPersons(persons);
        assertEquals(medicalRecords.toString(), medicalRecord.toString());
    }

    @Test
    void modelToDto() {
        MedicalRecordsDTO medicalRecordsDto = medicalRecordsMapper.modelToDto(medicalRecords);
        assertEquals(medicalRecords.getFirstName(), medicalRecordsDto.getFirstName());
        assertEquals(medicalRecords.getLastName(), medicalRecordsDto.getLastName());
        assertEquals(medicalRecords.getBirthdate(), medicalRecordsDto.getBirthdate());
    }

    @Test
    void testModelToDto() {
        List<MedicalRecordsDTO> medicalRecordsDtoList = medicalRecordsMapper.modelToDto(medicalRecordsList);
        assertFalse(medicalRecordsDtoList.isEmpty());
        assertEquals(medicalRecords.getBirthdate(), medicalRecordsDtoList.get(0).getBirthdate());
    }
}
