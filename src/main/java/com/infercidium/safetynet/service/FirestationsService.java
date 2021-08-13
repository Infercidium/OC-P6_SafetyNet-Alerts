package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.Firestations;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class FirestationsService implements FirestationsI{
    @Override
    public ResponseEntity<Void> createStationMap(Firestations firestations) {
        return null;
    }

    @Override
    public ResponseEntity<Void> editStationMap(String address) {
        return null;
    }

    @Override
    public ResponseEntity<Void> removeStation(int station) {
        return null;
    }

    @Override
    public ResponseEntity<Void> removeAddress(String address) {
        return null;
    }

    @Override
    public Firestations getAddress(String address) {
        return null;
    }

    @Override
    public List<Firestations> getStation(int station) {
        return null;
    }

    @Override
    public String stationNumberInfo(int station) {
        return null;
    }

    @Override
    public String phoneAlert(int station) {
        return null;
    }

    @Override
    public String floodStation(int station) {
        return null;
    }
}
