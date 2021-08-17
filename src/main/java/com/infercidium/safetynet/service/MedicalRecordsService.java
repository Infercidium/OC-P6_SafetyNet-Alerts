package com.infercidium.safetynet.service;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.infercidium.safetynet.model.Allergies;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Medications;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.repository.AllergiesRepository;
import com.infercidium.safetynet.repository.MedicalrecordsRepository;
import com.infercidium.safetynet.repository.MedicationsRepository;
import org.apache.commons.lang.NullArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Service
public class MedicalRecordsService implements MedicalRecordsI {

    private final PersonsService personsS;
    private static final Logger LOGGER
            = LoggerFactory.getLogger(MedicalRecordsService.class);
    private final MedicalrecordsRepository medicalrecordsR;
    private final MedicationsRepository medicationsR;
    private final AllergiesRepository allergiesR;
    public MedicalRecordsService(
            final PersonsService personsSe, final MedicalrecordsRepository medicalrecordsRe,
            final MedicationsRepository medicationsRe,
            final AllergiesRepository allergiesRe) {
        this.personsS = personsSe;
        this.medicalrecordsR = medicalrecordsRe;
        this.medicationsR = medicationsRe;
        this.allergiesR = allergiesRe;
    }

    //Post, Put, delete
    @Override
    public ResponseEntity<Void> createMedicalRecords(
            final MedicalRecords medicalRecords) throws NullArgumentException {
        MedicalRecords verify = medicalRecords;
        if (personsS.personCheck(medicalRecords.getFirstName(), medicalRecords.getLastName())) {
            medicalRecords.setPersons((Persons) personsS.getPerson(medicalRecords.getFirstName(), medicalRecords.getLastName()).get(0));
        } else {
            throw new NullArgumentException("Null table binding");
        }
        SecondaryTableService sts
                = new SecondaryTableService(medicationsR, allergiesR);
            Set<Medications> medicationsSet
                    = sts.checkMedicationMedicalRecords(
                            medicalRecords.getMedications());
            verify.setMedications(medicationsSet);
            Set<Allergies> allergiesSet
                    = sts.checkAllergieMedicalRecords(
                            medicalRecords.getAllergies());
            verify.setAllergies(allergiesSet);

            MedicalRecords result = this.medicalrecordsR.save(verify);
            URI locate = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{firstName}/{lastName}")
                    .buildAndExpand(result.getFirstName(), result.getLastName())
                    .toUri();
            return ResponseEntity.created(locate).build();
    }

    @Override
    public ResponseEntity<Void> editMedicalRecords(
            final String firstName,
            final String lastName,
            final MedicalRecords medicalRecords) {
        MedicalRecords basicMedicalRecords = getMedicalRecord(firstName, lastName).get(0);
        MedicalRecords medicalRecordsChanged = medicalRecords;
        medicalRecordsChanged.setId(basicMedicalRecords.getId());
        MedicalRecords medicalRecord
                = medicalRecordsVerification(basicMedicalRecords, medicalRecordsChanged);
        this.medicalrecordsR.save(medicalRecord);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> removeMedicalRecords(
            final String firstName, final String lastName) {
        List<MedicalRecords> medicalRecord = getMedicalRecord(firstName, lastName);
        this.medicalrecordsR.delete(medicalRecord.get(0));
        return ResponseEntity.ok().build();
    }

    //Get
    @Override
    public List<MedicalRecords> getMedicalRecord(
            final String firstName, final String lastName) {
        Optional<MedicalRecords> medicalRecord = this.medicalrecordsR
                .findByFirstNameIgnoreCaseAndLastNameIgnoreCase(
                        firstName, lastName);
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
    @Override
    public MappingJacksonValue getChildAlert(String address) {
        List<MedicalRecords> child = this.medicalrecordsR.findByPersonsAddressIgnoreCase(address);
        int childNumber = child.size();
        Collections.sort(child, new Comparator<MedicalRecords>() {
            @Override
            public int compare(MedicalRecords o1, MedicalRecords o2) {
                return o2.getBirthdate().compareTo(o1.getBirthdate());
            }
        });
        for(MedicalRecords medicalRecord : child) {
            if (medicalRecord.getAge() > 18) {
                childNumber --;
            }
        }
        if (childNumber == 0) {
            child.clear();
            LOGGER.debug("No child");
        } else if (childNumber == 1) {
            LOGGER.debug("1 child");
        } else {
            LOGGER.debug(childNumber + " children");
        }
        Set<String> attribute = new HashSet<>();
        attribute.add("firstName");
        attribute.add("lastName");
        attribute.add("age");
        MappingJacksonValue childResult = medicalRecordFilterAdd(child, attribute);
        return childResult;
    }
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
    private MappingJacksonValue medicalRecordFilterAdd(final List<MedicalRecords> medicalRecords, final Set<String> attribute) {
        SimpleBeanPropertyFilter medicalRecordFilter = SimpleBeanPropertyFilter.filterOutAllExcept(attribute);
        FilterProvider listFilter = new SimpleFilterProvider().addFilter("MedicalRecordFilter", medicalRecordFilter);
        MappingJacksonValue filterMedicalRecords = new MappingJacksonValue(medicalRecords);
        filterMedicalRecords.setFilters(listFilter);
        LOGGER.debug("Applying filters " + attribute);
        return filterMedicalRecords;
    }

    public MappingJacksonValue medicalRecordFilterNull(final List<MedicalRecords> medicalRecords) {
        Set<String> nul = new HashSet<>();
        nul.add("persons");
        SimpleBeanPropertyFilter medicalRecordFilter = SimpleBeanPropertyFilter.serializeAllExcept(nul);
        FilterProvider listFilter = new SimpleFilterProvider().addFilter("MedicalRecordFilter", medicalRecordFilter);
        MappingJacksonValue filterMedicalRecords = new MappingJacksonValue(medicalRecords);
        filterMedicalRecords.setFilters(listFilter);
        return filterMedicalRecords;
    }
}
