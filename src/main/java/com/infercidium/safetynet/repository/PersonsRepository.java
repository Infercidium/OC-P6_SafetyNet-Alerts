package com.infercidium.safetynet.repository;

import com.infercidium.safetynet.model.Persons;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonsRepository extends JpaRepository<Persons, Long> {
    Persons findByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);
    List<Persons> findByAddressAddressIgnoreCase(String address);
    List<Persons> findByCityIgnoreCase(String city);
}
