package com.infercidium.safetynet.service;

import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.model.MedicalRecords;

import java.util.List;
import java.util.Map;

/**
 * Flood interface to store public methods and use them in different services.
 */
public interface FloodI {
    /**
     * Allows to retrieve all the residents of a station.
     * @param station to use.
     * @return The list of all residents linked to the station.
     */
    List<MedicalRecords> getFloodMedicalRecords(int station);

    /**
     * Allows you to sort residents by address.
     * @param personsAndMedicalRecordsDTOList : The list of all residents
     *                                        linked to the station.
     * @return A Map of residents sorted by address.
     */
    Map<String, List<PersonsAndMedicalRecordsDTO>> getFloodResult(
            List<PersonsAndMedicalRecordsDTO> personsAndMedicalRecordsDTOList);
}
