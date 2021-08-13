package com.infercidium.safetynet.repository;

import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Name;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalrecordsRepository
        extends JpaRepository<MedicalRecords, Name> {
}
