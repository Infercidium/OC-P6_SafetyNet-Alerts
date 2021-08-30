package com.Infercidium.SafetyNet.service;

import com.infercidium.safetynet.repository.MedicalrecordsRepository;
import com.infercidium.safetynet.service.MedicalRecordsService;
import com.infercidium.safetynet.service.PersonsService;
import com.infercidium.safetynet.service.SecondaryTableService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {MedicalRecordsService.class})
class MedicalRecordsServiceTest {

    @MockBean
    private PersonsService personsS;
    @MockBean
    private MedicalrecordsRepository medicalRecordsR;
    @MockBean
    private SecondaryTableService secondaryTableS;
    @Autowired
    private MedicalRecordsService medicalRecordsService;

    @BeforeEach
    private void setUpPerTest() {

    }

    @Test
    void postMedicalRecords() {
    }

    @Test
    void editMedicalRecords() {
    }

    @Test
    void removeMedicalRecords() {
    }

    @Test
    void getMedicalRecordName() {
    }

    @Test
    void getMedicalRecords() {
    }

    @Test
    void checkMajority() {
    }

    @Test
    void getPersonsAddress() {
    }

    @Test
    void getChildAlertCount() {
    }
}