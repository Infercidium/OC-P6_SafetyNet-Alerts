package com.infercidium.safetynet.repository;

import com.infercidium.safetynet.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findByAddressIgnoreCase(String address);
}
