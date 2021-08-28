package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.Allergies;
import com.infercidium.safetynet.model.Medications;

import java.util.Set;

public interface SecondaryTableI {
    Address checkAddress(Address address);
    Set<Allergies> checkAllergies(Set<Allergies> allergiesSet);
    Set<Medications> checkMedications(Set<Medications> medicationsSet);
}
