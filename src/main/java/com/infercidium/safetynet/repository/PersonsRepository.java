package com.infercidium.safetynet.repository;

import com.infercidium.safetynet.model.Persons;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonsRepository extends JpaRepository<Persons, Long> {
    Optional<Persons> findByFirstNameIgnoreCaseAndLastNameIgnoreCase(
            String firstName, String lastName);
    List<Persons> findByCityIgnoreCase(String city);
}
