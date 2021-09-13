package com.Infercidium.SafetyNet.mapper;

import com.infercidium.safetynet.dto.PersonInfoDTO;
import com.infercidium.safetynet.mapper.PersonInfoMapper;
import com.infercidium.safetynet.mapper.PersonInfoMapperImpl;
import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {PersonInfoMapperImpl.class})
class PersonInfoMapperTest {

    @Autowired
    PersonInfoMapper personInfoMapper;

    Address address = new Address("1 rue du testing");

    Persons persons = new Persons("Jean", "Bobine", address, "Testy", 12345, "456-789-1011", "jbob@email.com");

    LocalDate localDate = LocalDate.of(1990, 1, 1);
    MedicalRecords medicalRecords = new MedicalRecords(localDate, new HashSet<>(), new HashSet<>(), persons);

    PersonInfoDTO personInfoDTO = new PersonInfoDTO();

    @BeforeEach
    private void setUpPerTest() {
        personInfoDTO.setFirstName(medicalRecords.getFirstName());
        personInfoDTO.setLastName(medicalRecords.getLastName());
        personInfoDTO.setAge(medicalRecords.getAge());
        personInfoDTO.setAllergies(medicalRecords.getAllergies());
        personInfoDTO.setMedications(medicalRecords.getMedications());

        personInfoDTO.setEmail(persons.getEmail());
        personInfoDTO.setAddress(persons.getAddress().getAddress());
    }

    @Test
    void modelToDto() {
        PersonInfoDTO personInfoDto = personInfoMapper.modelToDto(medicalRecords);
        assertEquals(personInfoDTO.getFirstName(), personInfoDto.getFirstName());
        assertEquals(personInfoDTO.getLastName(), personInfoDto.getLastName());
        assertEquals(personInfoDTO.getAge(), personInfoDto.getAge());
        assertEquals(personInfoDTO.getAllergies(), personInfoDto.getAllergies());
        assertEquals(personInfoDTO.getMedications(), personInfoDto.getMedications());
        assertEquals(personInfoDTO.getEmail(), personInfoDto.getEmail());
        assertEquals(personInfoDTO.getAddress(), personInfoDto.getAddress());
    }
}