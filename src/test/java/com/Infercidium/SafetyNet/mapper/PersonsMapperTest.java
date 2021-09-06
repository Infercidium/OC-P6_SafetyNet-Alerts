package com.Infercidium.SafetyNet.mapper;

import com.infercidium.safetynet.dto.PersonsDTO;
import com.infercidium.safetynet.mapper.PersonsMapper;
import com.infercidium.safetynet.mapper.PersonsMapperImpl;
import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.Persons;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {PersonsMapperImpl.class})
class PersonsMapperTest {

   /* @Autowired
    private PersonsMapper personsMapperImpl;

    Persons persons = new Persons("Jean", "Bobine", new Address("1 rue du testing"), "Testy", 12345, "456-789-1011", "jbob@email.com");
    List<Persons> personsList = new ArrayList<>();

    PersonsDTO personsDTO = new PersonsDTO();
    List<PersonsDTO> personsDTOList = new ArrayList<>();

    @BeforeEach
    private void setUpPerTest() {
        personsList.add(persons);

        personsDTO.setFirstName(persons.getFirstName());
        personsDTO.setLastName(persons.getLastName());
        personsDTO.setEmail(persons.getEmail());
        personsDTO.setPhone(persons.getPhone());
        personsDTO.setZip(persons.getZip());
        personsDTO.setCity(persons.getCity());
        personsDTO.setAddress(persons.getAddress().getAddress());
        personsDTOList.add(personsDTO);
    }

    @Test
    void dtoToModel() {
        Persons person = personsMapperImpl.dtoToModel(personsDTO);
        assertEquals(persons.toString(), person.toString());
    }

    @Test
    void map() {
        Set<Address> address = personsMapperImpl.map(persons.getAddress().getAddress());
        assertEquals(personsDTO.getAddress(), address.getAddress());
    }

    @Test
    void modelToDto() {
        PersonsDTO personsDto = personsMapperImpl.modelToDto(persons);
        assertEquals(persons.getFirstName(), personsDto.getFirstName());
        assertEquals(persons.getLastName(), personsDto.getLastName());
        assertEquals(persons.getEmail(), personsDto.getEmail());
        assertEquals(persons.getCity(), personsDto.getCity());
        assertEquals(persons.getZip(), personsDto.getZip());
        assertEquals(persons.getPhone(), personsDto.getPhone());
        assertEquals(persons.getAddress().getAddress(), personsDto.getAddress());
    }

    @Test
    void testModelToDto() {
        List<PersonsDTO> personsDtoList = personsMapperImpl.modelToDto(personsList);
        assertFalse(personsDtoList.isEmpty());
        assertEquals(persons.getFirstName(), personsDtoList.get(0).getFirstName());
        assertEquals(persons.getLastName(), personsDtoList.get(0).getLastName());
        assertEquals(persons.getEmail(), personsDtoList.get(0).getEmail());
        assertEquals(persons.getCity(), personsDtoList.get(0).getCity());
        assertEquals(persons.getZip(), personsDtoList.get(0).getZip());
        assertEquals(persons.getPhone(), personsDtoList.get(0).getPhone());
        assertEquals(persons.getAddress().getAddress(), personsDtoList.get(0).getAddress());

    }

    @Test
    void testMap() {
        String address = personsMapperImpl.map(persons.getAddress());
        assertEquals(persons.getAddress().getAddress(), address);
    }*/
}