package com.infercidium.safetynet.rest;

import com.infercidium.safetynet.dto.*;
import com.infercidium.safetynet.mapper.FirestationsMapper;
import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.Firestations;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.service.FirestationsI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class FirestationsRest {

    /**
     * Instantiation of LOGGER in order to inform in console.
     */
    private static final Logger LOGGER
            = LoggerFactory.getLogger(FirestationsRest.class);

    /**
     * Instantiation of FirestationsService.
     */
    private final FirestationsI firestationsS;

    /**
     * Instantiation of FirestationsMapper.
     */
    private final FirestationsMapper firestationsM;

    /**
     * Class constructor.
     * @param firestationsSe this is FirestationsService.
     * @param firestationsMa this is FirestationsMapper.
     */
    public FirestationsRest(final FirestationsI firestationsSe,
                            final FirestationsMapper firestationsMa) {
        this.firestationsS = firestationsSe;
        this.firestationsM = firestationsMa;
    }

    //Post, Put, Delete

    /**
     * Endpoint allowing to post a Firestation.
     * @param firestationsAddressDTO this is the information entered by the user.
     * @return 201 Created if successful,
     * 409 conflict if already exist or 400 bad request if bad field.
     */
    @PostMapping(value = "/firestation")
    public ResponseEntity<Void> createStationMap(@Valid @RequestBody final FirestationsAddressDTO firestationsAddressDTO) throws SQLIntegrityConstraintViolationException {
        FirestationsDTO firestationsDTO = new FirestationsDTO(firestationsAddressDTO);
        Firestations firestations = firestationsM.dtoToModel(firestationsDTO);
        Address address = new Address(firestationsAddressDTO.getAddress());
        Firestations postFirestation = firestationsS.postFirestation(address, firestations);

        URI locate = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{address}")
                .buildAndExpand(postFirestation.getAddress())
                .toUri();

        LOGGER.info("Saving " + firestations + " in the Firestations table");
        return ResponseEntity.created(locate).build();
    }

    /**
     * Endpoint allowing to post a Firestation.
     * @param address : allows you to find the resource in the database.
     * @param station allows you to find the ressource in the database.
     * @param firestationsDTO this is the information entered by the user.
     * @return 200 Ok if successful,
     * 404 not found if non-existent or 400 bad request if bad field.
     */
    @PutMapping(value = "/firestation/{address}&{station}")
    public ResponseEntity<Void> editStationMap(
            @PathVariable final String address,
            @PathVariable final int station,
            @RequestBody final FirestationsDTO firestationsDTO) {
        Firestations firestations = firestationsM.dtoToModel(firestationsDTO);
        firestationsS.editFirestation(address, station, firestations);
        LOGGER.info(address + " mapping modification");
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint allowing to delete Firestations.
     * @param station : allows you to find the resources in the database.
     * @return 200 Ok if successful, 404 not found if it does not exist.
     */
    @DeleteMapping(value = "/firestation/station/{station}")
    public ResponseEntity<Void> removeStation(@PathVariable final int station) {
        firestationsS.removeStationMapping(station);
        LOGGER.info("Deletion of station : "
                + station + " and its linked addresses");
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint allowing to delete a Firestation.
     * @param address : allows you to find the resource in the database.
     * @return 200 Ok if successful, 404 not found if it does not exist.
     */
    @DeleteMapping(value = "/firestation/address/{address}")
    public ResponseEntity<Void> removeAddress(
            @PathVariable final String address) {
        firestationsS.removeAddressMapping(address);
        LOGGER.info(address + " deletion");
        return ResponseEntity.ok().build();
    }
    //Get

    /**
     * Endpoint to get Firestation.
     * @param address : allows you to find the resource in the database.
     * @return firestation and 200 Ok if successful
     * or 404 not found if it does not exist.
     */
    @GetMapping(value = "/firestation/{address}")
    public List<FirestationsDTO> getAddress(
            @PathVariable final String address) {
        List<Firestations> firestations
                = firestationsS.getFirestationsAddress(address);
        List<FirestationsDTO> firestationsDTO
                = firestationsM.modelToDto(firestations);
        LOGGER.info("Firestation found");
        return firestationsDTO;
    }

    /**
     * Endpoint to get Firestations.
     * @param station : allows you to find the resources in the database.
     *                If default value (0), all the stations are called.
     * @return List of firestation and 200 Ok if successful
     * or 404 not found if it does not exist.
     */
    @GetMapping(value = "/firestations")
    public List<FirestationsDTO> getStation(
            @RequestParam(required = false, defaultValue = "0")
            final int station) {
        List<Firestations> firestations
                = firestationsS.getFireStationsStation(station);
        List<FirestationsDTO> firestationsDTO
                = firestationsM.modelToDto(firestations);
        if (station > 0) {
            LOGGER.info("List of Firestations " + station + " displayed");
        } else {
            LOGGER.info("List of Firestations displayed");
        }
        return firestationsDTO;
    }
    //URL lié à Firestations

    /**
     * This url returns a map containing the number of adults,
     * the number of children and the list of residents
     * covered by the Firestation.
     * @param stationNumber : allows you to find the resource in the database.
     * @return a Map and 200 Ok if successful
     * or 404 not found if it does not exist.
     */
    /*@GetMapping(value = "/firestation")
    public Map<String, Object> getStationNumber(
            @RequestParam final int stationNumber) {
        if (stationNumber < 1) {
            throw new NullPointerException();
        }
        List<Firestations> firestations
                = firestationsS.getFireStationsStation(stationNumber);
        List<Persons> persons
                = firestationsS.getFirestationsListToPersonsList(firestations);
        List<StationNumberDTO> stationNumberDTO
                = firestationsM.personsModelToStationNumberDTO(persons);
        Map<String, Object> stationNumberResult
                = firestationsS.getStationNumberCount(stationNumberDTO);
        LOGGER.info("List of inhabitants covered by station "
                + stationNumber + " found");
        return stationNumberResult;
    }*/

    /**
     * This url refers to the list of telephone numbers of residents
     * covered by the Firestation.
     * @param firestation : allows you to find the resource in the database.
     * @return a list and 200 Ok if successful
     * or 404 not found if it does not exist.
     */
    /*@GetMapping(value = "/phoneAlert")
    public List<PersonsDTO> getPhoneAlert(@RequestParam final int firestation) {
        if (firestation < 1) {
            throw new NullPointerException();
        }
        List<Firestations> firestationsList
                = firestationsS.getFireStationsStation(firestation);
        List<Persons> persons
                = firestationsS
                .getFirestationsListToPersonsList(firestationsList);
        List<PersonsDTO> personsDTO
                = firestationsS.personsToPersonsdtoPhone(persons);
        LOGGER.info("List of telephone numbers of residents "
                + "served by the station " + firestation + " found");
        return personsDTO;
    }*/

    /**
     * This url asks for an address
     * and returns a map containing the station covering the address
     * and a list of people living at this address as well as their information.
     * @param address : allows you to find the resource in the database.
     * @return a map and 200 Ok if successful
     * or 404 not found if it does not exist.
     */
    /*@GetMapping(value = "/fire")
    public Map<String, Object> getFire(@RequestParam final String address) {
        List<Firestations> firestations
                = firestationsS.getFirestationsAddress(address);
        List<Integer> station = new ArrayList<>();
        for (Firestations firestation : firestations) {
            station.add(firestation.getStation());
        }
        List<Persons> persons = firestationsS.getFireResidents(address);
        List<MedicalRecords> medicalRecords
                = firestationsS.getFireMedicalRecords(persons);
        List<PersonsAndMedicalRecordsDTO> fireDTO
                = firestationsM
                .personsAndMedicalRecordsModelToPersonsAndMedicalRecordsDTO(
                        medicalRecords);
        LOGGER.info("Station number and list of residents of "
                + address + " found");
        return firestationsS.getFireResult(station, fireDTO);
    }*/

    /**
     * This url reviews a list of inhabitants
     * by address covered by the Firestation.
     * @param station : allows you to find the resource in the database.
     * @return a map and 200 Ok if successful
     * or 404 not found if it does not exist.
     */
    /*@GetMapping(value = "/flood/stations")
    public Map<String, Object> getFlood(@RequestParam final int station) {
        if (station < 1) {
            throw new NullPointerException();
        }
        List<Firestations> firestations
                = firestationsS.getFireStationsStation(station);
        List<FirestationsDTO> firestationsDTO
                = firestationsM.modelToDto(firestations);
        Map<String, List<Persons>> personsMap
                = firestationsS.getFloodResidents(firestationsDTO);
        Map<String, List<MedicalRecords>> medicalRecordsMap
                = firestationsS.getFloodMedicalRecords(personsMap);
        LOGGER.info("List of inhabitants by address served by station "
                + station + " found");
        return firestationsM
                .personsAndMedicalRecordsModelToFloodDTO(medicalRecordsMap);
    }*/
}
