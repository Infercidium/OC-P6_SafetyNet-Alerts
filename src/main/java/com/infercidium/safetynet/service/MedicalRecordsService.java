package com.infercidium.safetynet.service;

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

@Service
public class MedicalRecordsService implements MedicalRecordsI {

    /**
     * Instantiation of LOGGER in order to inform in console.
     */
    private static final Logger LOGGER
            = LoggerFactory.getLogger(MedicalRecordsService.class);
    /**
     * Majorite constant.
     */
    private static final int MAJORITE = 18;

    /**
     * Instantiation of personsService.
     */
    private final PersonsI personsS;
    /**
     * Instantiation of SecondaryTableService.
     */
    private final MedicationsI medicationsS;
    /**
     * Instantiation of SecondaryTableService.
     */
    private final AllergiesI allergiesS;
    /**
     * Instantiation of MedicalRecordsRepository.
     */
    private final MedicalrecordsRepository medicalRecordsR;

    /**
     * Class constructor.
     * @param personsSe this is PersonsService.
     * @param medicalrecordsRe this is MedicalRecordsRepository.
     * @param medicationsSe this is MedicationsService.
     * @param allergiesSe this is AllergiesService.
     */
    public MedicalRecordsService(
            final PersonsI personsSe,
            final MedicalrecordsRepository medicalrecordsRe,
            final MedicationsI medicationsSe,
            final AllergiesI allergiesSe) {
        this.personsS = personsSe;
        this.medicalRecordsR = medicalrecordsRe;
        this.medicationsS = medicationsSe;
        this.allergiesS = allergiesSe;
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
        if (personsS.personCheck(firstName, lastName)) {
            medicalRecords.setPersons(
                    personsS.getPersonName(firstName, lastName));
        } else {
            throw new NullArgumentException("Null table binding");
        }
        medicalRecords.setAllergies(
                allergiesS.checkAllergies(
                        medicalRecords.getAllergies()));
        medicalRecords.setMedications(
                medicationsS.checkMedications(
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
    //URL lié à MedicalRecords

    /**
     * Check the person's age to find out if they are over majorite.
     * @param firstName to check medicalRecords Age.
     * @param lastName to check medicalRecords Age.
     * @return True if major and False if not.
     */
    @Override
    public boolean checkMajority(final String firstName,
                                 final String lastName) {
        MedicalRecords medicalRecords
                = getMedicalRecordName(firstName, lastName);
        LOGGER.debug("Checking if the person is over " + MAJORITE);
        return medicalRecords.getAge() > MAJORITE;
    }

    /**
     * Relay method.
     * @param address to check Persons.
     * @return list of Persons checked.
     */
    @Override
    public List<Persons> getPersonsAddress(final String address) {
        return personsS.getPersonsAddress(address);
    }

    /**
     * Formatting the response for the ChildAlert URL.
     * @param personsAndMedicalrecordsDTO : list of inhabitants.
     * @return Map expected in result.
     */
    @Override
    public Map<String, Object> getChildAlertCount(
          final List<PersonsAndMedicalRecordsDTO> personsAndMedicalrecordsDTO) {
        Map<String, Object> childAlert = new HashMap<>();
        List<PersonsAndMedicalRecordsDTO> adultPersonsAndMedicalRecordsDTO
                = new ArrayList<>();
        List<PersonsAndMedicalRecordsDTO> childPersonsAndMedicalRecordsDTO
                = new ArrayList<>();
        int adult = 0;
        int child = 0;
        String adultString;
        String childString;

        for (PersonsAndMedicalRecordsDTO
                personsAndMedicalrecordsDto : personsAndMedicalrecordsDTO) {
            MedicalRecords medicalRecords = getMedicalRecordName(
                    personsAndMedicalrecordsDto.getFirstName(),
                    personsAndMedicalrecordsDto.getLastName());
            personsAndMedicalrecordsDto.setPhone(null);
            if (checkMajority(medicalRecords.getFirstName(),
                    medicalRecords.getLastName())) {
                adult++;
                adultPersonsAndMedicalRecordsDTO
                        .add(personsAndMedicalrecordsDto);
            } else {
                personsAndMedicalrecordsDto.setAge(medicalRecords.getAge());
                child++;
                childPersonsAndMedicalRecordsDTO
                        .add(personsAndMedicalrecordsDto);
            }
        }

        adultString = adult > 1 ? "Adults" : "Adult";
        childString = child > 1 ? "Children" : "Child";
        childAlert.put(adultString, adultPersonsAndMedicalRecordsDTO);
        childAlert.put(childString, childPersonsAndMedicalRecordsDTO);
        LOGGER.debug("Counting and formatting of the list "
                + "of inhabitants covered");
        if (child == 0) {
            childAlert.clear();
        }
        return childAlert;
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
                    allergiesS.checkAllergies(
                            medicalRecordsChanged.getAllergies()));
        }
        if (medicalRecordsChanged.getMedications() == null) {
            medicalRecordsChanged.setMedications(
                    basicMedicalRecords.getMedications());
        } else {
            medicalRecordsChanged.setMedications(
                    medicationsS.checkMedications(
                            medicalRecordsChanged.getMedications()));
        }
        return medicalRecordsChanged;
    }
}
