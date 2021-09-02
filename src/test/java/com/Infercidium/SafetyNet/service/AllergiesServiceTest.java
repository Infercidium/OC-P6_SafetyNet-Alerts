package com.Infercidium.SafetyNet.service;

import com.infercidium.safetynet.model.Allergies;
import com.infercidium.safetynet.repository.AllergiesRepository;
import com.infercidium.safetynet.service.AllergiesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {AllergiesService.class})
class AllergiesServiceTest {

    @MockBean
    AllergiesRepository allergiesR;
    @Autowired
    AllergiesService allergiesS;

    Set<Allergies> allergiesSet = new HashSet<>();
    Allergies allergie = new Allergies("test");
    @Test
    void succesCheckAllergies() {
        allergiesSet.add(allergie);
        when(allergiesR.findByAllergyIgnoreCase(allergie.getAllergy())).thenReturn(null);
        when(allergiesR.save(allergie)).thenReturn(allergie);
        Set<Allergies> allergiesResult = allergiesS.checkAllergies(allergiesSet);
        assertEquals(allergiesSet, allergiesResult);
        verify(allergiesR, times(1)).save(allergie);
        verify(allergiesR, times(1)).findByAllergyIgnoreCase(allergie.getAllergy());
    }

    @Test
    void echecCheckAllergies() {
        allergiesSet.add(allergie);
        when(allergiesR.findByAllergyIgnoreCase(allergie.getAllergy())).thenReturn(allergie);
        Set<Allergies> allergiesResult = allergiesS.checkAllergies(allergiesSet);
        assertEquals(allergiesSet, allergiesResult);
        verify(allergiesR, times(2)).findByAllergyIgnoreCase(allergie.getAllergy());
    }
}