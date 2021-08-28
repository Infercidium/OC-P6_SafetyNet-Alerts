package com.infercidium.safetynet.rest;

import com.infercidium.safetynet.dto.PersonInfoDTO;
import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.dto.MedicalRecordsDTO;
import com.infercidium.safetynet.mapper.MedicalRecordsMapper;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.service.MedicalRecordsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
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
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
public class MedicalRecordsRest {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(MedicalRecordsRest.class);
    private final MedicalRecordsService medicalRecordsS;
    private final MedicalRecordsMapper medicalRecordsM;
    public MedicalRecordsRest(final MedicalRecordsService medicalRecordsSe,
                              final MedicalRecordsMapper medicalRecordsMa) {
        this.medicalRecordsS = medicalRecordsSe;
        this.medicalRecordsM = medicalRecordsMa;
    }

    //Post, Put, Delete
    @PostMapping(value = "/medicalRecord")
    public ResponseEntity<Void> createMedicalRecords(@Valid @RequestBody final MedicalRecordsDTO medicalRecordsDTO) {
        String firstName = medicalRecordsDTO.getFirstName();
        String lastname = medicalRecordsDTO.getLastName();
        MedicalRecords medicalRecords = medicalRecordsM.dtoToModel(medicalRecordsDTO);
        MedicalRecords postMedicalRecords = medicalRecordsS.postMedicalRecords(medicalRecords, firstName, lastname);

        URI locate = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{firstName}/{lastName}")
                .buildAndExpand(postMedicalRecords.getFirstName(), postMedicalRecords.getLastName())
                .toUri();

        LOGGER.info("Saving " + medicalRecords.getFirstName() + " " + medicalRecords.getLastName() + " in the Medicalrecords table");
        return ResponseEntity.created(locate).build();
    }

    @PutMapping(value = "/medicalRecord/{firstName}/{lastName}")
    public ResponseEntity<Void> editMedicalRecords(@PathVariable final String firstName, @PathVariable final String lastName, @RequestBody final MedicalRecordsDTO medicalRecordsDTO) {
        MedicalRecords medicalRecords = medicalRecordsM.dtoToModel(medicalRecordsDTO);
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

    //Get
    @GetMapping(value = "/medicalRecord/{firstName}/{lastName}")
    public MedicalRecordsDTO getMedicalRecord(@PathVariable final String firstName, @PathVariable final String lastName) {
        MedicalRecords medicalRecords = medicalRecordsS.getMedicalRecordName(firstName, lastName);
        MedicalRecordsDTO medicalRecordsDTO = medicalRecordsM.modelToDto(medicalRecords);
        LOGGER.info("MedicalRecord found");
        return medicalRecordsDTO;
    }

    @GetMapping(value = "/medicalRecords")
    public List<MedicalRecordsDTO> getMedicalRecords() {
        List<MedicalRecords> medicalRecords = medicalRecordsS.getMedicalRecords();
        List<MedicalRecordsDTO> medicalRecordsDTOS = medicalRecordsM.modelToDto(medicalRecords);
        LOGGER.info("List of MedicalRecords displayed");
        return medicalRecordsDTOS;
    }

    //URL lié à MedicalRecords
    @GetMapping(value = "/childAlert")
    public Map<String, Object> getChildAlert(@RequestParam final String address) {
        List<Persons> persons = medicalRecordsS.getPersonsAddress(address);
        List<PersonsAndMedicalRecordsDTO> personsAndMedicalrecordsDTO = medicalRecordsM.personsModelToChildAlertAndFireDTO(persons);
        Map<String, Object> childAlertResult = medicalRecordsS.getChildAlertCount(personsAndMedicalrecordsDTO);
        LOGGER.info("List of children of address : " + address + ", found");
        return childAlertResult;
    }

    @GetMapping(value = "personInfo")
    public PersonInfoDTO getPersonInfo(@RequestParam final String firstName, @RequestParam final String lastName) {
        MedicalRecords medicalRecords = medicalRecordsS.getMedicalRecordName(firstName, lastName);
        PersonInfoDTO personInfoDTO = medicalRecordsM.modelToPersonInfoDTO(medicalRecords);
        LOGGER.info("Person information " + firstName + " " + lastName + " found");
        return personInfoDTO;
    }
}
