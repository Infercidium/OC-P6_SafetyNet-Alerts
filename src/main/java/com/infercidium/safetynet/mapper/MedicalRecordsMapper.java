package com.infercidium.safetynet.mapper;

import com.infercidium.safetynet.dto.MedicalRecordsDTO;
import com.infercidium.safetynet.model.MedicalRecords;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Converter of MedicalRecords.
 */
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
}
