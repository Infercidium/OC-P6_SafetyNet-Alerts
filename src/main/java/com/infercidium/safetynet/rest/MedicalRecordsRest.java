package com.infercidium.safetynet.rest;

import com.infercidium.safetynet.dto.MedicalRecordsDTO;
import com.infercidium.safetynet.mapper.MedicalRecordsMapper;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.service.MedicalRecordsI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/medicalRecord")
public class MedicalRecordsRest {

    /**
     * Instantiation of LOGGER in order to inform in console.
     */
    private static final Logger LOGGER
            = LoggerFactory.getLogger(MedicalRecordsRest.class);

    /**
     * Instantiation of MedicalRecordsService.
     */
    private final MedicalRecordsI medicalRecordsI;

    /**
     * Instantiation of MedicalrecordsMapper.
     */
    private final MedicalRecordsMapper medicalRecordsM;

    /**
     * Class constructor.
     * @param medicalRecordsIn this is MedicalrecordsService.
     * @param medicalRecordsMa this is MedicalRecordsMapper.
     */
    public MedicalRecordsRest(final MedicalRecordsI medicalRecordsIn,
                              final MedicalRecordsMapper medicalRecordsMa) {
        this.medicalRecordsI = medicalRecordsIn;
        this.medicalRecordsM = medicalRecordsMa;
    }

    //Post, Put, Delete

    /**
     * Endpoint allowing to post a MedicalRecord.
     * @param medicalRecordsDTO this is the information entered by the user.
     * @return 201 Created if successful,
     * 409 conflict if already exist or 400 bad request if bad field.
     */
    @PostMapping
    public ResponseEntity<Void> createMedicalRecords(
            @Valid @RequestBody final MedicalRecordsDTO medicalRecordsDTO) {
        String firstName = medicalRecordsDTO.getFirstName();
        String lastname = medicalRecordsDTO.getLastName();
        MedicalRecords medicalRecords
                = medicalRecordsM.dtoToModel(medicalRecordsDTO);
        MedicalRecords postMedicalRecords
                = medicalRecordsI
                .postMedicalRecords(medicalRecords, firstName, lastname);

        URI locate = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{firstName}/{lastName}")
                .buildAndExpand(postMedicalRecords
                        .getFirstName(), postMedicalRecords.getLastName())
                .toUri();

        LOGGER.info("Saving " + medicalRecords.getFirstName() + " "
                + medicalRecords.getLastName()
                + " in the Medicalrecords table");
        return ResponseEntity.created(locate).build();
    }

    /**
     * Endpoint allowing to post a Medicalrecord.
     * @param firstName : allows you to find the resource in the database.
     * @param lastName : allows you to find the resource in the database.
     * @param medicalRecordsDTO this is the information entered by the user.
     * @return 200 Ok if successful, 404 not found if non-existent
     * or 400 bad request if bad field.
     */
    @PutMapping(value = "/{firstName}/{lastName}")
    public ResponseEntity<Void> editMedicalRecords(
            @PathVariable final String firstName,
            @PathVariable final String lastName,
            @RequestBody final MedicalRecordsDTO medicalRecordsDTO) {
        MedicalRecords medicalRecords
                = medicalRecordsM.dtoToModel(medicalRecordsDTO);
        medicalRecordsI.editMedicalRecords(firstName, lastName, medicalRecords);
        LOGGER.info("MedicalRecord " + firstName + " "
                + lastName + " modification");
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint allowing to delete a MedicalRecord.
     * @param firstName : allows you to find the resource in the database.
     * @param lastName : allows you to find the resource in the database.
     * @return 200 Ok if successful, 404 not found if it does not exist.
     */
    @DeleteMapping(value = "/{firstName}/{lastName}")
    public ResponseEntity<Void> removeMedicalRecords(
            @PathVariable final String firstName,
            @PathVariable final String lastName) {
        medicalRecordsI.removeMedicalRecords(firstName, lastName);
        LOGGER.info("MedicalRecord " + firstName
                + " " + lastName + " deletion");
        return ResponseEntity.ok().build();
    }

    //Get

    /**
     * Endpoint to get MedicalRecord.
     * @param firstName : allows you to find the resource in the database.
     * @param lastName : allows you to find the resource in the database.
     * @return medicalRecord and 200 Ok if successful
     * or 404 not found if it does not exist.
     */
    @GetMapping(value = "/{firstName}/{lastName}")
    public MedicalRecordsDTO getMedicalRecord(
            @PathVariable final String firstName,
            @PathVariable final String lastName) {
        MedicalRecords medicalRecords
                = medicalRecordsI.getMedicalRecordName(firstName, lastName);
        MedicalRecordsDTO medicalRecordsDTO
                = medicalRecordsM.modelToDto(medicalRecords);
        LOGGER.info("MedicalRecord found");
        return medicalRecordsDTO;
    }

    /**
     * Endpoint to get MedicalRecords.
     * @return List of medicalRecord and 200 Ok if successful
     * or 404 not found if it does not exist.
     */
    @GetMapping(value = "/")
    public List<MedicalRecordsDTO> getMedicalRecords() {
        List<MedicalRecords> medicalRecords
                = medicalRecordsI.getMedicalRecords();
        List<MedicalRecordsDTO> medicalRecordsDTOS
                = medicalRecordsM.modelToDto(medicalRecords);
        LOGGER.info("List of MedicalRecords displayed");
        return medicalRecordsDTOS;
    }
}
