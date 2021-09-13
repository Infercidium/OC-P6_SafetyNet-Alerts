package com.Infercidium.SafetyNet.mapper;

import com.infercidium.safetynet.dto.FirestationsDTO;
import com.infercidium.safetynet.mapper.AddressMapper;
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
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {FirestationsMapperImpl.class})
class FirestationsMapperTest {

    @MockBean
    private AddressMapper addressMapper;
    @Autowired
    private FirestationsMapper firestationsMapper;

    String addressString = "1 rue du testing";
    Address address = new Address(addressString);
    Set<Address> addressSet = Collections.singleton(address);
    Set<String> addressStringSet = Collections.singleton(addressString);

    FirestationsDTO firestationsDTO = new FirestationsDTO();
    List<FirestationsDTO> firestationsDTOList = new ArrayList<>();

    Firestations firestations = new Firestations(addressSet, 1);
    List<Firestations> firestationsList = new ArrayList<>();

    Persons persons = new Persons("Jean", "Bobine", address, "Testy", 12345, "456-789-1011", "jbob@email.com");
    List<Persons> personsList = new ArrayList<>();

    LocalDate localDate = LocalDate.of(1990, 1, 1);
    MedicalRecords medicalRecords = new MedicalRecords(localDate, new HashSet<>(), new HashSet<>(), persons);
    List<MedicalRecords> medicalRecordsList = new ArrayList<>();

    @BeforeEach
    private void setUpPerTest() {
        firestationsDTO.setStation(1);
        firestationsDTO.setAddress(addressStringSet);
        firestationsDTOList.add(firestationsDTO);

        firestationsList.add(firestations);
        personsList.add(persons);
        medicalRecordsList.add(medicalRecords);

        when(addressMapper.addressFromString(addressString)).thenReturn(address);
        when(addressMapper.stringFromAddress(address)).thenReturn(addressString);
        when(addressMapper.addressFromString(addressStringSet)).thenReturn(addressSet);
        when(addressMapper.stringFromAddress(addressSet)).thenReturn(addressStringSet);
    }


    @Test
    void dtoToModel() {
        Firestations convertFirestations = firestationsMapper.dtoToModel(firestationsDTO);
        assertTrue(firestations.toString().equals(convertFirestations.toString()));
    }

    @Test
    void modelToDto() {
        FirestationsDTO firestationsDto = firestationsMapper.modelToDto(firestations);
        assertEquals(firestationsDTO.getAddress(), firestationsDto.getAddress());
        assertEquals(firestationsDTO.getStation(), firestationsDto.getStation());
    }

    @Test
    void testModelToDto() {
        List<FirestationsDTO> firestationsDtoList = firestationsMapper.modelToDto(firestationsList);
        assertEquals(firestationsDTOList.get(0).getAddress(), firestationsDtoList.get(0).getAddress());
        assertEquals(firestationsDtoList.get(0).getStation(), firestationsDtoList.get(0).getStation());
    }
}
