package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.Firestations;
import com.infercidium.safetynet.repository.FirestationsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FirestationsService implements FirestationsI {

    private static final Logger LOGGER = LoggerFactory.getLogger(FirestationsService.class);
    private final FirestationsRepository firestationsR;
    public FirestationsService(final FirestationsRepository firestationsRe) {
        this.firestationsR = firestationsRe;
    }

    //Post, Put, Delete
    @Override
    public Firestations createStationMap(final Firestations firestations) {
        Firestations postFirestation = this.firestationsR.save(firestations);
        return postFirestation;
}

    @Override
    public void editStationMap(final String address, final Firestations firestations) {
        List<Firestations> basicFirestation = getAddress(address);
        Firestations firestationChanged = firestations;
        firestationChanged.setId(basicFirestation.get(0).getId());
        firestationChanged.setAddress(basicFirestation.get(0).getAddress());
        this.firestationsR.save(firestationChanged);
    }

    @Override
    public void removeStation(final int station) {
        List<Firestations> firestations = getStation(station);
        this.firestationsR.deleteAll(firestations);
    }

    @Override
    public void removeAddress(final String address) {
        List<Firestations> firestations = getAddress(address);
        this.firestationsR.delete(firestations.get(0));
    }

    //Get
    @Override
    public List<Firestations> getAddress(final String address) {
        Optional<Firestations> firestations = this.firestationsR.findByAddressIgnoreCase(address);
        if (firestations.isPresent()) {
            List<Firestations> firestationsList = new ArrayList<>();
            firestationsList.add(firestations.get());
            return firestationsList;
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public List<Firestations> getStation(final int station) {
        List<Firestations> firestations;
        if (station == 0) {
            firestations = this.firestationsR.findAll();
        } else {
            firestations = this.firestationsR.findByStation(station);
        }
        if (firestations.size() != 0) {
            return firestations;
        } else {
            throw new NullPointerException();
        }
    }
    //URL lié à Persons
}
