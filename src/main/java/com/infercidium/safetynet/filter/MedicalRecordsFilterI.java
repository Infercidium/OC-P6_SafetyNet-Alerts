package com.infercidium.safetynet.filter;

import com.infercidium.safetynet.model.MedicalRecords;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.util.List;
import java.util.Set;

public interface MedicalRecordsFilterI {
    MappingJacksonValue medicalRecordsFilterAdd(List<MedicalRecords> medicalRecords, Set<String> attribute);
    MappingJacksonValue medicalRecordsFilterRemove(List<MedicalRecords> medicalRecords, Set<String> attribute);
    MappingJacksonValue medicalRecordsNullFilter(List<MedicalRecords> medicalRecords);
}
