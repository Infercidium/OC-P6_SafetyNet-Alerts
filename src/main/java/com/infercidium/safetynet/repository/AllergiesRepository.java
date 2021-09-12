package com.infercidium.safetynet.repository;

import com.infercidium.safetynet.model.Allergies;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Methods of retrieving Allergies data from the database.
 */
public interface AllergiesRepository extends JpaRepository<Allergies, Long> {

    /**
     * Consult the database for allergies.
     * @param allergy : String allergy.
     * @return the corresponding allergies.
     */
    Allergies findByAllergyIgnoreCase(String allergy);
}
