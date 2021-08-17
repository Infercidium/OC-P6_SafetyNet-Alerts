package com.infercidium.safetynet.service;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.infercidium.safetynet.model.Firestations;
import com.infercidium.safetynet.repository.FirestationsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FirestationsService implements FirestationsI {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(FirestationsService.class);
    private final FirestationsRepository firestationsR;
    public FirestationsService(final FirestationsRepository firestationsRe) {
        this.firestationsR = firestationsRe;
    }

    //Post, Put, Delete
    @Override
    public ResponseEntity<Void> createStationMap(
            final Firestations firestations) {
    Firestations verify = this.firestationsR.save(firestations);

    URI locate = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{address}")
            .buildAndExpand(verify.getAddress())
            .toUri();
            return ResponseEntity.created(locate).build();
}

    @Override
    public ResponseEntity<Void> editStationMap(
            final String address,
            final Firestations firestations) {
        List<Firestations> basicFirestation = getAddress(address);
        Firestations firestationChanged = firestations;
        firestationChanged.setId(basicFirestation.get(0).getId());
        firestationChanged.setAddress(basicFirestation.get(0).getAddress());
        this.firestationsR.save(firestationChanged);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> removeAddress(final String address) {
        List<Firestations> firestations = getAddress(address);
        this.firestationsR.delete(firestations.get(0));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> removeStation(final int station) {
        List<Firestations> firestations = getStation(station);
        this.firestationsR.deleteAll(firestations);
        return ResponseEntity.ok().build();
    }

    //Get
    @Override
    public List<Firestations> getAddress(final String address) {
        Optional<Firestations> firestations
                = this.firestationsR.findByAddressIgnoreCase(address);
        if (firestations.isPresent()) {
            List<Firestations> firestationsList = new ArrayList<>();
            firestationsList.add(firestations.get());
            return firestationsList;
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public List<Firestations> getStation(final int station) {
        List<Firestations> firestations;
        if (station == 0) {
            firestations = this.firestationsR.findAll();
        } else {
            firestations = this.firestationsR.findByStation(station);
        }
        if (firestations.size() != 0) {
            return firestations;
        } else {
            throw new NullPointerException();
        }
    }
    //URL lié à Persons

    //Méthode tiers
    private MappingJacksonValue firestationFilterAdd(
            final List<Firestations> firestations,
            final Set<String> attribute) {
        SimpleBeanPropertyFilter firestationFilter
                = SimpleBeanPropertyFilter.filterOutAllExcept(attribute);
        FilterProvider listFilter = new SimpleFilterProvider()
                .addFilter("FirestationFilter", firestationFilter);
        MappingJacksonValue filterMedicalRecords
                = new MappingJacksonValue(firestations);
        filterMedicalRecords.setFilters(listFilter);
        LOGGER.debug("Applying filters " + attribute);
        return filterMedicalRecords;
    }

    public MappingJacksonValue firestationsFilterNull(
            final List<Firestations> firestations) {
        Set<String> nul = new HashSet<>();
        SimpleBeanPropertyFilter firestationsFilter
                = SimpleBeanPropertyFilter.serializeAllExcept(nul);
        FilterProvider listFilter = new SimpleFilterProvider()
                .addFilter("FirestationFilter", firestationsFilter);
        MappingJacksonValue filterFirestations
                = new MappingJacksonValue(firestations);
        filterFirestations.setFilters(listFilter);
        return filterFirestations;
    }
}
