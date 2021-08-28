package com.infercidium.safetynet.service;

import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.dto.FirestationsDTO;
import com.infercidium.safetynet.dto.PersonsDTO;
import com.infercidium.safetynet.dto.StationNumberDTO;
import com.infercidium.safetynet.model.Firestations;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.repository.FirestationsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FirestationsService implements FirestationsI {

    private static final Logger LOGGER = LoggerFactory.getLogger(FirestationsService.class);
    private final FirestationsRepository firestationsR;
    private final SecondaryTableService secondaryTableS;
    private final PersonsService personsS;
    private final MedicalRecordsService medicalRecordsS;

    public FirestationsService(final FirestationsRepository firestationsRe, final SecondaryTableService secondaryTableSe, final PersonsService personsSe, final MedicalRecordsService medicalRecordsSe) {
        this.firestationsR = firestationsRe;
        this.secondaryTableS = secondaryTableSe;
        this.personsS = personsSe;
        this.medicalRecordsS = medicalRecordsSe;
    }

    //Post, Put, Delete
    @Override
    public Firestations postFirestation(final Firestations firestations) {
        firestations.setAddress(secondaryTableS.checkAddress(firestations.getAddress()));
        return this.firestationsR.save(firestations);
    }

    @Override
    public void editFirestation(final String address, final Firestations firestations) {
        Firestations basicFirestation = getFirestationsAddress(address);
        firestations.setId(basicFirestation.getId());
        firestations.setAddress(basicFirestation.getAddress());
        this.firestationsR.save(firestations);
    }

    @Override
    public void removeStationMapping(final int station) {
        List<Firestations> firestations = getFireStationsStation(station);
        this.firestationsR.deleteAll(firestations);
    }

    @Override
    public void removeAddressMapping(final String address) {
        Firestations firestations = getFirestationsAddress(address);
        this.firestationsR.delete(firestations);
    }
    //Get
    @Override
    public Firestations getFirestationsAddress(final String address) {
        Firestations firestations = firestationsR.findByAddressAddressIgnoreCase(address);
        if (firestations != null) {
            return firestations;
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public List<Firestations> getFireStationsStation(final int station) {
        List<Firestations> firestations;
        if (station == 0) {
            firestations = this.firestationsR.findAll();
        } else {
            firestations = this.firestationsR.findByStation(station);
        }
        if (firestations.size() != 0) {
            return firestations;
        } else {
            throw new NullPointerException();
        }
    }

    //URL lié à Persons
    @Override
    public List<Persons> getFirestationsListToPersonsList(final List<Firestations> firestations) {
        List<Persons> personsList = new ArrayList<>();
        for (Firestations firestation : firestations) {
            List<Persons> persons = personsS.getPersonsAddress(firestation.getAddress().getAddress());
            personsList.addAll(persons);
            LOGGER.debug("List of people linked to the station " + firestation.getStation() + " by address : " + firestation.getAddress().getAddress());
        }
        return personsList;
    }

    @Override
    public Map<String, Object> getStationNumberCount(final List<StationNumberDTO> stationNumberDTO) {
        Map<String, Object> stationNumber = new HashMap<>();
        int adult = 0;
        int child = 0;
        String residentString;
        String adultString;
        String childString;

        for (StationNumberDTO stationNumberDto : stationNumberDTO) {
            if (medicalRecordsS.checkMajority(stationNumberDto.getFirstName(), stationNumberDto.getLastName())) {
                adult++;
            } else {
                child++;
            }
        }

        residentString = stationNumberDTO.size() > 1 ? "Residents" : "Resident";
        adultString = adult > 1 ? "Adults" : "Adult";
        childString = child > 1 ? "Children" : "Child";
        stationNumber.put(residentString, stationNumberDTO);
        stationNumber.put(adultString, adult);
        stationNumber.put(childString, child);
        LOGGER.debug("Counting and formatting of the list of inhabitants covered");
        return stationNumber;
    }

    @Override
    public List<PersonsDTO> personsToPersonsdtoPhone(final List<Persons> persons) {
        List<PersonsDTO> personsDTO = new ArrayList<>();
        for (Persons person : persons) {
            PersonsDTO personsDto = new PersonsDTO();
            personsDto.setPhone(person.getPhone());
            personsDTO.add(personsDto);
        }
        LOGGER.debug("Retrieval of residents' telephone numbers");
        return personsDTO;
    }

    @Override
    public List<Persons> getFireResidents(final String address) {
        return personsS.getPersonsAddress(address);
    }

    @Override
    public List<MedicalRecords> getFireMedicalRecords(final List<Persons> persons) {
        List<MedicalRecords> medicalRecords = new ArrayList<>();
        for (Persons person :persons) {
            MedicalRecords medicalRecord = medicalRecordsS.getMedicalRecordName(person.getFirstName(), person.getLastName());
            medicalRecords.add(medicalRecord);
        }
        LOGGER.debug("List of Persons in MedicalRecords List");
        return medicalRecords;
    }

    @Override
    public Map<String, Object> getFireResult(final Integer station, final List<PersonsAndMedicalRecordsDTO> fireDTO) {
        Map<String, Object> fireResult = new HashMap<>();
        String residentString = fireDTO.size() > 1 ? "Residents" : "Resident";
        fireResult.put("Station", station);
        fireResult.put(residentString, fireDTO);
        return fireResult;
    }

    @Override
    public Map<String, List<Persons>> getFloodResidents(final List<FirestationsDTO> firestationsDTO) {
        Map<String, List<Persons>> personsMap = new HashMap<>();
        for (FirestationsDTO firestationsDto :firestationsDTO) {
            String address = firestationsDto.getAddress();
            personsMap.put(address, personsS.getPersonsAddress(address));
        }
        LOGGER.debug("List of Persons by address");
        return personsMap;
    }

    @Override
    public Map<String, List<MedicalRecords>> getFloodMedicalRecords(final Map<String, List<Persons>> personsMap) {
        Map<String, List<MedicalRecords>> medicalRecordsMap = new HashMap<>();
        for (Map.Entry<String, List<Persons>> personsList : personsMap.entrySet()) {
            List<MedicalRecords> medicalRecords = new ArrayList<>();
            String address = personsList.getKey();
            for (Persons person : personsList.getValue()) {
                MedicalRecords medicalRecord = medicalRecordsS.getMedicalRecordName(person.getFirstName(), person.getLastName());
                medicalRecords.add(medicalRecord);
            }
            medicalRecordsMap.put(address, medicalRecords);
        }
        return medicalRecordsMap;
    }
}
