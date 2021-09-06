package com.infercidium.safetynet.mapper;

import com.infercidium.safetynet.model.Address;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address addressFromString(String address);
    String stringFromAddress(Address address);
    Set<Address> addressFromString(Set<String> address);
    Set<String> strinngFromAddress(Set<Address> address);
}
