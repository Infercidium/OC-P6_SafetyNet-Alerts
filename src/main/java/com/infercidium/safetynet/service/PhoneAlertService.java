package com.infercidium.safetynet.service;

import com.infercidium.safetynet.dto.PersonsDTO;
import com.infercidium.safetynet.model.Firestations;
import com.infercidium.safetynet.model.Persons;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhoneAlertService implements PhoneAlertI {
    private final PersonsI personsI;
    private final FirestationsI firestationsI;

    public PhoneAlertService(final PersonsI personsIn,
                             final FirestationsI firestationsIn) {
        this.personsI = personsIn;
        this.firestationsI = firestationsIn;
    }

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
     *
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
