package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.Allergies;
import com.infercidium.safetynet.model.Medications;

import java.util.List;

public interface SecondaryTableI {
    List<Allergies> checkAllergieMedicalRecords(List<String> allergies);
    List<Medications> checkMedicationMedicalRecords(List<String> medications);
}
