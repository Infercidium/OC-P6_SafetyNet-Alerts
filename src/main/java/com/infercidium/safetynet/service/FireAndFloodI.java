package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;

import java.util.List;

/**
 * FireAndFlood interface to store public methods
 * and use them in different services.
 */
public interface FireAndFloodI {

    /**
     * Retrieves a list of MedicalRecords via a list of Persons.
     * @param personsList to convert.
     * @return medicalRecordsList.
     */
    List<MedicalRecords> personsListToMedicalRecordsList(
            List<Persons> personsList);
}
