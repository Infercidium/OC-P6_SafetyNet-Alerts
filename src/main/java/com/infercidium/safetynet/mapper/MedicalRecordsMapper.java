package com.infercidium.safetynet.mapper;

import com.infercidium.safetynet.dto.PersonInfoDTO;
import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.dto.MedicalRecordsDTO;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MedicalRecordsMapper {
    MedicalRecords dtoToModel(MedicalRecordsDTO medicalRecordsDTO);
    MedicalRecordsDTO modelToDto(MedicalRecords medicalRecords);
    List<MedicalRecordsDTO> modelToDto(List<MedicalRecords> medicalRecords);

    //URL
    List<PersonsAndMedicalRecordsDTO> personsModelToChildAlertAndFireDTO(List<Persons> persons);

    @Mappings({@Mapping(source = "medicalRecords.persons.firstName", target = "firstName"),
            @Mapping(source = "medicalRecords.persons.lastName", target = "lastName"),
            @Mapping(source = "medicalRecords.persons.address.address", target = "address"),
            @Mapping(source = "medicalRecords.age", target = "age"),
            @Mapping(source = "medicalRecords.persons.email", target = "email"),
            @Mapping(source = "medicalRecords.medications", target = "medications"),
            @Mapping(source = "medicalRecords.allergies", target = "allergies")})
    PersonInfoDTO modelToPersonInfoDTO(MedicalRecords medicalRecords);
}
