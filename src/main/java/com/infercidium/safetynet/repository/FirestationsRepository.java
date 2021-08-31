package com.infercidium.safetynet.repository;

import com.infercidium.safetynet.model.Firestations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FirestationsRepository
        extends JpaRepository<Firestations, Long> {

    /**
     * Consult the database in search of the Firestations.
     * @param address : address in character string.
     * @return corresponding Firestations.
     */
    Firestations findByAddressAddressIgnoreCase(String address);

    /**
     * Consult the database to search for Firestations.
     * @param station : station in numbers.
     * @return corresponding firestations.
     */
    List<Firestations> findByStation(int station);
}
