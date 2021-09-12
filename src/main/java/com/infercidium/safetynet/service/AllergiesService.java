package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.Allergies;
import com.infercidium.safetynet.repository.AllergiesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Allergies Service develops public methods of interfaces, and private methods.
 */
@Service
public class AllergiesService implements AllergiesI {

    /**
     * Instantiation of LOGGER in order to inform in console.
     */
    private static final Logger LOGGER
            = LoggerFactory.getLogger(AllergiesService.class);

    /**
     * Instantiation of AllergiesRepository.
     */
    private final AllergiesRepository allergiesR;

    /**
     * Class constructor.
     * @param allergiesRe this is AllergiesRepository.
     */
    public AllergiesService(final AllergiesRepository allergiesRe) {
        this.allergiesR = allergiesRe;
    }

    /**
     * Check the allergy of allergiesSet,
     * if it exists add the id, if not create it.
     * @param allergiesSet to be checked.
     * @return allergiesSet complete.
     */
    @Override
    public Set<Allergies> checkAllergies(final Set<Allergies> allergiesSet) {
        Set<Allergies> allergies = new HashSet<>();
        for (Allergies allergie : allergiesSet) {
            if (allergiesR.findByAllergyIgnoreCase(
                    allergie.getAllergy()) == null) {
                allergiesR.save(allergie);
                LOGGER.debug("Save " + allergie + " in the Allergies table");
            } else {
                allergie.setId(allergiesR.findByAllergyIgnoreCase(
                        allergie.getAllergy()).getId());
            }
            allergies.add(allergie);
        }
        LOGGER.debug("Allergie check completed");
        return allergies;
    }
}
