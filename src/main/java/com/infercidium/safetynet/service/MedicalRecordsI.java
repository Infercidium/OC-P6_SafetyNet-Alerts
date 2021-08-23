package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.MedicalRecords;

import java.util.List;

public interface MedicalRecordsI {

    //Post, Put, delete
    MedicalRecords createMedicalRecords(MedicalRecords medicalRecords);
    void editMedicalRecords(String firstName, String lastName, MedicalRecords medicalRecords);
    void removeMedicalRecords(String firstName, String lastName);

    //Get
    List<MedicalRecords> getMedicalRecord(String firstName, String lastName);
    List<MedicalRecords> getMedicalRecords();
}
