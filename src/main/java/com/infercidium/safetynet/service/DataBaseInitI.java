package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.Allergies;
import com.infercidium.safetynet.model.Medications;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * DataBaseInit interface to store public methods
 * and use them in different services.
 */
public interface DataBaseInitI {

    /**
     * File to string converter.
     * @param path of File.
     * @return contents of the file in String.
     */
    String convertFileToString(String path);

    /**
     * Transforms a String into a Map.
     * @param data this is String.
     * @return a map containing the data of the String.
     */
    Map<String, Object> deserializeStringToMap(String data);

    /**
     * Retrieves the values contained in a key from the listed map.
     * @param passageList : the value to list.
     * @param name : the key to the map.
     * @return a list.
     */
    List<Map<String, String>> convertMaptoList(
            Map<String, Object> passageList, String name);

    /**
     * Retrieves the values contained in a key (MedicalRecords)
     * from the listed map.
     * @param passageList : the value to list.
     * @return a list.
     */
    List<Map<String, Object>> convertMedicalRecordsMaptoList(
            Map<String, Object> passageList);

    /**
     *  Instantiating and saving Firestations.
     * @param firestations : the list to instantiate and save.
     */
    void instanciateListFirestations(List<Map<String, String>> firestations)
            throws SQLIntegrityConstraintViolationException;

    /**
     * Instantiating and saving Persons.
     * @param persons : the list to instantiate and save.
     */
    void instanciateListPersons(List<Map<String, String>> persons);

    /**
     * Instantiating and saving MedicalRecords.
     * @param medicalRecords : the list to instantiate and save.
     */
    void instanciateListMedicalRecords(
            List<Map<String, Object>> medicalRecords);

    /**
     * Instantiating and saving Medications.
     * @param medications : the list to instantiate and save.
     * @return the list once instantiate and save.
     */
    Set<Medications> instanciateListMedications(List<String> medications);

    /**
     * Instantiating and saving Allergies.
     * @param allergies : the list to instantiate and save.
     * @return the list once instantiate and save.
     */
    Set<Allergies> instanciateListAllergies(List<String> allergies);
}
