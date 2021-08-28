package com.infercidium.safetynet.mapper;

import com.infercidium.safetynet.dto.PersonsDTO;
import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.Persons;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class PersonsMapper {
    public abstract Persons dtoToModel(PersonsDTO personsDTO);
    public abstract Address map(String address);
    public abstract PersonsDTO modelToDto(Persons persons);
    public abstract List<PersonsDTO> modelToDto(List<Persons> persons);
    public String map(final Address address) {
        return address.getAddress();
    }
}
