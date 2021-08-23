package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.Firestations;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FirestationsI {
    //Post, Put, Delete
    Firestations createStationMap(Firestations firestations);
    void editStationMap(String address, Firestations firestations);
    void removeStation(int station);
    void removeAddress(String address);

    //Get
    List<Firestations> getAddress(String address);
    List<Firestations> getStation(int station);

    //URL lié à Persons
}
