package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.Allergies;
import com.infercidium.safetynet.model.Medications;

import java.util.Set;

public interface SecondaryTableI {

    /**
     * Check the address, if it exists add the id, if not create it.
     * @param address to be checked.
     * @return address complete.
     */
    Address checkAddress(Address address);

    /**
     * Check the allergy of allergiesSet,
     * if it exists add the id, if not create it.
     * @param allergiesSet to be checked.
     * @return allergiesSet complete.
     */
    Set<Allergies> checkAllergies(Set<Allergies> allergiesSet);

    /**
     * Check the medication of medicationsSet,
     * if it exists add the id, if not create it.
     * @param medicationsSet to be checked.
     * @return  medicationsSet complete.
     */
    Set<Medications> checkMedications(Set<Medications> medicationsSet);
}
