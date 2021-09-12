package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.Persons;

import java.util.List;
import java.util.Set;

/**
 * Persons interface to store public methods and use them in different services.
 */
public interface PersonsI {
    //Post, Put, Delete
    /**
     * Post Method Service.
     * @param persons to save.
     * @return persons saved.
     */
    Persons postPerson(Persons persons);

    /**
     * Edit Method Service.
     * @param firstName to check Persons.
     * @param lastName to check Persons.
     * @param persons to edit.
     */
    void editPerson(String firstName, String lastName, Persons persons);

    /**
     * Remove Method Service.
     * @param firstName to check Persons.
     * @param lastName to check Persons.
     */
    void removePerson(String firstName, String lastName);

    //Get
    /**
     * Get Method Service.
     * @param firstName to check Persons.
     * @param lastName to check Persons.
     * @return persons checked.
     */
    Persons getPersonName(String firstName, String lastName);

    /**
     * GetAll Method Service.
     * @return all Persons.
     */
    List<Persons> getPersons();

    //Methods Tiers
    /**
     * Check if Persons exists in the database.
     * @param firstName to check Persons.
     * @param lastName to check Persons.
     * @return True if exist or False if not.
     */
    boolean personCheck(String firstName, String lastName);

    //URL
    /**
     * Create a list of Persons from an Address Set.
     * @param addressSet contains the addresses of the residents.
     * @return the list of residents linked to the address.
     */
    List<Persons> addressSetToPersonsList(Set<Address> addressSet);
}
