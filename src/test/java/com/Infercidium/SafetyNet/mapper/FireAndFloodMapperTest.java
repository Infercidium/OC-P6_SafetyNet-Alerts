package com.Infercidium.SafetyNet.mapper;

import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.mapper.FireAndFloodMapper;
import com.infercidium.safetynet.mapper.FireAndFloodMapperImpl;
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

@SpringBootTest(classes = {FireAndFloodMapperImpl.class})
class FireAndFloodMapperTest {

    @Autowired
    private FireAndFloodMapper fireAndFloodMapper;

    Persons persons = new Persons("Jean", "Bobine", new Address("1 rue du testing"), "Testy", 12345, "456-789-1011", "jbob@email.com");

    LocalDate localDate = LocalDate.of(1990, 1, 1);
    MedicalRecords medicalRecords = new MedicalRecords(localDate, new HashSet<>(), new HashSet<>(), persons);
    List<MedicalRecords> medicalRecordsList = new ArrayList<>();

    @BeforeEach
    private void setUpPerTest() {
        medicalRecordsList.add(medicalRecords);
    }

    @Test
    void modelToDto() {
        PersonsAndMedicalRecordsDTO result = fireAndFloodMapper.modelToDto(medicalRecords);
        assertFalse(result == null);
        assertEquals(medicalRecords.getAge() ,result.getAge());
    }

    @Test
    void testModelToDto() {
        List<PersonsAndMedicalRecordsDTO> result = fireAndFloodMapper.modelToDto(medicalRecordsList);
        assertFalse(result.isEmpty());
        assertEquals(medicalRecords.getAge() ,result.get(0).getAge());
    }
}