package com.infercidium.safetynet.repository;

import com.infercidium.safetynet.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

    /**
     * Consult the database for the address.
     * @param address : address in character string.
     * @return the corresponding Address.
     */
    Address findByAddressIgnoreCase(String address);
}
