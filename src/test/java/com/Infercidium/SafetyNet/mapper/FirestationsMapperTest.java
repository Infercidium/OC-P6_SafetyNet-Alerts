package com.Infercidium.SafetyNet.mapper;

import com.infercidium.safetynet.dto.FirestationsDTO;
import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.dto.StationNumberDTO;
import com.infercidium.safetynet.mapper.FirestationsMapper;
import com.infercidium.safetynet.mapper.FirestationsMapperImpl;
import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.Firestations;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {FirestationsMapperImpl.class}) //todo réactiver
class FirestationsMapperTest {

    /*@Autowired
    private FirestationsMapper firestationsMapperImpl;

    FirestationsDTO firestationsDTO = new FirestationsDTO();
    List<FirestationsDTO> firestationsDTOList = new ArrayList<>();

    Firestations firestations = new Firestations(new Address("1 rue du testing"), 1);
    List<Firestations> firestationsList = new ArrayList<>();

    Persons persons = new Persons("Jean", "Bobine", new Address("1 rue du testing"), "Testy", 12345, "456-789-1011", "jbob@email.com");
    List<Persons> personsList = new ArrayList<>();

    LocalDate localDate = LocalDate.of(1990, 1, 1);
    MedicalRecords medicalRecords = new MedicalRecords(localDate, new HashSet<>(), new HashSet<>(), persons);
    List<MedicalRecords> medicalRecordsList = new ArrayList<>();

    @BeforeEach
    private void setUpPerTest() {
        firestationsDTO.setStation(1);
        firestationsDTO.setAddress("1 rue du testing");
        firestationsDTOList.add(firestationsDTO);

        firestationsList.add(firestations);
        personsList.add(persons);
        medicalRecordsList.add(medicalRecords);
    }

    @Test
    void dtoToModel() {
        Firestations convertFirestations = firestationsMapperImpl.dtoToModel(firestationsDTO);
        assertTrue(firestations.toString().equals(convertFirestations.toString()));
    }

    @Test
    void map() {
        Address result = firestationsMapperImpl.map("1 rue du testing");
        assertEquals(firestations.getAddress().toString(), result.toString());
    }

    @Test
    void modelToDto() {
        FirestationsDTO firestationsDto = firestationsMapperImpl.modelToDto(firestations);
        assertEquals(firestationsDTO.getAddress(), firestationsDto.getAddress());
        assertEquals(firestationsDTO.getStation(), firestationsDto.getStation());
    }

    @Test
    void testModelToDto() {
        List<FirestationsDTO> firestationsDtoList = firestationsMapperImpl.modelToDto(firestationsList);
        assertEquals(firestationsDTOList.get(0).getAddress(), firestationsDtoList.get(0).getAddress());
        assertEquals(firestationsDtoList.get(0).getStation(), firestationsDtoList.get(0).getStation());
    }

    @Test
    void testMap() {
        String result = firestationsMapperImpl.map(firestations.getAddress());
        assertEquals(firestationsDTO.getAddress(), result);
    }

    @Test
    void personsModelToStationNumberDTO() {
        List<StationNumberDTO> stationNumberDtoList = firestationsMapperImpl.personsModelToStationNumberDTO(personsList);
        assertEquals(persons.getFirstName(), stationNumberDtoList.get(0).getFirstName());
        assertEquals(persons.getLastName(), stationNumberDtoList.get(0).getLastName());
        assertEquals(persons.getAddress().getAddress(), stationNumberDtoList.get(0).getAddress());
        assertEquals(persons.getPhone(), stationNumberDtoList.get(0).getPhone());
    }

    @Test
    void personsAndMedicalRecordsModelToPersonsAndMedicalRecordsDTO() {
        List<PersonsAndMedicalRecordsDTO> result = firestationsMapperImpl.personsAndMedicalRecordsModelToPersonsAndMedicalRecordsDTO(medicalRecordsList);
        assertFalse(result.isEmpty());
        assertEquals(medicalRecords.getAge() ,result.get(0).getAge());
    }

    @Test
    void personsAndMedicalRecordsModelToFloodDTO() {
        Map<String, List<MedicalRecords>> medicalRecordsMap = new HashMap<>();
        medicalRecordsMap.put(firestationsDTO.getAddress(), medicalRecordsList);
        Map<String, Object> result = firestationsMapperImpl.personsAndMedicalRecordsModelToFloodDTO(medicalRecordsMap);
        assertTrue(medicalRecordsMap.containsKey(persons.getAddress().getAddress()));
    }*/
}
