package com.infercidium.safetynet.rest;

import com.infercidium.safetynet.filter.MedicalRecordsFilter;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.*;

@RestController
public class MedicalRecordsRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MedicalRecordsRest.class);
    private final MedicalRecordsService medicalRecordsS;
    private final MedicalRecordsFilter medicalRecordsF;
    public MedicalRecordsRest(final MedicalRecordsService medicalRecordsSe, final MedicalRecordsFilter medicalRecordsFe) {
        this.medicalRecordsS = medicalRecordsSe;
        this.medicalRecordsF = medicalRecordsFe;
    }

    @PostMapping(value = "/medicalRecord")
    public ResponseEntity<Void> createMedicalRecords(@Valid @RequestBody final MedicalRecords medicalRecords) {
        MedicalRecords finalMedicalRecords = medicalRecordsS.createMedicalRecords(medicalRecords);

        URI locate = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{firstName}/{lastName}")
                .buildAndExpand(finalMedicalRecords.getFirstName(), finalMedicalRecords.getLastName())
                .toUri();

        LOGGER.info("Saving " + medicalRecords.getFirstName() + " " + medicalRecords.getLastName() + " in the Medicalrecords table");
        return ResponseEntity.created(locate).build();
    }

    @PutMapping(value = "/medicalRecord/{firstName}/{lastName}")
    public ResponseEntity<Void> editMedicalRecords(@PathVariable final String firstName, @PathVariable final String lastName, @RequestBody final MedicalRecords medicalRecords) {
        medicalRecordsS.editMedicalRecords(firstName, lastName, medicalRecords);
        LOGGER.info("MedicalRecord " + firstName + " " + lastName + " modification");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/medicalRecord/{firstName}/{lastName}")
    public ResponseEntity<Void> removeMedicalRecords(@PathVariable final String firstName, @PathVariable final String lastName) {
        medicalRecordsS.removeMedicalRecords(firstName, lastName);
        LOGGER.info("MedicalRecord " + firstName + " " + lastName + " deletion");
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/medicalRecord/{firstName}/{lastName}")
    public MappingJacksonValue getMedicalRecord(@PathVariable final String firstName, @PathVariable final String lastName) {
        List<MedicalRecords> medicalRecord = medicalRecordsS.getMedicalRecord(firstName, lastName);
        MappingJacksonValue filterMedicalRecords = medicalRecordsF.medicalRecordsNullFilter(medicalRecord);
        LOGGER.info("MedicalRecord found");
        return filterMedicalRecords;
    }

    @GetMapping(value = "/medicalRecords")
    public MappingJacksonValue getMedicalRecords() {
        List<MedicalRecords> medicalRecords = medicalRecordsS.getMedicalRecords();
        MappingJacksonValue filterMedicalRecords = medicalRecordsF.medicalRecordsNullFilter(medicalRecords);
        LOGGER.info("List of MedicalRecords displayed");
        return filterMedicalRecords;
    }

    //URL lié à MedicalRecords
    @GetMapping(value = "/childAlert")
    public Object getChildAlert(@RequestParam final String address) {
        Map<String, Object> finalResult = new HashMap<>();
        List<MedicalRecords> child = medicalRecordsS.getChildAlertChild(address);
        if(child.size() == 0) {
            return finalResult;
        } else {
            Set<String> attributeA = medicalRecordsF.newFilterSet("firstName", "lastName");
            Set<String> attributeC = medicalRecordsF.newFilterSet("firstName", "lastName", "age");
            List<MedicalRecords> adult = medicalRecordsS.getChildAlertAdult(address);
            MappingJacksonValue adultFilter = medicalRecordsF.medicalRecordsFilterAdd(adult, attributeA);
            MappingJacksonValue childFilter = medicalRecordsF.medicalRecordsFilterAdd(child, attributeC);
            finalResult.put("child", childFilter);
            finalResult.put("adult", adultFilter);
            return finalResult;
        }
    }
/*
    @GetMapping(value = "/personInfo")
    public MappingJacksonValue getPersonInfo(
            @RequestParam final String firstName,
            @RequestParam final String lastName) {
        MappingJacksonValue personInfo
                = medicalRecordsS.getPersonInfo(firstName, lastName);
        LOGGER.info("Information from " + firstName
                + " " + lastName + " displayed");
        return personInfo;
    }*/
}
