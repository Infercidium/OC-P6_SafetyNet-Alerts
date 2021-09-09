package com.infercidium.safetynet.service;

import com.infercidium.safetynet.dto.PersonsDTO;
import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.Firestations;
import com.infercidium.safetynet.repository.FirestationsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * @param address to add.
     * @param firestations to save.
     * @return firestations saved.
     */
    @Override
    public Firestations createMapage(final Address address, final Firestations firestations) {
        Address addressComplete = addressS.checkAddress(address);
        Firestations firestation;
        if (stationCheck(firestations.getStation())) {
            firestation = firestationsR.findByStation(firestations.getStation());
        } else {
            firestation = firestations;
            firestation.getAddress().clear();
        }
        firestation.addAddress(addressComplete);
        return this.firestationsR.save(firestation);
    }

    /**
     * Edit Method Service.
     * @param address to check Firestations.
     * @param station to check Firestations.
     * @param firestations to edit.
     */
    @Override
    public void editFirestation(final String address, final int station, final Firestations firestations) {
        Address addressComplete = new Address(address);
        removeMapage(address, station);
        createMapage(addressComplete, firestations);
    }

    @Override
    public void removeMapage(final String address, final int station) {
        Address addressComplete = addressS.checkAddress(new Address(address));
        Firestations firestation = firestationsR.findByAddressAddressIgnoreCaseAndStation(address, station);
        firestation.removeAddress(addressComplete);
        if (firestation.getAddress().isEmpty()) {
            firestationsR.delete(firestation);
        } else {
            firestationsR.save(firestation);
        }
    }

    @Override
    public void removeStation(final int station) {
        Firestations firestation = getFirestationsStation(station);
        firestationsR.delete(firestation);
    }

    @Override
    public void removeAddress(final String address) {
        List<Firestations> firestationsList = getFirestationsAddress(address);
        for (Firestations firestation : firestationsList) {
            removeMapage(address, firestation.getStation());
        }
    }

    //Get

    /**
     * Get Method Service.
     * @param address to check Firestations.
     * @return firestations checked.
     */
    @Override
    public List<Firestations> getFirestationsAddress(final String address) {
        List<Firestations> firestations;
        if (address.equals("null")) {
            firestations = firestationsR.findAll();
        } else {
             firestations = firestationsR.findByAddressAddressIgnoreCase(address);
        }
        return firestations;
    }

    @Override
    public Firestations getFirestationsStation(final int station) {
        return firestationsR.findByStation(station);
    }

    //URL lié à Persons

    /**
     * Convert the list of Firestations to the list of Persons.
     * @param firestations : list of Firestations used.
     * @return list of Persons.
     */
    /*@Override
    public List<Persons> getFirestationsListToPersonsList(
            final List<Firestations> firestations) {
        List<Persons> personsList = new ArrayList<>();
        for (Firestations firestation : firestations) {
            List<Persons> persons = personsS.getPersonsAddress(firestation.getAddress());
            personsList.addAll(persons);
            LOGGER.debug("List of people linked to the station "
                    + firestation.getStation() + " by address : "
                    + firestation.getAddress());
        }
        return personsList;
    }*/

    /**
     * Formatting the response for the StationNumber URL.
     * @param stationNumberDTO : list to count.
     * @return Map expected in result.
     */
    /*@Override
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
    }*/

    /**
     *Extract the phone from the Persons list in PersonsDTO.
     * @param persons : list of Persons used.
     * @return list of phone.
     */
    /*@Override
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
    }*/

    /**
     * Relay method.
     * @param address to check Persons.
     * @return list of Persons checked.
     */
    /*@Override
    public List<Persons> getFireResidents(final String address) {
        return personsS.getPersonsAddress(address);
    }*/

    /**
     * Convert list of Persons to list of MedicalRecords.
     * @param persons : list to convert.
     * @return list Medicalrecords.
     */
    /*@Override
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
    }*/

    /**
     * Formatting the response for the Fire URL.
     * @param station : station number.
     * @param fireDTO : list of inhabitants.
     * @return Map expected in result.
     */
   /* @Override
    public Map<String, Object> getFireResult(
            final List<Integer> station,
            final List<PersonsAndMedicalRecordsDTO> fireDTO) {
        Map<String, Object> fireResult = new HashMap<>();
        String residentString = fireDTO.size() > 1 ? "Residents" : "Resident";
        String stationString = station.size() > 1 ? "Stations" : "Station";
        fireResult.put(stationString, station);
        fireResult.put(residentString, fireDTO);
        return fireResult;
    }*/

    /**
     * Formatting step for the response for the Flood URL.
     * @param firestationsDTO : list of Firestations.
     * @return Map expected for final step of formatting.
     */
    /*@Override
    public Map<String, List<Persons>> getFloodResidents(
            final List<FirestationsDTO> firestationsDTO) {
        Map<String, List<Persons>> personsMap = new HashMap<>();
        for (FirestationsDTO firestationsDto :firestationsDTO) {
            String address = firestationsDto.getAddress();
            personsMap.put(address, personsS.getPersonsAddress(address));
        }
        LOGGER.debug("List of Persons by address");
        return personsMap;
    }*/

    /**
     * Formatting the response for the Flood URL.
     * @param personsMap : Map obtained containing the inhabitants
     *                   in a list of address key.
     * @return Map expected in result.
     */
    /*@Override
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
    }*/

    //Method Tiers
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
    @Override
    public boolean mapageCheck(final String address, final int station) {
        return firestationsR.findByAddressAddressIgnoreCaseAndStation(address, station) != null;
    }

    @Override
    public boolean addressCheck(final String address) {
        return firestationsR.findByAddressAddressIgnoreCase(address) != null;
    }

    @Override
    public boolean stationCheck(final int station) {
        return firestationsR.findByStation(station) != null;
    }
}
