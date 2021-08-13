package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.Firestations;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;

import java.util.List;
import java.util.Map;

public interface DataBaseInitI {

    String convertFileToString(String path);

    Map<String, Object> deserializeStringToMap(String data);

    List<Map<String, String>> convertMaptoList(
            Map<String, Object> passageList, String name);

    List<Map<String, Object>> convertMedicalRecordsMaptoList(
            Map<String, Object> passageList);

    List<Persons> instantiateListPersons(List<Map<String, String>> persons);

    List<Firestations> instantiateListFirestations(
            List<Map<String, String>> firestations);

    List<MedicalRecords> instantiateListMedicalRecords(
            List<Map<String, Object>> medicalRecords);

    void saveAllList(List<Persons> persons,
                     List<Firestations> firestations,
                     List<MedicalRecords> medicalRecords);
}
