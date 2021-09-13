package com.infercidium.safetynet.service;

import com.infercidium.safetynet.dto.PersonsDTO;
import com.infercidium.safetynet.model.Firestations;
import com.infercidium.safetynet.model.Persons;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * PhoneAlert Service develops public methods of interfaces,
 * and private methods.
 */
@Service
public class PhoneAlertService implements PhoneAlertI {

    /**
     *  Instantiation of personsInterface.
     */
    private final PersonsI personsI;

    /**
     * Instantiation of firestationsInterface.
     */
    private final FirestationsI firestationsI;

    /**
     *  Class constructor.
     * @param personsIn this is personsInterface.
     * @param firestationsIn this is firestationsInterface.
     */
    public PhoneAlertService(final PersonsI personsIn,
                             final FirestationsI firestationsIn) {
        this.personsI = personsIn;
        this.firestationsI = firestationsIn;
    }

    /**
     * Find the phone people covered by this station.
     * @param station to retrieve the list of persons.
     * @return list of phone Persons.
     */
    @Override
    public List<PersonsDTO> getPersonsPhone(final int station) {
        List<PersonsDTO> personsDTOList = new ArrayList<>();
        Firestations firestation
                = firestationsI.getFirestationsStation(station);
        List<Persons> personsList
                = personsI.addressSetToPersonsList(firestation.getAddress());
        for (Persons person : personsList) {
            PersonsDTO personsDto = new PersonsDTO();
            personsDto.setPhone(person.getPhone());
            if (!phoneDuplicate(personsDTOList, personsDto.getPhone())) {
                personsDTOList.add(personsDto);
            }
        }
        return personsDTOList;
    }

    /**
     * Check if phone exists in PersonsDTO list.
     * @param personsDTO this is a list.
     * @param phone      this is phone to verify.
     * @return True if phone exist in list or False if phone is new.
     */
    private boolean phoneDuplicate(final List<PersonsDTO> personsDTO,
                                   final String phone) {
        for (PersonsDTO personsDto : personsDTO) {
            if (personsDto.getPhone().equals(phone)) {
                return true;
            }
        }
        return false;
    }
}
