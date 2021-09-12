package com.infercidium.safetynet.service;

import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;

import java.util.List;
import java.util.Map;

public interface ChildAlertI {

    /**
     *Method which finds and formats childAlert lists.
     * @param address of the targeted residence.
     * @return Children List and Adult List
     * if Child list not empty.
     */
    Map<String, List<PersonsAndMedicalRecordsDTO>> childAlert(String address);
}
