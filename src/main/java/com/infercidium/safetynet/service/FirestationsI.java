package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.Firestations;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface FirestationsI {

    //Post, Put, delete
    /**
     * Post Method Service.
     * @param address to add.
     * @param firestations to save.
     * @return firestations saved.
     */
    Firestations createMapage(Address address,
                              Firestations firestations)
            throws SQLIntegrityConstraintViolationException;

    /**
     * Edit Method Service.
     * @param address to check Firestations.
     * @param station to check Firestations.
     * @param firestations to edit.
     */
    void editFirestation(String address, int station,
                         Firestations firestations)
            throws SQLIntegrityConstraintViolationException;

    void removeAddress(String address, int station);

    void removeStation(int station);

    void removeAddress(String address);

    //Get
    /**
     * Get Method Service.
     * @param address to check Firestations.
     * @return firestations checked.
     */
    List<Firestations> getFirestationsAddress(String address);

    Firestations getFirestationsStation(int station);

    //Method Tiers
    boolean mapageCheck(String address, int station);

    boolean addressCheck(String address);

    boolean stationCheck(int station);
}
