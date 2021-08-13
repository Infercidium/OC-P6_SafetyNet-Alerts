package com.infercidium.safetynet.repository;

import com.infercidium.safetynet.model.Name;
import com.infercidium.safetynet.model.Persons;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonsRepository extends JpaRepository<Persons, Name> {


    List<Persons> findByCityIgnoreCase(String city);
    List<Persons> findByAddressIgnoreCase(String address);
}
