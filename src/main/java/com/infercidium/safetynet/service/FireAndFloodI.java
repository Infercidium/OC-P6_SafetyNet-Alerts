package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;

import java.util.List;

public interface FireAndFloodI {
    List<MedicalRecords> personsListToMedicalRecordsList(
            List<Persons> personsList);
}
