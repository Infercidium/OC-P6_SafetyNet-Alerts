package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.MedicalRecords;
import org.apache.commons.lang.NullArgumentException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.util.List;

public interface MedicalRecordsI {

    //Post, Put, Delete
    ResponseEntity<Void> createMedicalRecords(
            MedicalRecords medicalRecords) throws NullArgumentException;
    ResponseEntity<Void> editMedicalRecords(String firstName,
                                            String lastName,
                                            MedicalRecords medicalRecords);
    ResponseEntity<Void> removeMedicalRecords(String firstName,
                                              String lastName);

    //Get
    List<MedicalRecords> getMedicalRecord(String firstName,
                                          String lastName);
    List<MedicalRecords> getMedicalRecords();

    //URL lié MedicalRecords
    MappingJacksonValue getChildAlert(String address);
    MappingJacksonValue getPersonInfo(String firstName, String lastName);
}
