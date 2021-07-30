package com.infercidium.safetynet.rest;

import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.repository.MedicalrecordsRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MedicalRecordsRest {
    private final MedicalrecordsRepository medicalrecordsR;

    public MedicalRecordsRest(MedicalrecordsRepository medicalrecordsR) { this.medicalrecordsR = medicalrecordsR;}

    @GetMapping(value = "/medicalrecords")
    public List<MedicalRecords> getMedicalrecords(){
        return this.medicalrecordsR.findAll();
    }
}
