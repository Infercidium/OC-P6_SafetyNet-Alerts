package com.infercidium.safetynet.service;

import com.infercidium.safetynet.dto.FirestationsDTO;
import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.dto.PersonsDTO;
import com.infercidium.safetynet.dto.StationNumberDTO;
import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.Firestations;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;

public interface FirestationsI {

    /*public Firestations postFirestation(final Address address, final Firestations firestations) throws SQLIntegrityConstraintViolationException {
            Address addressComplete = addressS.checkAddress(address);
            List<Firestations> firestationsList = firestationsR.findByStation(firestations.getStation());
            Firestations firestation;
            if (firestationsList.isEmpty()) {
                firestation = firestations;
                firestation.getAddress().clear();
            } else if (firestationsList.get(0).getAddress().contains(addressComplete)) {
                throw new SQLIntegrityConstraintViolationException();
            } else {
                firestation = firestationsList.get(0);
            }
            firestation.addAddress(addressComplete);
            return this.firestationsR.save(firestation);
        }*/

    Firestations createMapage(Address address, Firestations firestations);

    void editFirestation(String address, int station, Firestations firestations);

    void removeMapage(String address, int station);

    void removeStation(int station);

    void removeAddress(String address);

    List<Firestations> getFirestationsAddress(String address);

    Firestations getFirestationsStation(int station);

    boolean mapageCheck(String address, int station);

    boolean addressCheck(String address);

    boolean stationCheck(int station);
}
