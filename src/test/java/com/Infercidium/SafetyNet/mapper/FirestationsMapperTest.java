package com.Infercidium.SafetyNet.mapper;

import com.infercidium.safetynet.dto.FirestationsDTO;
import com.infercidium.safetynet.mapper.FirestationsMapper;
import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.Firestations;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FirestationsMapperTest {

    @Autowired
    FirestationsMapper firestationsMapperImpl;

    FirestationsDTO firestationsDTO = new FirestationsDTO();
    Firestations firestations = new Firestations(new Address("1 rue du testing"), 1);
    List<Firestations> firestationsList = new ArrayList<>();

    @Test
    void dtoToModel() {
        firestationsDTO.setStation(1);
        firestationsDTO.setAddress("1 rue du testing");
        Firestations convertFirestations = firestationsMapperImpl.dtoToModel(firestationsDTO);
        assertEquals(firestations, convertFirestations);
    }

    @Test
    void map() {
    }

    @Test
    void modelToDto() {
    }

    @Test
    void testModelToDto() {
    }

    @Test
    void testMap() {
    }

    @Test
    void personsModelToStationNumberDTO() {
    }

    @Test
    void personsAndMedicalRecordsModelToPersonsAndMedicalRecordsDTO() {
    }

    @Test
    void personsAndMedicalRecordsModelToFloodDTO() {
    }
}