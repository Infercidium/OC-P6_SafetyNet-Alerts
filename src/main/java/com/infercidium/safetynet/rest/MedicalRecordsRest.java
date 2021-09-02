package com.infercidium.safetynet.rest;

import com.infercidium.safetynet.dto.PersonInfoDTO;
import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.dto.MedicalRecordsDTO;
import com.infercidium.safetynet.mapper.MedicalRecordsMapper;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
public class MedicalRecordsRest {

    /**
     * Instantiation of LOGGER in order to inform in console.
     */
    private static final Logger LOGGER
            = LoggerFactory.getLogger(MedicalRecordsRest.class);

    /**
     * Instantiation of MedicalRecordsService.
     */
    private final MedicalRecordsI medicalRecordsS;

    /**
     * Instantiation of MedicalrecordsMapper.
     */
    private final MedicalRecordsMapper medicalRecordsM;

    /**
     * Class constructor.
     * @param medicalRecordsSe this is MedicalrecordsService.
     * @param medicalRecordsMa this is MedicalRecordsMapper.
     */
    public MedicalRecordsRest(final MedicalRecordsI medicalRecordsSe,
                              final MedicalRecordsMapper medicalRecordsMa) {
        this.medicalRecordsS = medicalRecordsSe;
        this.medicalRecordsM = medicalRecordsMa;
    }

    //Post, Put, Delete

    /**
     * Endpoint allowing to post a MedicalRecord.
     * @param medicalRecordsDTO this is the information entered by the user.
     * @return 201 Created if successful,
     * 409 conflict if already exist or 400 bad request if bad field.
     */
    @PostMapping(value = "/medicalRecord")
    public ResponseEntity<Void> createMedicalRecords(
            @Valid @RequestBody final MedicalRecordsDTO medicalRecordsDTO) {
        String firstName = medicalRecordsDTO.getFirstName();
        String lastname = medicalRecordsDTO.getLastName();
        MedicalRecords medicalRecords
                = medicalRecordsM.dtoToModel(medicalRecordsDTO);
        MedicalRecords postMedicalRecords
                = medicalRecordsS
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
    @PutMapping(value = "/medicalRecord/{firstName}/{lastName}")
    public ResponseEntity<Void> editMedicalRecords(
            @PathVariable final String firstName,
            @PathVariable final String lastName,
            @RequestBody final MedicalRecordsDTO medicalRecordsDTO) {
        MedicalRecords medicalRecords
                = medicalRecordsM.dtoToModel(medicalRecordsDTO);
        medicalRecordsS.editMedicalRecords(firstName, lastName, medicalRecords);
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
    @DeleteMapping(value = "/medicalRecord/{firstName}/{lastName}")
    public ResponseEntity<Void> removeMedicalRecords(
            @PathVariable final String firstName,
            @PathVariable final String lastName) {
        medicalRecordsS.removeMedicalRecords(firstName, lastName);
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
    @GetMapping(value = "/medicalRecord/{firstName}/{lastName}")
    public MedicalRecordsDTO getMedicalRecord(
            @PathVariable final String firstName,
            @PathVariable final String lastName) {
        MedicalRecords medicalRecords
                = medicalRecordsS.getMedicalRecordName(firstName, lastName);
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
    @GetMapping(value = "/medicalRecords")
    public List<MedicalRecordsDTO> getMedicalRecords() {
        List<MedicalRecords> medicalRecords
                = medicalRecordsS.getMedicalRecords();
        List<MedicalRecordsDTO> medicalRecordsDTOS
                = medicalRecordsM.modelToDto(medicalRecords);
        LOGGER.info("List of MedicalRecords displayed");
        return medicalRecordsDTOS;
    }

    //URL lié à MedicalRecords

    /**
     * This url returns a map containing an adult list
     * and a list of children of an address,
     * if there are no children, it returns an empty map.
     * @param address : allows you to find the resource in the database.
     * @return result and 200 Ok if successful
     * or 404 not found if it does not exist.
     */
    @GetMapping(value = "/childAlert")
    public Map<String, Object> getChildAlert(
            @RequestParam final String address) {
        List<Persons> persons = medicalRecordsS.getPersonsAddress(address);
        List<PersonsAndMedicalRecordsDTO> personsAndMedicalrecordsDTO
                = medicalRecordsM.personsModelToChildAlertAndFireDTO(persons);
        Map<String, Object> childAlertResult
                = medicalRecordsS
                .getChildAlertCount(personsAndMedicalrecordsDTO);
        LOGGER.info("List of children of address : " + address + ", found");
        return childAlertResult;
    }

    /**
     * This url allows to have the complete information of a person.
     * @param firstName : allows you to find the resource in the database.
     * @param lastName : allows you to find the resource in the database.
     * @return personInfoDTO and 200 Ok if successful
     * or 404 not found if it does not exist.
     */
    @GetMapping(value = "personInfo")
    public PersonInfoDTO getPersonInfo(
            @RequestParam final String firstName,
            @RequestParam final String lastName) {
        MedicalRecords medicalRecords
                = medicalRecordsS.getMedicalRecordName(firstName, lastName);
        PersonInfoDTO personInfoDTO
                = medicalRecordsM.modelToPersonInfoDTO(medicalRecords);
        LOGGER.info("Person information " + firstName
                + " " + lastName + " found");
        return personInfoDTO;
    }
}
