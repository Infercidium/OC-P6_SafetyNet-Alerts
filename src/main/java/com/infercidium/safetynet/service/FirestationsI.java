package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.Firestations;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FirestationsI {
    //Post, Put, Delete
    ResponseEntity<Void> createStationMap(Firestations firestations);
    ResponseEntity<Void> editStationMap(String address,
                                        Firestations firestations);
    ResponseEntity<Void> removeAddress(String address);
    ResponseEntity<Void> removeStation(int station);

    //Get
    List<Firestations> getAddress(String address);
    List<Firestations> getStation(int station);

    //URL lié à Persons
}
