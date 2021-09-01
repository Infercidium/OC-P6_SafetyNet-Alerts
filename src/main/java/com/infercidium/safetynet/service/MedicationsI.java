package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.Medications;

import java.util.Set;

public interface MedicationsI {

    /**
     * Check the medication of medicationsSet,
     * if it exists add the id, if not create it.
     * @param medicationsSet to be checked.
     * @return  medicationsSet complete.
     */
    Set<Medications> checkMedications(Set<Medications> medicationsSet);
}
