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

    private static final Logger LOGGER = LoggerFactory.getLogger(MedicalRecordsService.class);
    private static final int MAJORITE = 18;

    private final PersonsService personsS;
    private final MedicalrecordsRepository medicalRecordsR;
    private final SecondaryTableService secondaryTableS;

    public MedicalRecordsService(
            final PersonsService personsSe,
            final MedicalrecordsRepository medicalrecordsRe,
            final SecondaryTableService secondaryTableSe) {
        this.personsS = personsSe;
        this.medicalRecordsR = medicalrecordsRe;
        this.secondaryTableS = secondaryTableSe;
    }

    //Post, Put, delete
    @Override
    public MedicalRecords postMedicalRecords(final MedicalRecords medicalRecords, final String firstName, final String lastName) {
        if (personsS.personCheck(firstName, lastName)) {
            medicalRecords.setPersons(personsS.getPersonName(firstName, lastName));
        } else {
            throw new NullArgumentException("Null table binding");
        }
        medicalRecords.setAllergies(secondaryTableS.checkAllergies(medicalRecords.getAllergies()));
        medicalRecords.setMedications(secondaryTableS.checkMedications(medicalRecords.getMedications()));
        return this.medicalRecordsR.save(medicalRecords);
    }

    @Override
    public void editMedicalRecords(final String firstName, final String lastName, final MedicalRecords medicalRecords) {
        MedicalRecords basicMedicalRecords = getMedicalRecordName(firstName, lastName);
        MedicalRecords medicalRecord = medicalRecordsVerification(basicMedicalRecords, medicalRecords);
        this.medicalRecordsR.save(medicalRecord);
    }

    @Override
    public void removeMedicalRecords(final String firstName, final String lastName) {
        MedicalRecords medicalRecord = getMedicalRecordName(firstName, lastName);
        this.medicalRecordsR.delete(medicalRecord);
    }
    //Get
    @Override
    public MedicalRecords getMedicalRecordName(final String firstName, final String lastName) {
        MedicalRecords medicalRecord = this.medicalRecordsR.findByPersonsFirstNameIgnoreCaseAndPersonsLastNameIgnoreCase(firstName, lastName);
        if (medicalRecord != null) {
            return medicalRecord;
        } else {
            throw new NullPointerException();
        }
    }

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
    @Override
    public boolean checkMajority(final String firstName, final String lastName) {
        MedicalRecords medicalRecords = getMedicalRecordName(firstName, lastName);
        LOGGER.debug("Checking if the person is over " + MAJORITE);
        return medicalRecords.getAge() > MAJORITE;
    }

    @Override
    public List<Persons> getPersonsAddress(final String address) {
        return personsS.getPersonsAddress(address);
    }

    @Override
    public Map<String, Object> getChildAlertCount(final List<PersonsAndMedicalRecordsDTO> personsAndMedicalrecordsDTO) {
        Map<String, Object> childAlert = new HashMap<>();
        List<PersonsAndMedicalRecordsDTO> adultPersonsAndMedicalRecordsDTO = new ArrayList<>();
        List<PersonsAndMedicalRecordsDTO> childPersonsAndMedicalRecordsDTO = new ArrayList<>();
        int adult = 0;
        int child = 0;
        String adultString;
        String childString;

        for (PersonsAndMedicalRecordsDTO personsAndMedicalrecordsDto : personsAndMedicalrecordsDTO) {
            MedicalRecords medicalRecords = getMedicalRecordName(personsAndMedicalrecordsDto.getFirstName(), personsAndMedicalrecordsDto.getLastName());
            personsAndMedicalrecordsDto.setPhone(null);
            if (checkMajority(medicalRecords.getFirstName(), medicalRecords.getLastName())) {
                adult++;
                adultPersonsAndMedicalRecordsDTO.add(personsAndMedicalrecordsDto);
            } else {
                personsAndMedicalrecordsDto.setAge(medicalRecords.getAge());
                child++;
                childPersonsAndMedicalRecordsDTO.add(personsAndMedicalrecordsDto);
            }
        }

        adultString = adult > 1 ? "Adults" : "Adult";
        childString = child > 1 ? "Children" : "Child";
        childAlert.put(adultString, adultPersonsAndMedicalRecordsDTO);
        childAlert.put(childString, childPersonsAndMedicalRecordsDTO);
        LOGGER.debug("Counting and formatting of the list of inhabitants covered");
        if (child == 0) {
            childAlert.clear();
        }
        return childAlert;
    }

    //Method Tiers
    private MedicalRecords medicalRecordsVerification(final MedicalRecords basicMedicalRecords, final MedicalRecords medicalRecordsChanged) {
        medicalRecordsChanged.setId(basicMedicalRecords.getId());
        medicalRecordsChanged.setPersons(basicMedicalRecords.getPersons());
        if (medicalRecordsChanged.getBirthdate() == null) {
            medicalRecordsChanged.setBirthdate(basicMedicalRecords.getBirthdate());
        }
        if (medicalRecordsChanged.getAllergies() == null) {
            medicalRecordsChanged.setAllergies(basicMedicalRecords.getAllergies());
        } else {
            medicalRecordsChanged.setAllergies(secondaryTableS.checkAllergies(medicalRecordsChanged.getAllergies()));
        }
        if (medicalRecordsChanged.getMedications() == null) {
            medicalRecordsChanged.setMedications(basicMedicalRecords.getMedications());
        } else {
            medicalRecordsChanged.setMedications(secondaryTableS.checkMedications(medicalRecordsChanged.getMedications()));
        }
        return medicalRecordsChanged;
    }
}
