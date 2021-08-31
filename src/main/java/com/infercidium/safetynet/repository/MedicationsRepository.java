package com.infercidium.safetynet.repository;

import com.infercidium.safetynet.model.Medications;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationsRepository
        extends JpaRepository<Medications, Long> {

    /**
     * Consult the database for Medications.
     * @param medication : chain medication.
     * @return corresponding medications.
     */
    Medications findByMedicationIgnoreCase(String medication);
}
