package com.infercidium.safetynet.service;

import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.model.MedicalRecords;

import java.util.List;
import java.util.Map;

/**
 * Fire interface to store public methods and use them in different services.
 */
public interface FireI {
    /**
     * Allows you to obtain the list of Medicalrecords
     * of Persons living at the address.
     * @param address : String of address target.
     * @return list of Medicalrecords.
     */
    List<MedicalRecords> getFireMedicalRecords(String address);

    /**
     * The method of shaping Fire.
     * @param address : String of address target.
     * @param personsAndMedicalRecordsDTOList : residents List.
     * @return A Map containing the stations covering the address
     * and the residents living there.
     */
    Map<String, Object> getFireResult(
            String address,
            List<PersonsAndMedicalRecordsDTO> personsAndMedicalRecordsDTOList);
}
