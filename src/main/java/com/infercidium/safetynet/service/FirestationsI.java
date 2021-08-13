package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.Firestations;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FirestationsI {
    //Post, Put, Delete, Get
    ResponseEntity<Void> createStationMap(Firestations firestations);
    ResponseEntity<Void> editStationMap(String address);
    ResponseEntity<Void> removeStation(int station);
    ResponseEntity<Void> removeAddress(String address);
    Firestations getAddress(String address);
    List<Firestations> getStation(int station);

    //URL lié à Firestations
    String stationNumberInfo(int station);
    String phoneAlert(int station);
    String floodStation(int station);

}
