package com.infercidium.safetynet.repository;

import com.infercidium.safetynet.model.Firestations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FirestationsRepository
        extends JpaRepository<Firestations, Long> {
    List<Firestations> findByStation(int station);
    Optional<Firestations> findByAddressIgnoreCase(String address);
}
