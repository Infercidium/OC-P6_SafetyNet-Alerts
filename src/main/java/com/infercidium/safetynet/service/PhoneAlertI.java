package com.infercidium.safetynet.service;

import com.infercidium.safetynet.dto.PersonsDTO;

import java.util.List;

/**
 * PhoneAlert interface to store public methods
 * and use them in different services.
 */
public interface PhoneAlertI {

    /**
     * Find the phone people covered by this station.
     * @param station to retrieve the list of persons.
     * @return list of phone Persons.
     */
    List<PersonsDTO> getPersonsPhone(int station);
}
