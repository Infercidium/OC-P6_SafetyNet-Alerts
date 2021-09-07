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

    //Post, Put, Delete
    /**
     * Post Method Service.
     * @param firestations to save.
     * @return firestations saved.
     */
    Firestations postFirestation(Address address, Firestations firestations)
            throws SQLIntegrityConstraintViolationException;

    /**
     * Edit Method Service.
     * @param address to check Firestations.
     * @param station to check Firestations.
     * @param firestations to edit.
     */
    void editFirestation(String address, int station,
                         Firestations firestations);

    /**
     * RemoveStation Method Service.
     * @param station to check Firestations.
     */
    void removeStationMapping(int station);

    /**
     * RemoveAddress Method Service.
     * @param address to check Firestations.
     */
    void removeAddressMapping(String address);

    //Get
    /**
     * Get Method Service.
     * @param address to check Firestations.
     * @return firestations checked.
     */
    List<Firestations> getFirestationsAddress(String address);

    /**
     * GetAll Method Service.
     * @param station to check Firestations.
     * @return firestations checked.
     */
    List<Firestations> getFireStationsStation(int station);

    //URL lié à Persons
    /**
     * Convert the list of Firestations to the list of Persons.
     * @param firestations : list of Firestations used.
     * @return list of Persons.
     */
    /*List<Persons> getFirestationsListToPersonsList(
            List<Firestations> firestations);*/

    /**
     * Formatting the response for the StationNumber URL.
     * @param stationNumberDTO : list to count.
     * @return Map expected in result.
     */
    /*Map<String, Object> getStationNumberCount(
            List<StationNumberDTO> stationNumberDTO);*/

    /**
     *Extract the phone from the Persons list in PersonsDTO.
     * @param persons : list of Persons used.
     * @return list of phone.
     */
    //List<PersonsDTO> personsToPersonsdtoPhone(List<Persons> persons);

    /**
     * Relay method.
     * @param address to check Persons.
     * @return list of Persons checked.
     */
    //List<Persons> getFireResidents(String address);

    /**
     * Convert list of Persons to list of MedicalRecords.
     * @param persons : list to convert.
     * @return list Medicalrecords.
     */
    //List<MedicalRecords> getFireMedicalRecords(List<Persons> persons);

    /**
     * Formatting the response for the Fire URL.
     * @param station : station number.
     * @param fireDTO : list of inhabitants.
     * @return Map expected in result.
     */
    /*Map<String, Object> getFireResult(
            List<Integer> station,
            List<PersonsAndMedicalRecordsDTO> fireDTO);*/

    /**
     * Formatting step for the response for the Flood URL.
     * @param firestationsDTO : list of Firestations.
     * @return Map expected for final step of formatting.
     */
    /*Map<String, List<Persons>> getFloodResidents(
            List<FirestationsDTO> firestationsDTO);*/

    /**
     * Formatting the response for the Flood URL.
     * @param personsMap : Map obtained containing the inhabitants
     *                   in a list of address key.
     * @return Map expected in result.
     */
   /* Map<String, List<MedicalRecords>> getFloodMedicalRecords(
            Map<String, List<Persons>> personsMap);*/
}
