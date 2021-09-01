package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.repository.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AddressService implements AddressI {

    /**
     * Instantiation of LOGGER in order to inform in console.
     */
    private static final Logger LOGGER
            = LoggerFactory.getLogger(AddressService.class);

    /**
     * Instantiation of AddressRepository.
     */
    private final AddressRepository addressR;

    /**
     * Class constructor.
     * @param addressRe this is AdressRepository.
     */
    public AddressService(final AddressRepository addressRe) {
        this.addressR = addressRe;
    }

    /**
     * Check the address, if it exists add the id, if not create it.
     * @param address to be checked.
     * @return address complete.
     */
    @Override
    public Address checkAddress(final Address address) {
        Address addressCheck;
        if (addressR.findByAddressIgnoreCase(address.getAddress()) == null) {
            addressCheck = addressR.save(address);
            LOGGER.debug("Save " + address + " in the Address table");
        } else {
            addressCheck = addressR
                    .findByAddressIgnoreCase(address.getAddress());
        }
        LOGGER.debug("Address checked");
        return addressCheck;
    }
}
