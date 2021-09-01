package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.Allergies;

import java.util.Set;

public interface AllergiesI {

    /**
     * Check the allergy of allergiesSet,
     * if it exists add the id, if not create it.
     * @param allergiesSet to be checked.
     * @return allergiesSet complete.
     */
    Set<Allergies> checkAllergies(Set<Allergies> allergiesSet);
}
