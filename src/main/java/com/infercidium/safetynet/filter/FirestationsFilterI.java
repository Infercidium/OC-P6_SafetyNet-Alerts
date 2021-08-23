package com.infercidium.safetynet.filter;

import com.infercidium.safetynet.model.Firestations;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.util.List;
import java.util.Set;

public interface FirestationsFilterI {
    MappingJacksonValue firestationsFilterAdd(List<Firestations> firestations, Set<String> attribute);
    MappingJacksonValue firestationsFilterRemove(List<Firestations> firestations, Set<String> attribute);
    MappingJacksonValue firestationsNullFilter(List<Firestations> firestations);
}
