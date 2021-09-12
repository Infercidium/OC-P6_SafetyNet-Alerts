package com.infercidium.safetynet.service;

import com.infercidium.safetynet.constants.Majority;
import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.repository.MedicalrecordsRepository;
import org.apache.commons.lang.NullArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * MedicalRecords Service develops public methods of interfaces,
 * and private methods.
 */
@Service
public class MedicalRecordsService
        implements MedicalRecordsI, ChildAlertI, FireAndFloodI {

    /**
     * Instantiation of LOGGER in order to inform in console.
     */
    private static final Logger LOGGER
            = LoggerFactory.getLogger(MedicalRecordsService.class);

    /**
     * Instantiation of personsService.
     */
    private final PersonsI personsI;
    /**
     * Instantiation of SecondaryTableService.
     */
    private final MedicationsI medicationsI;
    /**
     * Instantiation of SecondaryTableService.
     */
    private final AllergiesI allergiesI;
    /**
     * Instantiation of MedicalRecordsRepository.
     */
    private final MedicalrecordsRepository medicalRecordsR;

    /**
     * Class constructor.
     * @param personsIn this is PersonsService.
     * @param medicalrecordsRe this is MedicalRecordsRepository.
     * @param medicationsIn this is MedicationsService.
     * @param allergiesIn this is AllergiesService.
     */
    public MedicalRecordsService(
            final PersonsI personsIn,
            final MedicalrecordsRepository medicalrecordsRe,
            final MedicationsI medicationsIn,
            final AllergiesI allergiesIn) {
        this.personsI = personsIn;
        this.medicalRecordsR = medicalrecordsRe;
        this.medicationsI = medicationsIn;
        this.allergiesI = allergiesIn;
    }

    //Post, Put, delete

    /**
     * Post Method Service.
     * @param medicalRecords to save.
     * @param firstName to check Persons.
     * @param lastName to check Persons.
     * @return medicalrecords saved.
     */
    @Override
    public MedicalRecords postMedicalRecords(
            final MedicalRecords medicalRecords,
            final String firstName,
            final String lastName) {
        if (personsI.personCheck(firstName, lastName)) {
            medicalRecords.setPersons(
                    personsI.getPersonName(firstName, lastName));
        } else {
            throw new NullArgumentException("Null table binding");
        }
        medicalRecords.setAllergies(
                allergiesI.checkAllergies(
                        medicalRecords.getAllergies()));
        medicalRecords.setMedications(
                medicationsI.checkMedications(
                        medicalRecords.getMedications()));
        return this.medicalRecordsR.save(medicalRecords);
    }

    /**
     * Edit Method Service.
     * @param firstName to check Medicalrecords.
     * @param lastName to check MedicalRecords.
     * @param medicalRecords to edit.
     */
    @Override
    public void editMedicalRecords(final String firstName,
                                   final String lastName,
                                   final MedicalRecords medicalRecords) {
        MedicalRecords basicMedicalRecords
                = getMedicalRecordName(firstName, lastName);
        MedicalRecords medicalRecord = medicalRecordsVerification(
                basicMedicalRecords, medicalRecords);
        this.medicalRecordsR.save(medicalRecord);
    }

    /**
     * Remove Method Service.
     * @param firstName to check MedicalRecords.
     * @param lastName to check MedicalRecords.
     */
    @Override
    public void removeMedicalRecords(final String firstName,
                                     final String lastName) {
        MedicalRecords medicalRecord
                = getMedicalRecordName(firstName, lastName);
        this.medicalRecordsR.delete(medicalRecord);
    }
    //Get

    /**
     * Get Method Service.
     * @param firstName to check MedicalRecords.
     * @param lastName to check MedicalRecords.
     * @return medicalrecords checked.
     */
    @Override
    public MedicalRecords getMedicalRecordName(final String firstName,
                                               final String lastName) {
        MedicalRecords medicalRecord
                = this.medicalRecordsR
                .findByPersonsFirstNameIgnoreCaseAndPersonsLastNameIgnoreCase(
                        firstName, lastName);
        if (medicalRecord != null) {
            return medicalRecord;
        } else {
            throw new NullPointerException();
        }
    }

    /**
     * GetAll Method Service.
     * @return all MedicalRecords.
     */
    @Override
    public List<MedicalRecords> getMedicalRecords() {
        List<MedicalRecords> medicalRecords = this.medicalRecordsR.findAll();
        if (medicalRecords.size() != 0) {
            return medicalRecords;
        } else {
            throw new NullPointerException();
        }
    }

    //Method Tiers

    /**
     * Returns values to the mandatory field null.
     * @param basicMedicalRecords : Original medicalRecords.
     * @param medicalRecordsChanged : Edited medicalRecords.
     * @return Edited medicalrecords verified.
     */
    private MedicalRecords medicalRecordsVerification(
            final MedicalRecords basicMedicalRecords,
            final MedicalRecords medicalRecordsChanged) {
        medicalRecordsChanged.setId(basicMedicalRecords.getId());
        medicalRecordsChanged.setPersons(basicMedicalRecords.getPersons());
        if (medicalRecordsChanged.getBirthdate() == null) {
            medicalRecordsChanged.setBirthdate(
                    basicMedicalRecords.getBirthdate());
        }
        if (medicalRecordsChanged.getAllergies() == null) {
            medicalRecordsChanged.setAllergies(
                    basicMedicalRecords.getAllergies());
        } else {
            medicalRecordsChanged.setAllergies(
                    allergiesI.checkAllergies(
                            medicalRecordsChanged.getAllergies()));
        }
        if (medicalRecordsChanged.getMedications() == null) {
            medicalRecordsChanged.setMedications(
                    basicMedicalRecords.getMedications());
        } else {
            medicalRecordsChanged.setMedications(
                    medicationsI.checkMedications(
                            medicalRecordsChanged.getMedications()));
        }
        return medicalRecordsChanged;
    }

    /**
     * Check if a station exists.
     * @param firstName to verify.
     * @param lastName to verify.
     * @return True if existing otherwise false.
     */
    @Override
    public boolean medicalRecordCheck(final String firstName,
                                      final String lastName) {
        MedicalRecords medicalRecords = medicalRecordsR
                .findByPersonsFirstNameIgnoreCaseAndPersonsLastNameIgnoreCase(
                        firstName, lastName);
        LOGGER.debug("Verification of the medicalRecord's existence");
        return medicalRecords != null;
    }

    //URL ChildAlert

    /**
     *Method which finds and formats childAlert lists.
     * @param address of the targeted residence.
     * @return Children List and Adult List
     * if Child list not empty.
     */
    @Override
    public Map<String, List<PersonsAndMedicalRecordsDTO>> childAlert(
            final String address) {
        Map<String, List<PersonsAndMedicalRecordsDTO>> result = new HashMap<>();
        List<MedicalRecords> medicalRecordsList = medicalRecordsR
                .findByPersonsAddressAddressIgnoreCase(address);
        if (medicalRecordsList.isEmpty()) {
            throw  new NullPointerException();
        }

        //Separation into two lists
        List<MedicalRecords> medicalRecordsChild
                = medicalRecordsList.stream()
                .filter(medicalRecords -> medicalRecords
                        .getAge() <= Majority.MAJORITY)
                .collect(Collectors.toList());
        if (medicalRecordsChild.isEmpty()) {
            return result;
        }
        List<MedicalRecords> medicalRecordsAdult
                = medicalRecordsList.stream()
                .filter(medicalRecords -> medicalRecords
                        .getAge() > Majority.MAJORITY)
                .collect(Collectors.toList());

        //Formatting
        List<PersonsAndMedicalRecordsDTO> child
                = medicalRecordstoChildResult(medicalRecordsChild);
        List<PersonsAndMedicalRecordsDTO> adult
                = medicalRecordstoAdultResult(medicalRecordsAdult);
        String adultString = adult.size() > 1 ? "Adults" : "Adult";
        String childString = child.size() > 1 ? "Children" : "Child";

        result.put(childString, child);
        result.put(adultString, adult);
        return result;
    }

    /**
     * Convert a list from MedicalRecords to PersonsAndMedicalRecordsDTO
     * for Child Alert, Child List.
     * @param medicalRecords List to convert.
     * @return List of PersonsAndMedicalRecordsDTO.
     */
    private List<PersonsAndMedicalRecordsDTO> medicalRecordstoChildResult(
            final List<MedicalRecords> medicalRecords) {
        List<PersonsAndMedicalRecordsDTO> result = new ArrayList<>();
        for (MedicalRecords medicalRecord : medicalRecords) {
            PersonsAndMedicalRecordsDTO personsAndMedicalRecordsDto
                    = new PersonsAndMedicalRecordsDTO();
            personsAndMedicalRecordsDto
                    .setFirstName(medicalRecord.getFirstName());
            personsAndMedicalRecordsDto
                    .setLastName(medicalRecord.getLastName());
            personsAndMedicalRecordsDto.setAge(medicalRecord.getAge());
            result.add(personsAndMedicalRecordsDto);
        }
        return result;
    }

    /**
     * Converts a list from MedicalRecords to PersonsAndMedicalRecordsDTO
     * for childAlert, adult list.
     * @param medicalRecords List to convert.
     * @return List of PersonsAndMedicalRecordsDTO.
     */
    private List<PersonsAndMedicalRecordsDTO> medicalRecordstoAdultResult(
            final List<MedicalRecords> medicalRecords) {
        List<PersonsAndMedicalRecordsDTO> result = new ArrayList<>();
        for (MedicalRecords medicalRecord : medicalRecords) {
            PersonsAndMedicalRecordsDTO personsAndMedicalRecordsDto
                    = new PersonsAndMedicalRecordsDTO();
            personsAndMedicalRecordsDto
                    .setFirstName(medicalRecord.getFirstName());
            personsAndMedicalRecordsDto
                    .setLastName(medicalRecord.getLastName());
            result.add(personsAndMedicalRecordsDto);
        }
        return result;
    }

    //URL Fire and Flood

    /**
     * Retrieves a list of MedicalRecords via a list of Persons.
     * @param personsList to convert.
     * @return medicalRecordsList.
     */
    @Override
    public List<MedicalRecords> personsListToMedicalRecordsList(
            final List<Persons> personsList) {
        List<MedicalRecords> medicalRecordsList = new ArrayList<>();
        for (Persons person : personsList) {
            MedicalRecords medicalRecords
                    = getMedicalRecordName(
                            person.getFirstName(),
                            person.getLastName());
            medicalRecordsList.add(medicalRecords);
        }
        return medicalRecordsList;
    }
}
