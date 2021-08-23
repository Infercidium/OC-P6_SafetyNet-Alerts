package com.infercidium.safetynet.filter;

import com.infercidium.safetynet.model.Persons;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.util.List;
import java.util.Set;

public interface PersonsFilterI {
    MappingJacksonValue personsFilterAdd(List<Persons> persons, Set<String> attribute);
    MappingJacksonValue personsFilterRemove(List<Persons> persons, Set<String> attribute);
    MappingJacksonValue personsNullFilter(List<Persons> persons);
}
