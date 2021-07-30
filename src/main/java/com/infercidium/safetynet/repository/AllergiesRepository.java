package com.infercidium.safetynet.repository;

import com.infercidium.safetynet.model.Allergies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllergiesRepository extends JpaRepository<Allergies, Long> {
}
