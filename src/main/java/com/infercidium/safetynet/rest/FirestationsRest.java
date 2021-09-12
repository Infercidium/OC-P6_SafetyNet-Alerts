package com.infercidium.safetynet.rest;

import com.infercidium.safetynet.dto.FirestationsAddressDTO;
import com.infercidium.safetynet.dto.FirestationsDTO;
import com.infercidium.safetynet.mapper.FirestationsMapper;
import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.Firestations;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

/**
 * Contains URLs linked to Firestations.
 */
@RestController
@RequestMapping(value = "/firestation")
public class FirestationsRest {

    /**
     * Instantiation of LOGGER in order to inform in console.
     */
    private static final Logger LOGGER
            = LoggerFactory.getLogger(FirestationsRest.class);

    /**
     * Instantiation of FirestationsService.
     */
    private final FirestationsI firestationsI;

    /**
     * Instantiation of FirestationsMapper.
     */
    private final FirestationsMapper firestationsM;

    /**
     * Class constructor.
     * @param firestationsIn this is FirestationsService.
     * @param firestationsMa this is FirestationsMapper.
     */
    public FirestationsRest(final FirestationsI firestationsIn,
                            final FirestationsMapper firestationsMa) {
        this.firestationsI = firestationsIn;
        this.firestationsM = firestationsMa;
    }

    //Post, Put, Delete

    /**
     * Endpoint allowing to post a Firestation.
     * @param firestationsAddressDTO this is the information
     *                               entered by the user.
     * @return 201 Created if successful,
     * 409 conflict if already exist or 400 bad request if bad field.
     */
    @PostMapping
    public ResponseEntity<Void> createStationMap(
            @Valid
            @RequestBody final FirestationsAddressDTO firestationsAddressDTO)
            throws SQLIntegrityConstraintViolationException {
        FirestationsDTO firestationsDTO
                = new FirestationsDTO(firestationsAddressDTO);
        Firestations firestations = firestationsM.dtoToModel(firestationsDTO);
        Address address = new Address(firestationsAddressDTO.getAddress());

        Firestations postFirestation
                = firestationsI.createMapage(address, firestations);
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
     * @param station : allows you to find the ressource in the database.
     * @param firestationsAddressDTO : changes to be made.
     * @return 200 Ok if successful,
     * 404 not found if non-existent or 400 bad request if bad field.
     */
    @PutMapping(value = "/{address}&{station}")
    public ResponseEntity<Void> editStationMap(
            @PathVariable final String address,
            @PathVariable final int station,
            @RequestBody final FirestationsAddressDTO firestationsAddressDTO)
            throws SQLIntegrityConstraintViolationException {
        FirestationsDTO firestationsDTO
                = new FirestationsDTO(firestationsAddressDTO);
        Firestations firestations = firestationsM.dtoToModel(firestationsDTO);

        firestationsI.editFirestation(address, station, firestations);
        LOGGER.info(address + " mapping modification");
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint allowing to delete Firestations.
     * @param station : allows you to find the resources in the database.
     * @return 200 Ok if successful, 404 not found if it does not exist.
     */
    @DeleteMapping(value = "/station/{station}")
    public ResponseEntity<Void> removeStation(@PathVariable final int station) {
        firestationsI.removeStation(station);
        LOGGER.info("Deletion of station : "
                + station + " and its linked addresses");
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint allowing to delete a Firestation.
     * @param address : allows you to find the resource in the database.
     * @param station : allows you to specify a precise mapping.
     * @return 200 Ok if successful, 404 not found if it does not exist.
     */
    @DeleteMapping(value = "/address/{address}")
    public ResponseEntity<Void> removeAddress(
            @PathVariable final String address,
            @RequestParam(required = false, defaultValue = "0")
            final int station) {
        if (station == 0) {
            firestationsI.removeAddress(address);
            LOGGER.info(address + " deletion");
        } else {
            firestationsI.removeAddress(address, station);
            LOGGER.info("Mapping " + address + " and " + station + " delete");
        }
        return ResponseEntity.ok().build();
    }
    //Get

    /**
     * Endpoint to get Firestation.
     * @param address : allows you to find the resource in the database.
     * @return firestation and 200 Ok if successful
     * or 404 not found if it does not exist.
     */
    @GetMapping(value = "/")
    public List<FirestationsDTO> getAddress(
            @RequestParam(required = false, defaultValue = "null")
            final String address) {
        List<Firestations> firestations
                = firestationsI.getFirestationsAddress(address);
        List<FirestationsDTO> firestationsDTO
                = firestationsM.modelToDto(firestations);
        LOGGER.info("Firestations found");
        return firestationsDTO;
    }

    /**
     * Endpoint to get Firestations.
     * @param station : allows you to find the resources in the database.
     *                If default value (0), all the stations are called.
     * @return List of firestation and 200 Ok if successful
     * or 404 not found if it does not exist.
     */
    @GetMapping(value = "/{station}")
    public FirestationsDTO getStation(@PathVariable final int station) {
        Firestations firestations
                = firestationsI.getFirestationsStation(station);
        return firestationsM.modelToDto(firestations);
    }
}
