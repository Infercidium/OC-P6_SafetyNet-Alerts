package com.infercidium.safetynet.filter;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.infercidium.safetynet.model.Persons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PersonsFilter extends UtilityFilter implements PersonsFilterI {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonsFilter.class);
    private final String idName = "PersonFilter";

    @Override
    public MappingJacksonValue personsFilterAdd(final List<Persons> persons, final Set<String> attribute) {
        SimpleBeanPropertyFilter personsFilter = SimpleBeanPropertyFilter.filterOutAllExcept(attribute);
        FilterProvider listFilter = new SimpleFilterProvider().addFilter(idName, personsFilter);
        MappingJacksonValue filterPersons = new MappingJacksonValue(persons);
        filterPersons.setFilters(listFilter);
        LOGGER.debug("Applying the filter displaying : " + attribute);
        return filterPersons;
    }

    @Override
    public MappingJacksonValue personsFilterRemove(final List<Persons> persons, final Set<String> attribute) {
        SimpleBeanPropertyFilter personsFilter = SimpleBeanPropertyFilter.serializeAllExcept(attribute);
        FilterProvider listFilter = new SimpleFilterProvider().addFilter(idName, personsFilter);
        MappingJacksonValue filterPersons = new MappingJacksonValue(persons);
        filterPersons.setFilters(listFilter);
        LOGGER.debug("Applying the hiding filter : " + attribute);
        return filterPersons;
    }

    @Override
    public MappingJacksonValue personsNullFilter(final List<Persons> persons) {
        Set<String> noul = new HashSet<>();
        SimpleBeanPropertyFilter personsFilter = SimpleBeanPropertyFilter.serializeAllExcept(noul);
        FilterProvider listFilter = new SimpleFilterProvider().addFilter(idName, personsFilter);
        MappingJacksonValue filterPersons = new MappingJacksonValue(persons);
        filterPersons.setFilters(listFilter);
        LOGGER.debug("Applying the null filter");
        return filterPersons;
    }
}
