package com.infercidium.safetynet.service;

import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;

import java.util.List;
import java.util.Map;

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

    //URL lié à MedicalRecords
    /**
     * Check the person's age to find out if they are over majorite.
     * @param firstName to check medicalRecords Age.
     * @param lastName to check medicalRecords Age.
     * @return True if major and False if not.
     */
    boolean checkMajority(String firstName, String lastName);

    /**
     * Relay method.
     * @param address to check Persons.
     * @return list of Persons checked.
     */
    List<Persons> getPersonsAddress(String address);

    /**
     * Formatting the response for the ChildAlert URL.
     * @param personsAndMedicalrecordsDTO : list of inhabitants.
     * @return Map expected in result.
     */
    Map<String, Object> getChildAlertCount(
            List<PersonsAndMedicalRecordsDTO> personsAndMedicalrecordsDTO);
}
