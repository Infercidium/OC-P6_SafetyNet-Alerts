package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.Allergies;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Medications;
import com.infercidium.safetynet.repository.AllergiesRepository;
import com.infercidium.safetynet.repository.MedicalrecordsRepository;
import com.infercidium.safetynet.repository.MedicationsRepository;
import org.apache.commons.lang.NullArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MedicalRecordsService implements MedicalRecordsI {

    private final PersonsService personsS;
    private static final Logger LOGGER = LoggerFactory.getLogger(MedicalRecordsService.class);

    private final MedicalrecordsRepository medicalrecordsR;
    private final MedicationsRepository medicationsR;
    private final AllergiesRepository allergiesR;

    public MedicalRecordsService(final PersonsService personsSe, final MedicalrecordsRepository medicalrecordsRe, final MedicationsRepository medicationsRe, final AllergiesRepository allergiesRe) {
        this.personsS = personsSe;
        this.medicalrecordsR = medicalrecordsRe;
        this.medicationsR = medicationsRe;
        this.allergiesR = allergiesRe;
    }

    //Post, Put, delete
    @Override
    public MedicalRecords createMedicalRecords(final MedicalRecords medicalRecords) {
        MedicalRecords verify = medicalRecords;
        if (personsS.personCheck(medicalRecords.getFirstName(), medicalRecords.getLastName())) {
            medicalRecords.setPersons(personsS.getPerson(medicalRecords.getFirstName(), medicalRecords.getLastName()).get(0));
        } else {
            throw new NullArgumentException("Null table binding");
        }
        SecondaryTableService sts = new SecondaryTableService(medicationsR, allergiesR);
            Set<Medications> medicationsSet = sts.checkMedicationMedicalRecords(medicalRecords.getMedications());
            verify.setMedications(medicationsSet);
            Set<Allergies> allergiesSet = sts.checkAllergieMedicalRecords(medicalRecords.getAllergies());
            verify.setAllergies(allergiesSet);

            MedicalRecords finalMedicalRecords = this.medicalrecordsR.save(verify);
            return finalMedicalRecords;

    }

    @Override
    public void editMedicalRecords(final String firstName, final String lastName, final MedicalRecords medicalRecords) {
        MedicalRecords basicMedicalRecords = getMedicalRecord(firstName, lastName).get(0);
        MedicalRecords medicalRecordsChanged = medicalRecords;
        medicalRecordsChanged.setId(basicMedicalRecords.getId());
        MedicalRecords medicalRecord = medicalRecordsVerification(basicMedicalRecords, medicalRecordsChanged);
        this.medicalrecordsR.save(medicalRecord);
    }

    @Override
    public void removeMedicalRecords(
            final String firstName, final String lastName) {
        List<MedicalRecords> medicalRecord = getMedicalRecord(firstName, lastName);
        this.medicalrecordsR.delete(medicalRecord.get(0));
    }

    //Get
    @Override
    public List<MedicalRecords> getMedicalRecord(final String firstName, final String lastName) {
        Optional<MedicalRecords> medicalRecord = this.medicalrecordsR.findByFirstNameIgnoreCaseAndLastNameIgnoreCase(firstName, lastName);
        if (medicalRecord.isPresent()) {
            List<MedicalRecords> medicalRecordList = new ArrayList<>();
            medicalRecordList.add(medicalRecord.get());
            return medicalRecordList;
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public List<MedicalRecords> getMedicalRecords() {
        List<MedicalRecords> medicalRecords = this.medicalrecordsR.findAll();
        if (medicalRecords.size() != 0) {
            return medicalRecords;
        } else {
            throw new NullPointerException();
        }
    }


    //URL lié à MedicalRecords

    public List<MedicalRecords> getChildAlertChild(final String address) {
        List<MedicalRecords> medicalRecords = medicalrecordsR.findByPersonsAddressIgnoreCase(address);
        List<MedicalRecords> result = new ArrayList<>();
        for(MedicalRecords medicalRecord : medicalRecords) {
            if(medicalRecord.getAge() < 19) {
                result.add(medicalRecord);
            }
        }
        return result;
    }

    public List<MedicalRecords> getChildAlertAdult(final String address) {
        List<MedicalRecords> medicalRecords = medicalrecordsR.findByPersonsAddressIgnoreCase(address);
        List<MedicalRecords> result = new ArrayList<>();
        for(MedicalRecords medicalRecord : medicalRecords) {
            if(medicalRecord.getAge() > 18) {
                result.add(medicalRecord);
            }
        }
        return result;
    }
/*
    @Override
    public MappingJacksonValue getPersonInfo(final String firstName,
                                             final String lastName) {
        Optional<MedicalRecords> medicalRecords = this.medicalrecordsR
                .findByFirstNameIgnoreCaseAndLastNameIgnoreCase(
                        firstName, lastName);
        if (!medicalRecords.isPresent()) {
            throw new NullPointerException();
        }
        Set<String> attribute = newHashSet("firstName", "lastName", "age",
                "medications", "allergies", "persons");
        SimpleBeanPropertyFilter medicalRecordFilter
                = SimpleBeanPropertyFilter.filterOutAllExcept(attribute);
        Set<String> attributeP = newHashSet("address", "email");
        SimpleBeanPropertyFilter personFilter
                = SimpleBeanPropertyFilter.filterOutAllExcept(attributeP);
        FilterProvider listFilter = new SimpleFilterProvider()
                .addFilter("MedicalRecordFilter", medicalRecordFilter)
                .addFilter("PersonFilter", personFilter);
        MappingJacksonValue personInfo
                = new MappingJacksonValue(medicalRecords);
        personInfo.setFilters(listFilter);
        LOGGER.debug("Applying filters " + attribute);
        LOGGER.debug("Applying person filters " + attributeP);
        return personInfo;
    }*/

    //Methode tiers
    private MedicalRecords medicalRecordsVerification(
            final MedicalRecords basicMedicalRecords,
            final MedicalRecords medicalRecordsChanged) {
        medicalRecordsChanged.setFirstName(basicMedicalRecords.getFirstName());
        medicalRecordsChanged.setLastName(basicMedicalRecords.getLastName());
        medicalRecordsChanged.setPersons(basicMedicalRecords.getPersons());
        SecondaryTableService sts
                = new SecondaryTableService(medicationsR, allergiesR);
        Set<Medications> medicationsSet
                = sts.checkMedicationMedicalRecords(
                        medicalRecordsChanged.getMedications());
        medicalRecordsChanged.setMedications(medicationsSet);
        Set<Allergies> allergiesSet
                = sts.checkAllergieMedicalRecords(
                        medicalRecordsChanged.getAllergies());
        medicalRecordsChanged.setAllergies(allergiesSet);
        if (medicalRecordsChanged.getBirthdate() == null) {
            medicalRecordsChanged
                    .setBirthdate(basicMedicalRecords.getBirthdate());
        }
        return medicalRecordsChanged;
    }
}
