package com.infercidium.safetynet.service;

import com.infercidium.safetynet.dto.PersonsDTO;

import java.util.List;

public interface CommunityEmailI {

    /**
     * Find the email people living in the city.
     * @param city to check Persons.
     * @return list of email Persons.
     */
    List<PersonsDTO> getPersonsEmail(String city);
}
