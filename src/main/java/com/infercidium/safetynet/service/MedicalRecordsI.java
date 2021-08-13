package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Name;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface MedicalRecordsI {
    //Post, Put, Delete, Get
    ResponseEntity<Void> createMedicalRecords(MedicalRecords medicalRecords);
    ResponseEntity<Void> editMedicalRecords(Name name, MedicalRecords medicalRecords);
    ResponseEntity<Void> removeMedicalRecords(Name name);
    Optional<MedicalRecords> getMedicalRecord(Name name);
    List<MedicalRecords> getMedicalRecords();

    //URL lié MedicalRecords
    String addressChildrenInfo(String address);
}
