package com.infercidium.safetynet.service;

import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;

import java.util.List;
import java.util.Map;

public interface MedicalRecordsI {
    //Post, Put, delete
    MedicalRecords postMedicalRecords(MedicalRecords medicalRecords, String firstName, String lastName);
    void editMedicalRecords(String firstName, String lastName, MedicalRecords medicalRecords);
    void removeMedicalRecords(String firstName, String lastName);

    //Get
    MedicalRecords getMedicalRecordName(String firstName, String lastName);
    List<MedicalRecords> getMedicalRecords();

    //URL lié à MedicalRecords
    boolean checkMajority(String firstName, String lastName);
    List<Persons> getPersonsAddress(String address);
    Map<String, Object> getChildAlertCount(List<PersonsAndMedicalRecordsDTO> personsAndMedicalrecordsDTO);
}
