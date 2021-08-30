package com.infercidium.safetynet.rest;

import com.infercidium.safetynet.dto.FirestationsDTO;
import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.dto.PersonsDTO;
import com.infercidium.safetynet.dto.StationNumberDTO;
import com.infercidium.safetynet.mapper.FirestationsMapper;
import com.infercidium.safetynet.model.Firestations;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.service.FirestationsService;
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
import java.util.List;
import java.util.Map;

@RestController
public class FirestationsRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FirestationsRest.class);
    private final FirestationsService firestationsS;
    private final FirestationsMapper firestationsM;

    public FirestationsRest(final FirestationsService firestationsSe,
                            final FirestationsMapper firestationsMa) {
        this.firestationsS = firestationsSe;
        this.firestationsM = firestationsMa;
    }

    //Post, Put, Delete
    @PostMapping(value = "/firestation")
    public ResponseEntity<Void> createStationMap(@Valid @RequestBody final FirestationsDTO firestationsDTO) {
        Firestations firestations = firestationsM.dtoToModel(firestationsDTO);
        Firestations postFirestation = firestationsS.postFirestation(firestations);

        URI locate = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{address}")
                .buildAndExpand(postFirestation.getAddress())
                .toUri();

        LOGGER.info("Saving " + firestations + " in the Firestations table");
        return ResponseEntity.created(locate).build();
    }

    @PutMapping(value = "/firestation/{address}")
    public ResponseEntity<Void> editStationMap(@PathVariable final String address, @RequestBody final FirestationsDTO firestationsDTO) {
        Firestations firestations = firestationsM.dtoToModel(firestationsDTO);
        firestationsS.editFirestation(address, firestations);
        LOGGER.info(address + " mapping modification");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/firestation/station/{station}")
    public ResponseEntity<Void> removeStation(@PathVariable final int station) {
        firestationsS.removeStationMapping(station);
        LOGGER.info("Deletion of station : " + station + " and its linked addresses");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/firestation/address/{address}")
    public ResponseEntity<Void> removeAddress(@PathVariable final String address) {
        firestationsS.removeAddressMapping(address);
        LOGGER.info(address + " deletion");
        return ResponseEntity.ok().build();
    }
    //Get
    @GetMapping(value = "/firestation/{address}")
    public FirestationsDTO getAddress(@PathVariable final String address) {
        Firestations firestations = firestationsS.getFirestationsAddress(address);
        FirestationsDTO firestationsDTO = firestationsM.modelToDto(firestations);
        LOGGER.info("Firestation found");
        return firestationsDTO;
    }

    @GetMapping(value = "/firestations")
    public List<FirestationsDTO> getStation(@RequestParam(required = false, defaultValue = "0") final int station) {
        List<Firestations> firestations = firestationsS.getFireStationsStation(station);
        List<FirestationsDTO> firestationsDTO = firestationsM.modelToDto(firestations);
        if (station > 0) {
            LOGGER.info("List of Firestations " + station + " displayed");
        } else {
            LOGGER.info("List of Firestations displayed");
        }
        return firestationsDTO;
    }
    //URL lié à Firestations

    @GetMapping(value = "/firestation")
    public Map<String, Object> getStationNumber(@RequestParam final int stationNumber) {
        if (stationNumber < 1) {
            throw new NullPointerException();
        }
        List<Firestations> firestations = firestationsS.getFireStationsStation(stationNumber);
        List<Persons> persons = firestationsS.getFirestationsListToPersonsList(firestations);
        List<StationNumberDTO> stationNumberDTO = firestationsM.personsModelToStationNumberDTO(persons);
        Map<String, Object> stationNumberResult = firestationsS.getStationNumberCount(stationNumberDTO);
        LOGGER.info("List of inhabitants covered by station " + stationNumber + " found");
        return stationNumberResult;
    }

    @GetMapping(value = "/phoneAlert")
    public List<PersonsDTO> getPhoneAlert(@RequestParam final int firestation) {
        if (firestation < 1) {
            throw new NullPointerException();
        }
        List<Firestations> firestationsList = firestationsS.getFireStationsStation(firestation);
        List<Persons> persons = firestationsS.getFirestationsListToPersonsList(firestationsList);
        List<PersonsDTO> personsDTO = firestationsS.personsToPersonsdtoPhone(persons);
        LOGGER.info("List of telephone numbers of residents served by the station " + firestation + " found");
        return personsDTO;
    }

    @GetMapping(value = "/fire")
    public Map<String, Object> getFire(@RequestParam final String address) {
        Firestations firestations = firestationsS.getFirestationsAddress(address);
        Integer station = firestations.getStation();
        List<Persons> persons = firestationsS.getFireResidents(address);
        List<MedicalRecords> medicalRecords = firestationsS.getFireMedicalRecords(persons);
        List<PersonsAndMedicalRecordsDTO> fireDTO = firestationsM.personsAndMedicalRecordsModelToPersonsAndMedicalRecordsDTO(medicalRecords);
        LOGGER.info("Station number and list of residents of " + address + " found");
        return firestationsS.getFireResult(station, fireDTO);
    }

    @GetMapping(value = "/flood/stations")
    public Map<String, Object> getFlood(@RequestParam final int station) {
        if (station < 1) {
            throw new NullPointerException();
        }
        List<Firestations> firestations = firestationsS.getFireStationsStation(station);
        List<FirestationsDTO> firestationsDTO = firestationsM.modelToDto(firestations);
        Map<String, List<Persons>> personsMap = firestationsS.getFloodResidents(firestationsDTO);
        Map<String, List<MedicalRecords>> medicalRecordsMap = firestationsS.getFloodMedicalRecords(personsMap);
        LOGGER.info("List of inhabitants by address served by station " + station + " found");
        return firestationsM.personsAndMedicalRecordsModelToFloodDTO(medicalRecordsMap);
    }
}
