package com.infercidium.safetynet.service;

import com.infercidium.safetynet.dto.FirestationsDTO;
import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.dto.PersonsDTO;
import com.infercidium.safetynet.dto.StationNumberDTO;
import com.infercidium.safetynet.model.Firestations;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;

import java.util.List;
import java.util.Map;

public interface FirestationsI {
    //Post, Put, Delete
    Firestations postFirestation(Firestations firestations);
    void editFirestation(String address, Firestations firestations);
    void removeStationMapping(int station);
    void removeAddressMapping(String address);

    //Get
    Firestations getFirestationsAddress(String address);
    List<Firestations> getFireStationsStation(int station);

    //URL lié à Persons
    List<Persons> getFirestationsListToPersonsList(List<Firestations> firestations);
    Map<String, Object> getStationNumberCount(List<StationNumberDTO> stationNumberDTO);
    List<PersonsDTO> personsToPersonsdtoPhone(List<Persons> persons);
    List<Persons> getFireResidents(String address);
    List<MedicalRecords> getFireMedicalRecords(List<Persons> persons);
    Map<String, Object> getFireResult(Integer station, List<PersonsAndMedicalRecordsDTO> fireDTO);
    Map<String, List<Persons>> getFloodResidents(List<FirestationsDTO> firestationsDTO);
    Map<String, List<MedicalRecords>> getFloodMedicalRecords(Map<String, List<Persons>> personsMap);
}
