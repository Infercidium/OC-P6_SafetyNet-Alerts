package com.Infercidium.SafetyNet.service;

import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.repository.AddressRepository;
import com.infercidium.safetynet.service.AddressI;
import com.infercidium.safetynet.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = {AddressService.class})
class AddressServiceTest {

    @MockBean
    private AddressRepository addressR;
    @Autowired
    private AddressService addressS;

    Address address = new Address("test");

    @Test
    void succesCheckAddress() {
        when(addressR.findByAddressIgnoreCase(address.getAddress())).thenReturn(null);
        when(addressR.save(address)).thenReturn(address);
        Address addressResult = addressS.checkAddress(address);
        assertEquals(address, addressResult);
        verify(addressR, times(1)).save(address);
        verify(addressR, times(1)).findByAddressIgnoreCase(address.getAddress());
    }

    @Test
    void echecCheckAddress() {
        when(addressR.findByAddressIgnoreCase(address.getAddress())).thenReturn(address);
        Address addressResult = addressS.checkAddress(address);
        assertEquals(address, addressResult);
        verify(addressR, times(2)).findByAddressIgnoreCase(address.getAddress());
    }
}