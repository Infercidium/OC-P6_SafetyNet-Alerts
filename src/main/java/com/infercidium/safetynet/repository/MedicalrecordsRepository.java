package com.infercidium.safetynet.repository;

import com.infercidium.safetynet.model.MedicalRecords;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalrecordsRepository
        extends JpaRepository<MedicalRecords, Long> {

    /**
     * Consult the database in search of MedicalRecords.
     * @param firstName : firstname as a string.
     * @param lastName : lastname as a string.
     * @return corresponding medicalRecords.
     */
    MedicalRecords
    findByPersonsFirstNameIgnoreCaseAndPersonsLastNameIgnoreCase(
            String firstName, String lastName);

    List<MedicalRecords> findByPersonsAddressAddressIgnoreCase(String address);
}
