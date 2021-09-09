package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.Address;

import java.util.Set;

public interface AddressI {

    /**
     * Check the address, if it exists add the id, if not create it.
     * @param address to be checked.
     * @return address complete.
     */
    Address checkAddress(Address address);
}
