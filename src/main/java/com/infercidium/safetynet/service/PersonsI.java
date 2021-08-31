package com.infercidium.safetynet.service;

import com.infercidium.safetynet.dto.PersonsDTO;
import com.infercidium.safetynet.model.Persons;

import java.util.List;

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

    //URL lié à Persons
    /**
     * Finds people living at the address.
     * @param address to check Persons.
     * @return list of Persons.
     */
    List<Persons> getPersonsAddress(String address);

    /**
     * Find the people living in the city.
     * @param city to check Persons.
     * @return list of Persons.
     */
    List<Persons> getPersonsCity(String city);

    /**
     * Extract the email from the Persons list in PersonsDTO.
     * @param persons : list of Persons used.
     * @return list of Email.
     */
    List<PersonsDTO> personsToPersonsdtoEmail(List<Persons> persons);

    //Methods Tiers
    /**
     * Check if Persons exists in the database.
     * @param firstName to check Persons.
     * @param lastName to check Persons.
     * @return True if exist or False if not.
     */
    boolean personCheck(String firstName, String lastName);
}
