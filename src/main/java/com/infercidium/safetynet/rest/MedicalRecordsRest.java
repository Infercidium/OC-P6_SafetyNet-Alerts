package com.infercidium.safetynet.rest;

import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.service.MedicalRecordsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class MedicalRecordsRest {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(MedicalRecordsRest.class);
    private final MedicalRecordsService medicalRecordsS;
    public MedicalRecordsRest(final MedicalRecordsService medicalRecordsSe) {
        this.medicalRecordsS = medicalRecordsSe;
    }

    @PostMapping(value = "/medicalRecord")
    public String createMedicalRecords(
            @Valid @RequestBody final MedicalRecords medicalRecords) {
       // ResponseEntity<Void> result
              //  = medicalRecordsS.createMedicalRecords(medicalRecords);
        /*LOGGER.info("Saving " + medicalRecords.getFirstName()
                + " " + medicalRecords.getLastName()
                + " in the Medicalrecords table");*/
        System.out.println(medicalRecords);
        return "medicalRecords.toString()";
    }

    @PutMapping(value = "/medicalRecord/{firstName}/{lastName}")
    public ResponseEntity<Void> editMedicalRecords(
            @PathVariable final String firstName,
            @PathVariable final String lastName,
            @Valid @RequestBody final MedicalRecords medicalRecords) {
        ResponseEntity<Void> result
                = medicalRecordsS.editMedicalRecords(
                        firstName, lastName, medicalRecords);
        LOGGER.info("MedicalRecord " + firstName
                + " " + lastName + " modification");
        return result;
    }

    @DeleteMapping(value = "/medicalRecord/{firstName}/{lastName}")
    public ResponseEntity<Void> removeMedicalRecords(
            @PathVariable final String firstName,
            @PathVariable final String lastName) {
        ResponseEntity<Void> result
                = medicalRecordsS.removeMedicalRecords(firstName, lastName);
        LOGGER.info("MedicalRecord " + firstName
                + " " + lastName + " deletion");
        return result;
    }

    @GetMapping(value = "/medicalRecord/{firstName}/{lastName}")
    public MappingJacksonValue getMedicalRecord(
            @PathVariable final String firstName,
            @PathVariable final String lastName) {
        List<MedicalRecords> medicalRecord
                = medicalRecordsS.getMedicalRecord(firstName, lastName);
        MappingJacksonValue filterMedicalRecords
                = medicalRecordsS.medicalRecordFilterNull(medicalRecord);
        LOGGER.info("MedicalRecord found");
        return filterMedicalRecords;
    }

    @GetMapping(value = "/medicalRecords")
    public MappingJacksonValue getMedicalRecords() {
        List<MedicalRecords> medicalRecords
                = medicalRecordsS.getMedicalRecords();
        MappingJacksonValue filterMedicalRecords
                = medicalRecordsS.medicalRecordFilterNull(medicalRecords);
        LOGGER.info("List of MedicalRecords displayed");
        return filterMedicalRecords;
    }

    //URL lié à MedicalRecords
    @GetMapping(value = "/childAlert")
    public MappingJacksonValue getChildAlert(
            @RequestParam final String address) {
        MappingJacksonValue childAlert = medicalRecordsS.getChildAlert(address);
        LOGGER.info("List of child with adult displayed");
        return childAlert;
    }

    @GetMapping(value = "/personInfo")
    public MappingJacksonValue getPersonInfo(
            @RequestParam final String firstName,
            @RequestParam final String lastName) {
        MappingJacksonValue personInfo
                = medicalRecordsS.getPersonInfo(firstName, lastName);
        LOGGER.info("Information from " + firstName
                + " " + lastName + " displayed");
        return personInfo;
    }
}
