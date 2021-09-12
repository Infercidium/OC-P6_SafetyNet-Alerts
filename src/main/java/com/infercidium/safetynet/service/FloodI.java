package com.infercidium.safetynet.service;

import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.model.MedicalRecords;

import java.util.List;
import java.util.Map;

public interface FloodI {
    List<MedicalRecords> getFloodMedicalRecords(int station);

    Map<String, List<PersonsAndMedicalRecordsDTO>> getFloodResult(
            List<PersonsAndMedicalRecordsDTO> personsAndMedicalRecordsDTOList);
}
