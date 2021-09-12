package com.infercidium.safetynet.service;

import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.model.MedicalRecords;

import java.util.List;
import java.util.Map;

public interface FireI {
    List<MedicalRecords> getFireMedicalRecords(String address);

    Map<String, Object> getFireResult(
            String address,
            List<PersonsAndMedicalRecordsDTO> personsAndMedicalRecordsDTOList);
}
