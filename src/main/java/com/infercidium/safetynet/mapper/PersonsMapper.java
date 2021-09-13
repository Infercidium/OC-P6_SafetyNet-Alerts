package com.infercidium.safetynet.mapper;

import com.infercidium.safetynet.dto.PersonsDTO;
import com.infercidium.safetynet.model.Persons;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Converter of Persons.
 */
@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface PersonsMapper {

    /**
     * Converter.
     * @param personsDTO Contains information visible on the customer side.
     * @return a Person for server.
     */
    Persons dtoToModel(PersonsDTO personsDTO);

    /**
     * Converter.
     * @param persons Contains information server side.
     * @return a PersonDTO for customer.
     */
    PersonsDTO modelToDto(Persons persons);

    /**
     * List converter.
     * @param personsList Contains information server side.
     * @return a list of PersonDTO for customer.
     */
    List<PersonsDTO> modelToDto(List<Persons> personsList);
}
