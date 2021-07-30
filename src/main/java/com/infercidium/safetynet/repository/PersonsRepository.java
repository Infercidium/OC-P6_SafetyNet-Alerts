package com.infercidium.safetynet.repository;

import com.infercidium.safetynet.model.Persons;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonsRepository extends JpaRepository<Persons, Long> {

}
