package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.MedicalRecords;

import java.util.List;

public interface MedicalRecordsI {

    //Post, Put, delete
    /**
     * Post Method Service.
     * @param medicalRecords to save.
     * @param firstName to check Persons.
     * @param lastName to check Persons.
     * @return medicalrecords saved.
     */
    MedicalRecords postMedicalRecords(MedicalRecords medicalRecords,
                                      String firstName, String lastName);

    /**
     * Edit Method Service.
     * @param firstName to check Medicalrecords.
     * @param lastName to check MedicalRecords.
     * @param medicalRecords to edit.
     */
    void editMedicalRecords(String firstName, String lastName,
                            MedicalRecords medicalRecords);

    /**
     * Remove Method Service.
     * @param firstName to check MedicalRecords.
     * @param lastName to check MedicalRecords.
     */
    void removeMedicalRecords(String firstName, String lastName);

    //Get
    /**
     * Get Method Service.
     * @param firstName to check MedicalRecords.
     * @param lastName to check MedicalRecords.
     * @return medicalrecords checked.
     */
    MedicalRecords getMedicalRecordName(String firstName, String lastName);

    /**
     * GetAll Method Service.
     * @return all MedicalRecords.
     */
    List<MedicalRecords> getMedicalRecords();

    //Method Tiers

    boolean medicalRecordCheck(String firstName, String lastName);
}
