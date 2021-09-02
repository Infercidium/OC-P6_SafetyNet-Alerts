package com.infercidium.safetynet.repository;

import com.infercidium.safetynet.model.Persons;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonsRepository extends JpaRepository<Persons, Long> {

    /**
     * Consult the database in search of Persons.
     * @param firstName : firstname as a string.
     * @param lastName : lastname as a string.
     * @return corresponding persons.
     */
    Persons findByFirstNameIgnoreCaseAndLastNameIgnoreCase(
            String firstName, String lastName);

    /**
     * Consult the database to search for Persons.
     * @param address : address in character string.
     * @return corresponding Persons.
     */
    List<Persons> findByAddressAddressIgnoreCase(String address);

    /**
     * Consult the database to search for Persons.
     * @param city : city int character String.
     * @return corresponding Persons.
     */
    List<Persons> findByCityIgnoreCase(String city);
}
