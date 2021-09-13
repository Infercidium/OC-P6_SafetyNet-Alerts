package com.Infercidium.SafetyNet.rest;

import com.infercidium.safetynet.dto.PersonsDTO;
import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.rest.StationNumberRest;
import com.infercidium.safetynet.service.StationNumberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {StationNumberRest.class})
class StationNumberRestTest {

    @MockBean
    private StationNumberService stationNumberS;
    @Autowired
    private StationNumberRest stationNumberRest;

    String addressString = "1 rue du testing";
    Address address = new Address(addressString);

    Persons persons = new Persons("Jean", "Bobine", address, "Testy", 12345, "456-789-1011", "jbob@email.com");

    PersonsDTO stationNumberDTO = new PersonsDTO();
    List<PersonsDTO> stationNumberDTOList = new ArrayList<>();

    @BeforeEach
    private void setUpPerTest() throws NoSuchMethodException {
        stationNumberDTO.setFirstName(persons.getFirstName());
        stationNumberDTO.setLastName(persons.getLastName());
        stationNumberDTO.setAddress(persons.getAddress().getAddress());
        stationNumberDTO.setPhone(persons.getPhone());
        stationNumberDTOList.add(stationNumberDTO);
    }

    @Test
    void getStationNumber() {
        Map<String, Object> stationNumberMap = new HashMap<>();
        stationNumberMap.put("Resident", stationNumberDTOList);
        stationNumberMap.put("Adult", 1);
        stationNumberMap.put("Child", 0);
        when(stationNumberS.stationNumber(1)).thenReturn(stationNumberMap);
        Map<String, Object> result = stationNumberRest.getStationNumber(1);
        assertEquals(result.get("Resident"), stationNumberDTOList);
        assertEquals(1, result.get("Adult"));
        assertEquals(0, result.get("Child"));
    }
}