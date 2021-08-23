package com.infercidium.safetynet.rest;

import com.infercidium.safetynet.filter.FirestationsFilter;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestController
public class FirestationsRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FirestationsRest.class);
    private final FirestationsService firestationsS;
    private final FirestationsFilter firestationsF;

    public FirestationsRest(final FirestationsService firestationsSe, FirestationsFilter firestationsFi) {
        this.firestationsS = firestationsSe;
        this.firestationsF = firestationsFi;
    }

    //Post, Put, Delete
    @PostMapping(value = "/firestation")
    public ResponseEntity<Void> createStationMap(@Valid @RequestBody final Firestations firestations) {
        Firestations resultFirestation = firestationsS.createStationMap(firestations);

        URI locate = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{address}")
                .buildAndExpand(resultFirestation.getAddress())
                .toUri();

        LOGGER.info("Saving " + firestations + " in the Firestations table");
        return ResponseEntity.created(locate).build();
    }

    @PutMapping(value = "/firestation/{address}")
    public ResponseEntity<Void> editStationMap(@PathVariable final String address, @RequestBody final Firestations firestations) {
        firestationsS.editStationMap(address, firestations);
        LOGGER.info(address + "mapping modification");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/firestation/station/{station}")
    public ResponseEntity<Void> removeStation(@PathVariable final int station) {
        firestationsS.removeStation(station);
        LOGGER.info("Deletion of station : " + station + " and its linked addresses");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/firestation/address/{address}")
    public ResponseEntity<Void> removeAddress(@PathVariable final String address) {
        firestationsS.removeAddress(address);
        LOGGER.info(address + " deletion");
        return ResponseEntity.ok().build();
    }

    //Get
    @GetMapping(value = "/firestation/{address}")
    public MappingJacksonValue getAddress(@PathVariable final String address) {
        List<Firestations> firestations = firestationsS.getAddress(address);
        MappingJacksonValue firestationsFilter = firestationsF.firestationsNullFilter(firestations);
        LOGGER.info("Firestation found");
        return firestationsFilter;
    }

    @GetMapping(value = "/firestations")
    public MappingJacksonValue getStation(@RequestParam(required = false,
            defaultValue = "0") final int station) {
        List<Firestations> firestations = firestationsS.getStation(station);
        MappingJacksonValue firestationsFilter = firestationsF.firestationsNullFilter(firestations);
        LOGGER.info("List of Firestations displayed");
        return firestationsFilter;
    }

    //URL lié à Firestations
}
