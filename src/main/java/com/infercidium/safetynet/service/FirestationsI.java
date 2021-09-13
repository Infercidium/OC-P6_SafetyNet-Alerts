package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.Firestations;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

/**
 * Firestations interface to store public methods
 * and use them in different services.
 */
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

    /**
     * Allows you to delete an address from one to several stations.
     * @param address to delete.
     * @param station maybe specified.
     */
    void removeAddress(String address, int station);

    /**
     * Allows you to delete an entire station.
     * @param station to delete.
     */
    void removeStation(int station);

    /**
     * Allows you to delete an address from all stations.
     * @param address to delete.
     */
    void removeAddress(String address);

    //Get
    /**
     * Get Method Service.
     * @param address to check Firestations.
     * @return firestations checked.
     */
    List<Firestations> getFirestationsAddress(String address);

    /**
     * Find a Firestation.
     * @param station number sought.
     * @return a Firestation.
     */
    Firestations getFirestationsStation(int station);

    //Method Tiers
    /**
     * Check if an address and a station are linked.
     * @param address to verify.
     * @param station to verify.
     * @return True if existing otherwise false.
     */
    boolean mapageCheck(String address, int station);

    /**
     * Check if an address exists.
     * @param address to verify.
     * @return True if existing otherwise false.
     */
    boolean addressCheck(String address);

    /**
     * Check if a station exists.
     * @param station to verify.
     * @return True if existing otherwise false.
     */
    boolean stationCheck(int station);
}
