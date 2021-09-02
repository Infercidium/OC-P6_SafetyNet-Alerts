package com.Infercidium.SafetyNet.service;

import com.infercidium.safetynet.model.Medications;
import com.infercidium.safetynet.repository.MedicationsRepository;
import com.infercidium.safetynet.service.MedicationsService;
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

@SpringBootTest(classes = {MedicationsService.class})
class MedicationsServiceTest {
    @MockBean
    MedicationsRepository medicationsR;
    @Autowired
    MedicationsService medicationsS;

    Set<Medications> medicationsSet = new HashSet<>();
    Medications medications = new Medications("test");
    @Test
    void succesCheckMedication() {
        medicationsSet.add(medications);
        when(medicationsR.findByMedicationIgnoreCase(medications.getMedication())).thenReturn(null);
        when(medicationsR.save(medications)).thenReturn(medications);
        Set<Medications> medicationsResult = medicationsS.checkMedications(medicationsSet);
        assertEquals(medicationsSet, medicationsResult);
        verify(medicationsR, times(1)).save(medications);
        verify(medicationsR, times(1)).findByMedicationIgnoreCase(medications.getMedication());
    }

    @Test
    void echecCheckMedications() {
        medicationsSet.add(medications);
        when(medicationsR.findByMedicationIgnoreCase(medications.getMedication())).thenReturn(medications);
        Set<Medications> medicationsResult = medicationsS.checkMedications(medicationsSet);
        assertEquals(medicationsSet, medicationsResult);
        verify(medicationsR, times(2)).findByMedicationIgnoreCase(medications.getMedication());
    }
}