package com.infercidium.safetynet.repository;

import com.infercidium.safetynet.model.MedicalRecords;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicalrecordsRepository
        extends JpaRepository<MedicalRecords, Long> {
    Optional<MedicalRecords> findByFirstNameIgnoreCaseAndLastNameIgnoreCase(
            String firstName, String lastName);
    List<MedicalRecords> findByPersonsAddressIgnoreCase(String address);
}
