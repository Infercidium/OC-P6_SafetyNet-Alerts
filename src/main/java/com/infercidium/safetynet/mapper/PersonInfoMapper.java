package com.infercidium.safetynet.mapper;

import com.infercidium.safetynet.dto.PersonInfoDTO;
import com.infercidium.safetynet.model.MedicalRecords;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Converter of PersonInfo.
 */
@Mapper(componentModel = "spring")
public interface PersonInfoMapper {
    /**
     * This method converts a MedicalRecords to PersonInfoDTO.
     * @param medicalRecords Contains information server side.
     * @return a PersonInfoDTo containing the attributes of MedicalRecords.
     */
    @Mappings({@Mapping(source = "medicalRecords.persons.firstName",
            target = "firstName"),
            @Mapping(source = "medicalRecords.persons.lastName",
                    target = "lastName"),
            @Mapping(source = "medicalRecords.persons.email", target = "email"),
            @Mapping(source = "medicalRecords.age", target = "age"),
            @Mapping(source = "medicalRecords.medications",
                    target = "medications"),
            @Mapping(source = "medicalRecords.allergies",
                    target = "allergies"),
            @Mapping(source = "medicalRecords.persons.address.address",
                    target = "address")})
    PersonInfoDTO modelToDto(MedicalRecords medicalRecords);
}
