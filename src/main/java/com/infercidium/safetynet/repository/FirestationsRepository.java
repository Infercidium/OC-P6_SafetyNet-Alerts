package com.infercidium.safetynet.repository;

import com.infercidium.safetynet.model.Firestations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FirestationsRepository extends JpaRepository<Firestations, Long> {
    Firestations findByAddressAddressIgnoreCase(String address);
    List<Firestations> findByStation(int station);
}
