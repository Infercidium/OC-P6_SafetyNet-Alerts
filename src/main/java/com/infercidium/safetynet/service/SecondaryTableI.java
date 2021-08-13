package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.Allergies;
import com.infercidium.safetynet.model.Medications;

import java.util.List;
import java.util.Set;

public interface SecondaryTableI {
    List<Allergies> checkAllergieMedicalRecords(List<String> allergies);
    List<Medications> checkMedicationMedicalRecords(List<String> medications);
    Set<Allergies> checkAllergieMedicalRecords(Set<Allergies> allergiesSet);
    Set<Medications> checkMedicationMedicalRecords(Set<Medications> medicationsSet);
}
