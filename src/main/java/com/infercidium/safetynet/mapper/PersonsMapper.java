package com.infercidium.safetynet.mapper;

import com.infercidium.safetynet.dto.PersonsDTO;
import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.Persons;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public abstract class PersonsMapper {

    /**
     * Converter.
     * @param personsDTO Contains information visible on the customer side.
     * @return a Person for server.
     */
    public abstract Persons dtoToModel(PersonsDTO personsDTO);

    /**
     * Converter.
     * @param persons Contains information server side.
     * @return a PersonDTO for customer.
     */
    public abstract PersonsDTO modelToDto(Persons persons);

    /**
     * List converter.
     * @param personsList Contains information server side.
     * @return a list of PersonDTO for customer.
     */
    public abstract List<PersonsDTO> modelToDto(List<Persons> personsList);
}
