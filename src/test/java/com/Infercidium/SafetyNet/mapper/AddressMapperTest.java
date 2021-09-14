package com.Infercidium.SafetyNet.mapper;

import com.infercidium.safetynet.mapper.AddressMapper;
import com.infercidium.safetynet.mapper.AddressMapperImpl;
import com.infercidium.safetynet.mapper.FirestationsMapperImpl;
import com.infercidium.safetynet.model.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {AddressMapperImpl.class})
class AddressMapperTest {

    @Autowired
    AddressMapper addressMapper;

    String addressString = "1 rue du testing";
    Address address = new Address(addressString);
    Set<Address> addressSet = Collections.singleton(address);
    Set<String> addressStringSet = Collections.singleton(addressString);

    @Test
    void addressFromString() {
        Address result = addressMapper.addressFromString(addressString);
        assertEquals(address, result);
    }

    @Test
    void stringFromAddress() {
        Set<Address> result = addressMapper.addressFromString(addressStringSet);
        assertEquals(addressSet, result);
    }

    @Test
    void testAddressFromString() {
        String result = addressMapper.stringFromAddress(address);
        assertEquals(addressString, result);
    }

    @Test
    void testStringFromAddress() {
        Set<String> result = addressMapper.stringFromAddress(addressSet);
        assertEquals(addressStringSet, result);
    }
}