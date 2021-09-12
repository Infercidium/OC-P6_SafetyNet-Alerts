package com.infercidium.safetynet.service;

import com.infercidium.safetynet.dto.FirestationsAddressDTO;
import com.infercidium.safetynet.dto.FirestationsDTO;
import com.infercidium.safetynet.dto.MedicalRecordsDTO;
import com.infercidium.safetynet.dto.PersonsDTO;
import com.infercidium.safetynet.mapper.FirestationsMapper;
import com.infercidium.safetynet.mapper.MedicalRecordsMapper;
import com.infercidium.safetynet.mapper.PersonsMapper;
import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.Allergies;
import com.infercidium.safetynet.model.Firestations;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Medications;
import com.infercidium.safetynet.model.Persons;
import com.jsoniter.JsonIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class DataBaseInitService implements DataBaseInitI {

    /**
     * Instantiation of LOGGER in order to inform in console.
     */
    private static final Logger LOGGER
            = LoggerFactory.getLogger(DataBaseInitService.class);

    /**
     * Instantiation of FirestationsMapper.
     */
    private final FirestationsMapper firestationsM;
    /**
     * Instantiation of PersonsMapper.
     */
    private final PersonsMapper personsM;
    /**
     * Instantiation of MedicalRecordsMapper.
     */
    private final MedicalRecordsMapper medicalRecordsM;

    /**
     * Instantiation of FirestationService.
     */
    private final FirestationsI firestationsS;
    /**
     * Instantiation of PersonsService.
     */
    private final PersonsI personsI;
    /**
     * Instantiation of MedicalrecordsService.
     */
    private final MedicalRecordsI medicalRecordsI;

    /**
     * Class constructor.
     * @param firestationsMa this is FirestationsMapper.
     * @param personsMa this is PersonsMapper.
     * @param medicalRecordsMa this is MedicalRecordsMapper.
     * @param firestationsIn this is FirestationsService.
     * @param personsIn this is PersonsService.
     * @param medicalRecordsIn this is MedicalRecordsService.
     */
    public DataBaseInitService(final FirestationsMapper firestationsMa,
                               final PersonsMapper personsMa,
                               final MedicalRecordsMapper medicalRecordsMa,
                               final FirestationsI firestationsIn,
                               final PersonsI personsIn,
                               final MedicalRecordsI medicalRecordsIn) {
        this.firestationsM = firestationsMa;
        this.personsM = personsMa;
        this.medicalRecordsM = medicalRecordsMa;
        this.firestationsS = firestationsIn;
        this.personsI = personsIn;
        this.medicalRecordsI = medicalRecordsIn;
    }

    /**
     * File to string converter.
     * @param path of File.
     * @return contents of the file in String.
     */
    @Override
    public String convertFileToString(final String path) {
        StringBuilder data = new StringBuilder();
        try (InputStream inputStream = new FileInputStream(path);
             InputStreamReader inputStreamReader
                     = new InputStreamReader(inputStream, "UTF-8");
             BufferedReader bufferedReader
                     = new BufferedReader(inputStreamReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                data.append(line);
            }
        } catch (IOException e) {
            return String.valueOf(e);
        }
        return data.toString();
    }

    /**
     * Transforms a String into a Map.
     * @param data this is String.
     * @return a map containing the data of the String.
     */
    @Override
    public Map<String, Object> deserializeStringToMap(final String data) {
        Map<String, Object> passageList
                = JsonIterator.deserialize(data, Map.class);
        return passageList;
    }

    /**
     * Retrieves the values contained in a key from the listed map.
     * @param passageList : the value to list.
     * @param name : the key to the map.
     * @return a list.
     */
    @Override
    public List<Map<String, String>> convertMaptoList(
            final Map<String, Object> passageList, final String name) {
        List<Map<String, String>> list
                = (List<Map<String, String>>) passageList.get(name);
        return list;
    }

    /**
     * Retrieves the values contained in a key (MedicalRecords)
     * from the listed map.
     * @param passageList : the value to list.
     * @return a list.
     */
    @Override
    public List<Map<String, Object>> convertMedicalRecordsMaptoList(
            final Map<String, Object> passageList) {
        List<Map<String, Object>> list
                = (List<Map<String, Object>>) passageList.get("medicalrecords");
        return list;
    }

    /**
     *  Instantiating and saving Firestations.
     * @param firestations : the list to instantiate and save.
     */
    @Override
    public void instanciateListFirestations(
            final List<Map<String, String>> firestations)
            throws SQLIntegrityConstraintViolationException {

        for (Map<String, String> firestation : firestations) {
            FirestationsAddressDTO firestationsAddressDTO
                    = new FirestationsAddressDTO();
            firestationsAddressDTO.setAddress(firestation.get("address"));
            firestationsAddressDTO.setStation(
                    Integer.parseInt(firestation.get("station")));
            FirestationsDTO firestationsDTO
                    = new FirestationsDTO(firestationsAddressDTO);
            Firestations finalFirestation
                    = firestationsM.dtoToModel(firestationsDTO);
            Address address = new Address(firestation.get("address"));

            if (firestationsS.mapageCheck(address.getAddress(),
                    finalFirestation.getStation())) {
                LOGGER.debug("Mapping of address: " + address
                        + " and station: " + finalFirestation.getStation()
                        + " already existing detected, deletion of duplicate.");
            } else {
                firestationsS.createMapage(address, finalFirestation);
            }
        }
    }

    /**
     * Instantiating and saving Persons.
     * @param persons : the list to instantiate and save.
     */
    @Override
    public void instanciateListPersons(
            final List<Map<String, String>> persons) {
        for (Map<String, String> person : persons) {
            PersonsDTO personsDTO = new PersonsDTO();
            personsDTO.setFirstName(person.get("firstName"));
            personsDTO.setLastName(person.get("lastName"));
            personsDTO.setAddress(person.get("address"));
            personsDTO.setCity(person.get("city"));
            personsDTO.setZip(Integer.parseInt(person.get("zip")));
            personsDTO.setPhone(person.get("phone"));
            personsDTO.setEmail(person.get("email"));
            Persons finalPerson = personsM.dtoToModel(personsDTO);
            if (personsI.personCheck(
                    finalPerson.getFirstName(),
                    finalPerson.getLastName())) {
                LOGGER.debug(finalPerson.getFirstName()
                        + " " + finalPerson.getLastName()
                        + " detected in duplicate, deletion.");
            } else {
                personsI.postPerson(finalPerson);
            }
        }
    }

    /**
     * Instantiating and saving MedicalRecords.
     * @param medicalRecords : the list to instantiate and save.
     */
    @Override
    public void instanciateListMedicalRecords(
            final List<Map<String, Object>> medicalRecords) {
        for (Map<String, Object> medicalRecord : medicalRecords) {
            MedicalRecordsDTO medicalRecordsDTO = new MedicalRecordsDTO();
            medicalRecordsDTO.setFirstName(String.valueOf(medicalRecord
                    .get("firstName")));
            medicalRecordsDTO.setLastName(String.valueOf(medicalRecord
                    .get("lastName")));

            //Date Management
            DateTimeFormatter formatter
                    = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate localDate
                    = LocalDate.parse((CharSequence) medicalRecord
                    .get("birthdate"), formatter);
            medicalRecordsDTO.setBirthdate(localDate);

            //Set management
            medicalRecordsDTO.setMedications(
                    instanciateListMedications(
                            (List<String>) medicalRecord.get("medications")));
            medicalRecordsDTO.setAllergies(
                    instanciateListAllergies(
                            (List<String>) medicalRecord.get("allergies")));

            MedicalRecords finalMedicalRecord
                    = medicalRecordsM.dtoToModel(medicalRecordsDTO);

            //Possible error management
            if (medicalRecordsI.medicalRecordCheck(
                    medicalRecordsDTO.getFirstName(),
                    medicalRecordsDTO.getLastName())) {
                LOGGER.debug(medicalRecordsDTO.getFirstName() + " "
                        + medicalRecordsDTO.getLastName()
                        + " detected in duplicate, deletion.");
            } else if (!personsI.personCheck(
                    medicalRecordsDTO.getFirstName(),
                    medicalRecordsDTO.getLastName())) {
                LOGGER.debug("No Person to associate with MedicalRecords "
                        + medicalRecordsDTO.getFirstName() + " "
                        + medicalRecordsDTO.getLastName() + ", deletion.");
            } else {
                medicalRecordsI.postMedicalRecords(finalMedicalRecord,
                        medicalRecordsDTO.getFirstName(),
                        medicalRecordsDTO.getLastName());
            }
        }
    }

    /**
     * Instantiating and saving Medications.
     * @param medications : the list to instantiate and save.
     * @return the list once instantiate and save.
     */
    @Override
    public Set<Medications> instanciateListMedications(
            final List<String> medications) {
        Set<Medications> medicationsSet = new HashSet<>();
        for (String medication : medications) {
            Medications finalMedications = new Medications(medication);
            medicationsSet.add(finalMedications);
        }
        return medicationsSet;
    }

    /**
     * Instantiating and saving Allergies.
     * @param allergies : the list to instantiate and save.
     * @return the list once instantiate and save.
     */
    @Override
    public Set<Allergies> instanciateListAllergies(
            final List<String> allergies) {
        Set<Allergies> allergiesSet = new HashSet<>();
        for (String allergy : allergies) {
            Allergies finalAllergy = new Allergies(allergy);
            allergiesSet.add(finalAllergy);
        }
        return allergiesSet;
    }
}
