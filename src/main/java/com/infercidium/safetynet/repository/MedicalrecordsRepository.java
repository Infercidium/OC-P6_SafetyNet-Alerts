package com.infercidium.safetynet.repository;

import com.infercidium.safetynet.model.MedicalRecords;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalrecordsRepository extends JpaRepository<MedicalRecords, Long> {
}
