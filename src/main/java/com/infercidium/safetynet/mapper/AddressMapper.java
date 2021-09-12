package com.infercidium.safetynet.mapper;

import com.infercidium.safetynet.model.Address;
import org.mapstruct.Mapper;

import java.util.Set;

/**
 * Converter of Address.
 */
@Mapper(componentModel = "spring")
public abstract class AddressMapper {

    /**
     * Converter.
     * @param address Contains information visible on the customer side.
     * @return Address for server.
     */
    public abstract Address addressFromString(String address);

    /**
     * Converter.
     * @param address Contains information server side.
     * @return String address for customer.
     */
    public String stringFromAddress(final Address address) {
        return address.getAddress();
    }

    /**
     * Converter.
     * @param address Contains information visible on the customer side.
     * @return Address for server.
     */
    public abstract Set<Address> addressFromString(Set<String> address);

    /**
     * Converter.
     * @param address Contains information server side.
     * @return String address for customer.
     */
    public abstract Set<String> stringFromAddress(Set<Address> address);
}
