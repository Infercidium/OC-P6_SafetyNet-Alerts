package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.Allergies;
import com.infercidium.safetynet.model.Medications;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DataBaseInitI {
    String convertFileToString(String path);
    Map<String, Object> deserializeStringToMap(String data);
    List<Map<String, String>> convertMaptoList(Map<String, Object> passageList, String name);
    List<Map<String, Object>> convertMedicalRecordsMaptoList(Map<String, Object> passageList);
    void instanciateListFirestations(List<Map<String, String>> firestations);
    void instanciateListPersons(List<Map<String, String>> persons);
    void instanciateListMedicalRecords(List<Map<String, Object>> medicalRecords);
    Set<Medications> instanciateListMedications(List<String> medications);
    Set<Allergies> instanciateListAllergies(List<String> allergies);
}
