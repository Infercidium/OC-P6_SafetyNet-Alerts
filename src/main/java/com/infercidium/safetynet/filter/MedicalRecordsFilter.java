package com.infercidium.safetynet.filter;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.infercidium.safetynet.model.MedicalRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class MedicalRecordsFilter extends UtilityFilter implements MedicalRecordsFilterI {

    private static final Logger LOGGER = LoggerFactory.getLogger(MedicalRecordsFilter.class);
    private final String idName = "MedicalRecordFilter";

    @Override
    public MappingJacksonValue medicalRecordsFilterAdd(final List<MedicalRecords> medicalRecords, final Set<String> attribute) {
        SimpleBeanPropertyFilter medicalRecordsFilter = SimpleBeanPropertyFilter.filterOutAllExcept(attribute);
        FilterProvider listFilter = new SimpleFilterProvider().addFilter(idName, medicalRecordsFilter);
        MappingJacksonValue filterMedicalRecords = new MappingJacksonValue(medicalRecords);
        filterMedicalRecords.setFilters(listFilter);
        LOGGER.debug("Applying the filter displaying : " + attribute);
        return filterMedicalRecords;
    }

    @Override
    public MappingJacksonValue medicalRecordsFilterRemove(final List<MedicalRecords> medicalRecords, final Set<String> attribute) {
        SimpleBeanPropertyFilter medicalRecordsFilter = SimpleBeanPropertyFilter.serializeAllExcept(attribute);
        FilterProvider listFilter = new SimpleFilterProvider().addFilter(idName, medicalRecordsFilter);
        MappingJacksonValue filterMedicalRecords = new MappingJacksonValue(medicalRecords);
        filterMedicalRecords.setFilters(listFilter);
        LOGGER.debug("Applying the hiding filter : " + attribute);
        return filterMedicalRecords;
    }

    @Override
    public MappingJacksonValue medicalRecordsNullFilter(final List<MedicalRecords> medicalRecords) {
        Set<String> noul = newFilterSet("persons");
        SimpleBeanPropertyFilter medicalRecordsFilter = SimpleBeanPropertyFilter.serializeAllExcept(noul);
        FilterProvider listFilter = new SimpleFilterProvider().addFilter(idName, medicalRecordsFilter);
        MappingJacksonValue filterMedicalRecords = new MappingJacksonValue(medicalRecords);
        filterMedicalRecords.setFilters(listFilter);
        LOGGER.debug("Applying the null filter");
        return filterMedicalRecords;
    }
}
