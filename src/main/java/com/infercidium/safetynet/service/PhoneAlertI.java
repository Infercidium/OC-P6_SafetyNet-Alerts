package com.infercidium.safetynet.service;

import com.infercidium.safetynet.dto.PersonsDTO;

import java.util.List;

public interface PhoneAlertI {
    List<PersonsDTO> getPersonsPhone(int station);
}
