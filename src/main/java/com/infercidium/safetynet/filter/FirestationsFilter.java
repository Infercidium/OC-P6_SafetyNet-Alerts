package com.infercidium.safetynet.filter;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.infercidium.safetynet.model.Firestations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FirestationsFilter extends UtilityFilter implements FirestationsFilterI {

    private static final Logger LOGGER = LoggerFactory.getLogger(FirestationsFilter.class);
    private final String idName = "FirestationFilter";

    @Override
    public MappingJacksonValue firestationsFilterAdd(final List<Firestations> firestations, final Set<String> attribute) {
        SimpleBeanPropertyFilter firestationsFilter = SimpleBeanPropertyFilter.filterOutAllExcept(attribute);
        FilterProvider listFilter = new SimpleFilterProvider().addFilter(idName, firestationsFilter);
        MappingJacksonValue filterFirestations = new MappingJacksonValue(firestations);
        filterFirestations.setFilters(listFilter);
        LOGGER.debug("Applying the filter displaying : " + attribute);
        return filterFirestations;
    }

    @Override
    public MappingJacksonValue firestationsFilterRemove(final List<Firestations> firestations, final Set<String> attribute) {
        SimpleBeanPropertyFilter firestationsFilter = SimpleBeanPropertyFilter.serializeAllExcept(attribute);
        FilterProvider listFilter = new SimpleFilterProvider().addFilter(idName, firestationsFilter);
        MappingJacksonValue filterFirestations = new MappingJacksonValue(firestations);
        filterFirestations.setFilters(listFilter);
        LOGGER.debug("Applying the hiding filter : " + attribute);
        return filterFirestations;
    }

    @Override
    public MappingJacksonValue firestationsNullFilter(final List<Firestations> firestations) {
        Set<String> noul = new HashSet<>();
        SimpleBeanPropertyFilter firestationsFilter = SimpleBeanPropertyFilter.serializeAllExcept(noul);
        FilterProvider listFilter = new SimpleFilterProvider().addFilter(idName, firestationsFilter);
        MappingJacksonValue filterFirestations = new MappingJacksonValue(firestations);
        filterFirestations.setFilters(listFilter);
        LOGGER.debug("Applying the null filter");
        return filterFirestations;
    }
}
