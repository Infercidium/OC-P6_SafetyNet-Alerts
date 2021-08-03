package com.infercidium.safetynet.repository;

import com.infercidium.safetynet.model.Persons;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonsRepository extends JpaRepository<Persons, Long> {


    List<Persons> findByCity(String city);

    List<Persons> findByFirstNameAndLastName(String firstName, String lastName);
}
