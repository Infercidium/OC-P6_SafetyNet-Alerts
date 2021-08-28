package com.infercidium.safetynet.mapper;

import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.dto.FirestationsDTO;
import com.infercidium.safetynet.dto.StationNumberDTO;
import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.Firestations;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public abstract class FirestationsMapper {
    public abstract Firestations dtoToModel(FirestationsDTO firestationsDTO);
    public abstract Address map(String address);
    public abstract FirestationsDTO modelToDto(Firestations firestations);
    public abstract List<FirestationsDTO> modelToDto(List<Firestations> firestationsList);
    public String map(final Address address) {
        return address.getAddress();
    }

    //URL
    public abstract List<StationNumberDTO> personsModelToStationNumberDTO(List<Persons> persons);

    @Mappings({@Mapping(source = "medicalRecords.persons.firstName", target = "firstName"),
            @Mapping(source = "medicalRecords.persons.lastName", target = "lastName"),
            @Mapping(source = "medicalRecords.persons.phone", target = "phone"),
            @Mapping(source = "medicalRecords.age", target = "age"),
            @Mapping(source = "medicalRecords.medications", target = "medications"),
            @Mapping(source = "medicalRecords.allergies", target = "allergies")})
    public abstract List<PersonsAndMedicalRecordsDTO> personsAndMedicalRecordsModelToChildAlertAndFireDTO(List<MedicalRecords> medicalRecords);

    public Map<String, Object> personsAndMedicalRecordsModelToFloodDTO(final Map<String, List<MedicalRecords>> medicalRecordsMap) {
        Map<String, Object> flood = new HashMap<>();
        for (Map.Entry<String, List<MedicalRecords>> medicalRecordsList : medicalRecordsMap.entrySet()) {
            List<PersonsAndMedicalRecordsDTO> floodDTO = personsAndMedicalRecordsModelToChildAlertAndFireDTO(medicalRecordsList.getValue());
            flood.put(medicalRecordsList.getKey(), floodDTO);
        }
        return flood;
    }
}
