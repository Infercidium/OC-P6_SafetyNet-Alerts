package com.infercidium.safetynet.mapper;

import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.model.MedicalRecords;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FireAndFloodMapper {
    /**
     * This method converts a Medicalrecords
     * to PersonsAndMedicalRecordsDTO.
     * @param medicalRecords is a MedicalRecords.
     * @return a PersonsAndMedicalRecordsDTO
     * containing attributes of Persons and MedicalRecords.
     */
    @Mappings({@Mapping(source = "medicalRecords.persons.firstName",
            target = "firstName"),
            @Mapping(source = "medicalRecords.persons.lastName",
                    target = "lastName"),
            @Mapping(source = "medicalRecords.persons.phone", target = "phone"),
            @Mapping(source = "medicalRecords.age", target = "age"),
            @Mapping(source = "medicalRecords.medications",
                    target = "medications"),
            @Mapping(source = "medicalRecords.allergies",
                    target = "allergies"),
            @Mapping(source = "medicalRecords.persons.address.address",
                    target = "address")})
    PersonsAndMedicalRecordsDTO modelToDto(MedicalRecords medicalRecords);

    /**
     * This method converts a list of Medicalrecords
     * to PersonsAndMedicalRecordsDTO.
     * @param medicalRecords list of MedicalRecords.
     * @return a list of PersonsAndMedicalRecordsDTO
     * containing attributes of Persons and MedicalRecords.
     */
    List<PersonsAndMedicalRecordsDTO> modelToDto(
            List<MedicalRecords> medicalRecords);
}
