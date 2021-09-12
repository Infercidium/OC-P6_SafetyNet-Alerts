package com.infercidium.safetynet.mapper;

import com.infercidium.safetynet.dto.FirestationsDTO;
import com.infercidium.safetynet.model.Firestations;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Converter of Firestations.
 */
@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface FirestationsMapper {

    /**
     * Converter.
     * @param firestationsDTO Contains information visible on the customer side.
     * @return a Firestation for server.
     */
    Firestations dtoToModel(FirestationsDTO firestationsDTO);

    /**
     * Converter.
     * @param firestations Contains information server side.
     * @return a FirestationDTO for customer.
     */
    FirestationsDTO modelToDto(Firestations firestations);

    /**
     * List converter.
     * @param firestationsList Contains information server side.
     * @return a list of FirestationDTO for customer.
     */
    List<FirestationsDTO> modelToDto(List<Firestations> firestationsList);
}
