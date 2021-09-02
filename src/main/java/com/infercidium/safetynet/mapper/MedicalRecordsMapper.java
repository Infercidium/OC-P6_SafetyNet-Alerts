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

    /**
     * Converter.
     * @param medicalRecordsDTO Contains information visible
     *                          on the customer side.
     * @return a Medicalrecords for server.
     */
    MedicalRecords dtoToModel(MedicalRecordsDTO medicalRecordsDTO);

    /**
     * Converter.
     * @param medicalRecords Contains information server side.
     * @return a MedicalrecordsDTO for customer.
     */
    MedicalRecordsDTO modelToDto(MedicalRecords medicalRecords);

    /**
     * List converter.
     * @param medicalRecordsList Contains information server side.
     * @return a list of MedicalRecordsDTO for customer.
     */
    List<MedicalRecordsDTO> modelToDto(List<MedicalRecords> medicalRecordsList);

    //URL
    /**
     * This method converts a list of Persons to PersonsAndMedicalrecordsDTO.
     * @param persons list of Persons.
     * @return a list of personsAndMedicalRecordsDTo containing
     * the attributes of Persons.
     */
    List<PersonsAndMedicalRecordsDTO>
    personsModelToChildAlertAndFireDTO(List<Persons> persons);

    /**
     * This method converts a MedicalRecords to PersonInfoDTO.
     * @param medicalRecords Contains information server side.
     * @return a PersonInfoDTo containing the attributes of MedicalRecords.
     */
    @Mappings({@Mapping(source = "medicalRecords.persons.firstName",
                        target = "firstName"),
            @Mapping(source = "medicalRecords.persons.lastName",
                    target = "lastName"),
            @Mapping(source = "medicalRecords.persons.address.address",
                    target = "address"),
            @Mapping(source = "medicalRecords.age",
                    target = "age"),
            @Mapping(source = "medicalRecords.persons.email",
                    target = "email"),
            @Mapping(source = "medicalRecords.medications",
                    target = "medications"),
            @Mapping(source = "medicalRecords.allergies",
                    target = "allergies")})
    PersonInfoDTO modelToPersonInfoDTO(MedicalRecords medicalRecords);
}
