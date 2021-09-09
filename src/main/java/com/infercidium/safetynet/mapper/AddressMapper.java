package com.infercidium.safetynet.mapper;

import com.infercidium.safetynet.model.Address;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class AddressMapper {

    public abstract Address addressFromString(String address);
    public String stringFromAddress(Address address) {
        return address.getAddress();
    }
    public abstract Set<Address> addressFromString(Set<String> address);
    public abstract Set<String> strinngFromAddress(Set<Address> address);
}
