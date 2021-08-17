package com.infercidium.safetynet.rest;

import com.infercidium.safetynet.model.Firestations;
import com.infercidium.safetynet.service.FirestationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class FirestationsRest {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(FirestationsRest.class);
    private final FirestationsService firestationsS;

    public FirestationsRest(final FirestationsService firestationsSe) {
        this.firestationsS = firestationsSe;
    }

    //Post, Put, Delete
    @PostMapping(value = "/firestation")
    public ResponseEntity<Void> createStationMap(
            @Valid @RequestBody final Firestations firestations) {
        ResponseEntity<Void> result
                = firestationsS.createStationMap(firestations);
        LOGGER.info("Saving " + firestations + " in the Firestations table");
        return result;
    }

    @PutMapping(value = "/firestation/{address}")
    public ResponseEntity<Void> editStationMap(
            @PathVariable final String address,
            @Valid @RequestBody final Firestations firestations) {
        ResponseEntity<Void> result
                = firestationsS.editStationMap(address, firestations);
        LOGGER.info(address + "mapping modification");
        return result;
    }

    @DeleteMapping(value = "/firestation/station/{station}")
    public ResponseEntity<Void> removeStation(@PathVariable final int station) {
        ResponseEntity<Void> result = firestationsS.removeStation(station);
        LOGGER.info("Deletion of station : "
                + station + " and its linked addresses");
        return result;
    }

    @DeleteMapping(value = "/firestation/address/{address}")
    public ResponseEntity<Void> removeAddress(
            @PathVariable final String address) {
        ResponseEntity<Void> result = firestationsS.removeAddress(address);
        LOGGER.info(address + " deletion");
        return result;
    }

    //Get
    @GetMapping(value = "/firestation/{address}")
    public MappingJacksonValue getAddress(
            @PathVariable final String address) {
        List<Firestations> firestations = firestationsS.getAddress(address);
        MappingJacksonValue firestationsFilter = firestationsS.firestationsFilterNull(firestations);
        LOGGER.info("Firestation found");
        return firestationsFilter;
    }

    @GetMapping(value = "/firestations")
    public MappingJacksonValue getStation(@RequestParam(required = false,
            defaultValue = "0") final int station) {
        List<Firestations> firestations = firestationsS.getStation(station);
        MappingJacksonValue firestationsFilter = firestationsS.firestationsFilterNull(firestations);
        LOGGER.info("List of Firestations displayed");
        return firestationsFilter;
    }

    //URL lié à Firestations
}
