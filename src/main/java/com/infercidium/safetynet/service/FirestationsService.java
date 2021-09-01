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

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FirestationsService implements FirestationsI {

    /**
     * Instantiation of LOGGER in order to inform in console.
     */
    private static final Logger LOGGER
            = LoggerFactory.getLogger(FirestationsService.class);

    /**
     * Instantiation of FirestationsRepository.
     */
    private final FirestationsRepository firestationsR;
    /**
     * Instantiation of AddressService.
     */
    private final AddressI addressS;
    /**
     * Instantiation of PersonsService.
     */
    private final PersonsI personsS;
    /**
     * Instantiation of MedicalRecordsService.
     */
    private final MedicalRecordsI medicalRecordsS;

    /**
     * Class constructor.
     * @param firestationsRe this is FirestationsRepository.
     * @param addressSe this is AddressService.
     * @param personsSe this is PersonsService.
     * @param medicalRecordsSe this is MedicalRecordsService.
     */
    public FirestationsService(final FirestationsRepository firestationsRe,
                               final AddressI addressSe,
                               final PersonsI personsSe,
                               final MedicalRecordsI medicalRecordsSe) {
        this.firestationsR = firestationsRe;
        this.addressS = addressSe;
        this.personsS = personsSe;
        this.medicalRecordsS = medicalRecordsSe;
    }

    //Post, Put, Delete

    /**
     * Post Method Service.
     * @param firestations to save.
     * @return firestations saved.
     */
    @Override
    public Firestations postFirestation(final Firestations firestations)
            throws SQLIntegrityConstraintViolationException {
        firestations.setAddress(
                addressS.checkAddress(firestations.getAddress()));
        boolean duplicate = duplicateCheck(firestations);
        if (duplicate) {
            throw new SQLIntegrityConstraintViolationException();
        } else {
            return this.firestationsR.save(firestations);
        }
    }

    /**
     * Edit Method Service.
     * @param address to check Firestations.
     * @param station to check Firestations.
     * @param firestations to edit.
     */
    @Override
    public void editFirestation(final String address, final int station,
                                final Firestations firestations) {
        Firestations basicFirestation
                = firestationsR.findByAddressAddressIgnoreCaseAndStation(
                        address, station);
        firestations.setId(basicFirestation.getId());
        firestations.setAddress(basicFirestation.getAddress());
        this.firestationsR.save(firestations);
    }

    /**
     * RemoveStation Method Service.
     * @param station to check Firestations.
     */
    @Override
    public void removeStationMapping(final int station) {
        List<Firestations> firestations = getFireStationsStation(station);
        this.firestationsR.deleteAll(firestations);
    }

    /**
     * RemoveAddress Method Service.
     * @param address to check Firestations.
     */
    @Override
    public void removeAddressMapping(final String address) {
        List<Firestations> firestations = getFirestationsAddress(address);
        this.firestationsR.deleteAll(firestations);
    }
    //Get

    /**
     * Get Method Service.
     * @param address to check Firestations.
     * @return firestations checked.
     */
    @Override
    public List<Firestations> getFirestationsAddress(final String address) {
        List<Firestations> firestations
                = firestationsR.findByAddressAddressIgnoreCase(address);
        if (!firestations.isEmpty()) {
            return firestations;
        } else {
            throw new NullPointerException();
        }
    }

    /**
     * GetAll Method Service.
     * @param station to check Firestations.
     * @return firestations checked.
     */
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

    /**
     * Convert the list of Firestations to the list of Persons.
     * @param firestations : list of Firestations used.
     * @return list of Persons.
     */
    @Override
    public List<Persons> getFirestationsListToPersonsList(
            final List<Firestations> firestations) {
        List<Persons> personsList = new ArrayList<>();
        for (Firestations firestation : firestations) {
            List<Persons> persons = personsS
                    .getPersonsAddress(firestation.getAddress().getAddress());
            personsList.addAll(persons);
            LOGGER.debug("List of people linked to the station "
                    + firestation.getStation() + " by address : "
                    + firestation.getAddress().getAddress());
        }
        return personsList;
    }

    /**
     * Formatting the response for the StationNumber URL.
     * @param stationNumberDTO : list to count.
     * @return Map expected in result.
     */
    @Override
    public Map<String, Object> getStationNumberCount(
            final List<StationNumberDTO> stationNumberDTO) {
        Map<String, Object> stationNumber = new HashMap<>();
        int adult = 0;
        int child = 0;
        String residentString;
        String adultString;
        String childString;

        for (StationNumberDTO stationNumberDto : stationNumberDTO) {
            if (medicalRecordsS.checkMajority(
                    stationNumberDto.getFirstName(),
                    stationNumberDto.getLastName())) {
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
        LOGGER.debug("Counting and formatting "
                + "of the list of inhabitants covered");
        return stationNumber;
    }

    /**
     *Extract the phone from the Persons list in PersonsDTO.
     * @param persons : list of Persons used.
     * @return list of phone.
     */
    @Override
    public List<PersonsDTO> personsToPersonsdtoPhone(
            final List<Persons> persons) {
        List<PersonsDTO> personsDTO = new ArrayList<>();
        for (Persons person : persons) {
            PersonsDTO personsDto = new PersonsDTO();
            if (!phoneDuplicate(personsDTO, person.getPhone())) {
                personsDto.setPhone(person.getPhone());
                personsDTO.add(personsDto);
            }
        }
        LOGGER.debug("Retrieval of residents' telephone numbers");
        return personsDTO;
    }

    /**
     * Relay method.
     * @param address to check Persons.
     * @return list of Persons checked.
     */
    @Override
    public List<Persons> getFireResidents(final String address) {
        return personsS.getPersonsAddress(address);
    }

    /**
     * Convert list of Persons to list of MedicalRecords.
     * @param persons : list to convert.
     * @return list Medicalrecords.
     */
    @Override
    public List<MedicalRecords> getFireMedicalRecords(
            final List<Persons> persons) {
        List<MedicalRecords> medicalRecords = new ArrayList<>();
        for (Persons person :persons) {
            MedicalRecords medicalRecord
                    = medicalRecordsS.getMedicalRecordName(
                            person.getFirstName(), person.getLastName());
            medicalRecords.add(medicalRecord);
        }
        LOGGER.debug("List of Persons in MedicalRecords List");
        return medicalRecords;
    }

    /**
     * Formatting the response for the Fire URL.
     * @param station : station number.
     * @param fireDTO : list of inhabitants.
     * @return Map expected in result.
     */
    @Override
    public Map<String, Object> getFireResult(
            final List<Integer> station,
            final List<PersonsAndMedicalRecordsDTO> fireDTO) {
        Map<String, Object> fireResult = new HashMap<>();
        String residentString = fireDTO.size() > 1 ? "Residents" : "Resident";
        String stationString = station.size() > 1 ? "Stations" : "Station";
        fireResult.put(stationString, station);
        fireResult.put(residentString, fireDTO);
        return fireResult;
    }

    /**
     * Formatting step for the response for the Flood URL.
     * @param firestationsDTO : list of Firestations.
     * @return Map expected for final step of formatting.
     */
    @Override
    public Map<String, List<Persons>> getFloodResidents(
            final List<FirestationsDTO> firestationsDTO) {
        Map<String, List<Persons>> personsMap = new HashMap<>();
        for (FirestationsDTO firestationsDto :firestationsDTO) {
            String address = firestationsDto.getAddress();
            personsMap.put(address, personsS.getPersonsAddress(address));
        }
        LOGGER.debug("List of Persons by address");
        return personsMap;
    }

    /**
     * Formatting the response for the Flood URL.
     * @param personsMap : Map obtained containing the inhabitants
     *                   in a list of address key.
     * @return Map expected in result.
     */
    @Override
    public Map<String, List<MedicalRecords>> getFloodMedicalRecords(
            final Map<String, List<Persons>> personsMap) {
        Map<String, List<MedicalRecords>> medicalRecordsMap = new HashMap<>();
        for (Map.Entry<String, List<Persons>>
                personsList : personsMap.entrySet()) {
            List<MedicalRecords> medicalRecords = new ArrayList<>();
            String address = personsList.getKey();
            for (Persons person : personsList.getValue()) {
                MedicalRecords medicalRecord
                        = medicalRecordsS.getMedicalRecordName(
                                person.getFirstName(), person.getLastName());
                medicalRecords.add(medicalRecord);
            }
            medicalRecordsMap.put(address, medicalRecords);
        }
        return medicalRecordsMap;
    }

    //Method Tiers

    /**
     * Firestation Duplication Verification and Protection.
     * @param firestations : the one that is tested.
     * @return True if duplicate Firestations or False if new Firestations.
     */
    private boolean duplicateCheck(final Firestations firestations) {
        Firestations firestation = firestationsR
                .findByAddressAddressIgnoreCaseAndStation(
                        firestations.getAddress().getAddress(),
                        firestations.getStation());
        return firestation != null;
    }

    /**
     * Check if phone exists in PersonsDTO list.
     * @param personsDTO this is a list.
     * @param phone this is phone to verify.
     * @return True if phone exist in list or False if phone is new.
     */
    private boolean phoneDuplicate(final List<PersonsDTO> personsDTO,
                                   final String phone) {
        for (PersonsDTO personsDto : personsDTO) {
            if (personsDto.getPhone().equals(phone)) {
                return true;
            }
        }
        return false;
    }
}
