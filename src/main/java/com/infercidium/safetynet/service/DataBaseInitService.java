package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.Allergies;
import com.infercidium.safetynet.model.Firestations;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Medications;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.repository.AllergiesRepository;
import com.infercidium.safetynet.repository.FirestationsRepository;
import com.infercidium.safetynet.repository.MedicalrecordsRepository;
import com.infercidium.safetynet.repository.MedicationsRepository;
import com.infercidium.safetynet.repository.PersonsRepository;
import com.jsoniter.JsonIterator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataBaseInitService implements DataBaseInitI {

    private final PersonsRepository personR;
    private final FirestationsRepository firestationsR;
    private final MedicalrecordsRepository medicalrecordsR;
    private final MedicationsRepository medicationsR;
    private final AllergiesRepository allergiesR;

    public DataBaseInitService(final PersonsRepository personRe,
                               final FirestationsRepository firestationsRe,
                               final MedicalrecordsRepository medicalrecordsRe,
                               final MedicationsRepository medicationsRe,
                               final AllergiesRepository allergiesRe) {
        this.personR = personRe;
        this.firestationsR = firestationsRe;
        this.medicalrecordsR = medicalrecordsRe;
        this.medicationsR = medicationsRe;
        this.allergiesR = allergiesRe;
    }

    @Override
    public String convertFileToString(final String path) {
        StringBuilder data = new StringBuilder();
        String datatemp;
        BufferedReader readBuffer = null;
        try {
            readBuffer = new BufferedReader(new FileReader(path));
            while ((datatemp = readBuffer.readLine()) != null) {
                data.append(datatemp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (readBuffer != null) {
                    readBuffer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data.toString();
    }

    @Override
    public Map<String, Object> deserializeStringToMap(final String data) {
        Map<String, Object> passageList
                = JsonIterator.deserialize(data, Map.class);
        return passageList;
    }

    @Override
    public List<Map<String, String>> convertMaptoList(
            final Map<String, Object> passageList, final String name) {
        List<Map<String, String>> list
                = (List<Map<String, String>>) passageList.get(name);
        return list;
    }

    @Override
    public List<Map<String, Object>> convertMedicalRecordsMaptoList(
            final Map<String, Object> passageList) {
        List<Map<String, Object>> list
                = (List<Map<String, Object>>) passageList.get("medicalrecords");
        return list;
    }

    @Override
    public List<Persons> instantiateListPersons(
            final List<Map<String, String>> persons) {
        List<Persons> personsList = new ArrayList<>();
        for (Map<String, String> person : persons) {
            Persons person1 = new Persons();
            person1.setFirstName(person.get("firstName"));
            person1.setLastName(person.get("lastName"));
            person1.setAddress(person.get("address"));
            person1.setCity(person.get("city"));
            person1.setZip(Integer.parseInt(person.get("zip")));
            person1.setPhone(person.get("phone"));
            person1.setEmail(person.get("email"));
            personsList.add(person1);
        }
        return personsList;
    }

    @Override
    public List<Firestations> instantiateListFirestations(
            final List<Map<String, String>> firestations) {
        List<Firestations> firestationsList = new ArrayList<>();
        for (Map<String, String> firestation : firestations) {
            Firestations firestation1 = new Firestations();
            firestation1.setAddress(firestation.get("address"));
            firestation1.setStation(
                    Integer.parseInt(firestation.get("station")));
            firestationsList.add(firestation1);
        }
        return firestationsList;
    }

    @Override
    public List<MedicalRecords> instantiateListMedicalRecords(
            final List<Map<String, Object>> medicalRecords) {
        List<MedicalRecords> medicalRecordsList = new ArrayList<>();
        SecondaryTableService sts
                = new SecondaryTableService(medicationsR, allergiesR);
        for (Map<String, Object> medicalRecord : medicalRecords) {
            MedicalRecords medicalRecords1 = new MedicalRecords();
            medicalRecords1.setFirstName(
                    (String) medicalRecord.get("firstName"));
            medicalRecords1.setLastName((String) medicalRecord.get("lastName"));
            DateTimeFormatter formatter
                    = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate localDate
                    = LocalDate.parse((CharSequence) medicalRecord
                    .get("birthdate"), formatter);
            medicalRecords1.setBirthdate(localDate);
            HashSet<Medications> medicationRecord
                    = new HashSet<>(sts.checkMedicationMedicalRecords(
                            (List<String>) medicalRecord.get("medications")));
            medicalRecords1.setMedications(medicationRecord);
            HashSet<Allergies> allergiesRecord
                    = new HashSet<>(sts.checkAllergieMedicalRecords(
                            (List<String>) medicalRecord.get("allergies")));
            medicalRecords1.setAllergies(allergiesRecord);
            medicalRecordsList.add(medicalRecords1);
        }
        return medicalRecordsList;
    }

    @Override
    public void saveAllList(final List<Persons> persons,
                            final List<Firestations> firestations,
                            final List<MedicalRecords> medicalRecords) {
        this.personR.saveAll(persons);
        this.firestationsR.saveAll(firestations);
        List<MedicalRecords> medicalRecordsConnect = connectMedicalRecordsToPersons(medicalRecords);
        this.medicalrecordsR.saveAll(medicalRecordsConnect);
    }

    @Override
    public List<MedicalRecords> connectMedicalRecordsToPersons(final List<MedicalRecords> medicalRecords) {
        List<MedicalRecords> medicalRecordsList = new ArrayList<>();
        for(MedicalRecords medicalRecord : medicalRecords) {
           Optional<Persons> person = personR.findByFirstNameIgnoreCaseAndLastNameIgnoreCase(medicalRecord.getFirstName(), medicalRecord.getLastName());
           medicalRecord.setPersons(person.get());
           medicalRecordsList.add(medicalRecord);
        }
        return medicalRecordsList;
    }
}
