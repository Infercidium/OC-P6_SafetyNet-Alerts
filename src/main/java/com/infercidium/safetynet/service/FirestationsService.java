package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.Firestations;
import com.infercidium.safetynet.repository.FirestationsRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

/**
 * Firestations Service develops public methods of interfaces,
 * and private methods.
 */
@Service
public class FirestationsService implements FirestationsI {

    /**
     * Instantiation of FirestationsRepository.
     */
    private final FirestationsRepository firestationsR;
    /**
     * Instantiation of AddressService.
     */
    private final AddressI addressI;

    /**
     * Class constructor.
     * @param firestationsRe this is FirestationsRepository.
     * @param addressIn this is AddressService.
     */
    public FirestationsService(final FirestationsRepository firestationsRe,
                               final AddressI addressIn) {
        this.firestationsR = firestationsRe;
        this.addressI = addressIn;
    }

    //Post, Put, Delete

    /**
     * Post Method Service.
     * @param address to add.
     * @param firestations to save.
     * @return firestations saved.
     */
    @Override
    public Firestations createMapage(final Address address,
                                     final Firestations firestations)
            throws SQLIntegrityConstraintViolationException {
        if (mapageCheck(address.getAddress(), firestations.getStation())) {
            throw new SQLIntegrityConstraintViolationException();
        }

        Address addressComplete = addressI.checkAddress(address);
        Firestations firestation;

        if (stationCheck(firestations.getStation())) {
            firestation = firestationsR
                    .findByStation(firestations.getStation());
        } else {
            firestation = firestations;
            firestation.getAddress().clear();
        }
        firestation.addAddress(addressComplete);
        return this.firestationsR.save(firestation);
    }

    /**
     * Edit Method Service.
     * @param address to check Firestations.
     * @param station to check Firestations.
     * @param firestations to edit.
     */
    @Override
    public void editFirestation(final String address,
                                final int station,
                                final Firestations firestations)
            throws SQLIntegrityConstraintViolationException {
        if (!mapageCheck(address, station)) {
            throw new NullPointerException();
        } else if (mapageCheck(address, firestations.getStation())) {
            throw new SQLIntegrityConstraintViolationException();
        }
        Address addressComplete = new Address(address);
        removeAddress(address, station);
        createMapage(addressComplete, firestations);
    }

    /**
     * Allows you to delete an address from a specific station.
     * @param address to delete.
     * @param station specified.
     */
    @Override
    public void removeAddress(final String address, final int station) {
        if (!addressCheck(address)) {
            throw new NullPointerException();
        }
        Address addressComplete = addressI.checkAddress(new Address(address));
        Firestations firestation = firestationsR
                .findByAddressAddressIgnoreCaseAndStation(address, station);
        firestation.removeAddress(addressComplete);
        if (firestation.getAddress().isEmpty()) {
            firestationsR.delete(firestation);
        } else {
            firestationsR.save(firestation);
        }
    }

    /**
     * Allows you to delete an entire station.
     * @param station to delete.
     */
    @Override
    public void removeStation(final int station) {
        if (!stationCheck(station)) {
            throw new NullPointerException();
        }
        Firestations firestation = getFirestationsStation(station);
        firestationsR.delete(firestation);
    }

    /**
     * Allows you to delete an address from all stations.
     * @param address to delete.
     */
    @Override
    public void removeAddress(final String address) {
        List<Firestations> firestationsList = getFirestationsAddress(address);
        for (Firestations firestation : firestationsList) {
            removeAddress(address, firestation.getStation());
        }
    }

    //Get

    /**
     * Get Method Service.
     * @param address to check Firestations.
     * @return firestations checked.
     */
    @Override
    public List<Firestations> getFirestationsAddress(final String address) {
        if (!addressCheck(address) && !address.equals("null")) {
            throw new NullPointerException();
        }
        List<Firestations> firestations;
        if (address.equals("null")) {
            firestations = firestationsR.findAll();
        } else {
             firestations = firestationsR
                     .findByAddressAddressIgnoreCase(address);
        }
        return firestations;
    }

    /**
     * Find a Firestation.
     * @param station number sought.
     * @return a Firestation.
     */
    @Override
    public Firestations getFirestationsStation(final int station) {
        if (!stationCheck(station)) {
            throw new NullPointerException();
        }
        return firestationsR.findByStation(station);
    }

    //Method Tiers

    /**
     * Check if an address and a station are linked.
     * @param address to verify.
     * @param station to verify.
     * @return True if existing otherwise false.
     */
    @Override
    public boolean mapageCheck(final String address, final int station) {
        Firestations firestation = firestationsR
                .findByAddressAddressIgnoreCaseAndStation(address, station);
        return firestation != null;
    }

    /**
     * Check if an address exists.
     * @param address to verify.
     * @return True if existing otherwise false.
     */
    @Override
    public boolean addressCheck(final String address) {
        List<Firestations> firestationsList
                = firestationsR.findByAddressAddressIgnoreCase(address);
        return !firestationsList.isEmpty();
    }

    /**
     * Check if a station exists.
     * @param station to verify.
     * @return True if existing otherwise false.
     */
    @Override
    public boolean stationCheck(final int station) {
        Firestations firestation = firestationsR.findByStation(station);
        return firestation != null;
    }
}
