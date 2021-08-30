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

    /**
     * Converter.
     * @param firestationsDTO Contains information visible on the customer side.
     * @return a Firestation for server.
     */
    public abstract Firestations dtoToModel(FirestationsDTO firestationsDTO);

    /**
     * Converter help.
     * @param address string to inject into an address class.
     * @return an address class.
     */
    public abstract Address map(String address);

    /**
     * Converter.
     * @param firestations Contains information server side.
     * @return a FirestationDTO for customer.
     */
    public abstract FirestationsDTO modelToDto(Firestations firestations);

    /**
     * List converter.
     * @param firestationsList Contains information server side.
     * @return a list of FirestationDTO for customer.
     */
    public abstract List<FirestationsDTO>
    modelToDto(List<Firestations> firestationsList);

    /**
     * Converter help.
     * @param address an address class.
     * @return a String version of address.
     */
    public String map(final Address address) {
        return address.getAddress();
    }

    //URL

    /**
     * This method converts a list of Persons to StationNumberDTO.
     * @param persons list of Persons.
     * @return a list of StationNumberDTo containing the attributes of Persons.
     */
    public abstract List<StationNumberDTO>
    personsModelToStationNumberDTO(List<Persons> persons);

    /**
     * This method converts a list of Medicalrecords
     * to PersonsAndMedicalRecordsDTO.
     * @param medicalRecords list of MedicalRecords.
     * @return a list of PersonsAndMedicalRecordsDTO
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
                    target = "allergies")})
    public abstract List<PersonsAndMedicalRecordsDTO>
    personsAndMedicalRecordsModelToPersonsAndMedicalRecordsDTO(
            List<MedicalRecords> medicalRecords);

    /**
     * This method returns a list that groups people by address.
     * It should include their name, phone number and age,
     * and include their medical history.
     * @param medicalRecordsMap it's a Map containing in key the addresses
     *                          and in value the medicalrecords
     *                          of the Persons living there.
     * @return flood PersonsAndMedicalRecordsDTO containing attributes of People
     * and MedicalRecords.
     */
    public Map<String, Object> personsAndMedicalRecordsModelToFloodDTO(
            final Map<String, List<MedicalRecords>> medicalRecordsMap) {
        Map<String, Object> flood = new HashMap<>();
        for (Map.Entry<String, List<MedicalRecords>> medicalRecordsList
                : medicalRecordsMap.entrySet()) {
            List<PersonsAndMedicalRecordsDTO> floodDTO
            = personsAndMedicalRecordsModelToPersonsAndMedicalRecordsDTO(
                    medicalRecordsList.getValue());
            flood.put(medicalRecordsList.getKey(), floodDTO);
        }
        return flood;
    }
}
