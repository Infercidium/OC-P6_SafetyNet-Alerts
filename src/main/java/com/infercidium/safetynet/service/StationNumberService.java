package com.infercidium.safetynet.service;

import com.infercidium.safetynet.constants.Majority;
import com.infercidium.safetynet.dto.PersonsDTO;
import com.infercidium.safetynet.model.Firestations;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * StationNumber Service develops public methods of interfaces,
 * and private methods.
 */
@Service
public class StationNumberService implements StationNumberI {

    /**
     * Instantiation of medicalRecordsInstance.
     */
    private final MedicalRecordsI medicalRecordsI;

    /**
     * Instantiation of personsInterface.
     */
    private final PersonsI personsI;

    /**
     * Instantiation of firestationsInterface.
     */
    private final FirestationsI firestationsI;

    /**
     *  Class constructor.
     * @param medicalRecordsIn this is medicalRecordsInterface.
     * @param personsIn this is personsInterface.
     * @param firestationsIn this is firestationsInterface.
     */
    public StationNumberService(final MedicalRecordsI medicalRecordsIn,
                                final PersonsI personsIn,
                                final FirestationsI firestationsIn) {
        this.medicalRecordsI = medicalRecordsIn;
        this.personsI = personsIn;
        this.firestationsI = firestationsIn;
    }

    /**
     * Formats the list of residents covered
     * by a station as well as the adult and child count.
     * @param station number.
     * @return the final Map expected by / firestation.
     */
    @Override
    public Map<String, Object> stationNumber(final int station) {
        //Found the Firestation
        Firestations firestation
                = firestationsI.getFirestationsStation(station);

        //Retrieved the list of persons linked to the Firestation
        List<Persons> personsList
                = personsI.addressSetToPersonsList(firestation.getAddress());

        //Counter
        long child = childCount(personsList);
        long adult = personsList.size() - child;

        //Formatting
        List<PersonsDTO> personsDTOList = personsToPersonsdto(personsList);

        String residentString
                = personsList.size() > 1 ? "Residents" : "Resident";
        String adultString = adult > 1 ? "Adults" : "Adult";
        String childString = child > 1 ? "Children" : "Child";

        Map<String, Object> result = new HashMap<>();
        result.put(adultString, adult);
        result.put(childString, child);
        result.put(residentString, personsDTOList);
        return result;
    }

    /**
     * Convert a persons to personsdto.
     * @param persons list to be converted.
     * @return personsDTO list.
     */
    private List<PersonsDTO> personsToPersonsdto(final List<Persons> persons) {
        List<PersonsDTO> personsDTO = new ArrayList<>();
        for (Persons person : persons) {
            PersonsDTO personsDto = new PersonsDTO();
            personsDto.setFirstName(person.getFirstName());
            personsDto.setLastName(person.getLastName());
            personsDto.setAddress(person.getAddress().getAddress());
            personsDto.setPhone(person.getPhone());
            personsDTO.add(personsDto);
        }
        return personsDTO;
    }

    /**
     * Counts the number of children in a list of persons.
     * @param persons list to be counted.
     * @return the count of the number of children on the list.
     */
    private long childCount(final List<Persons> persons) {
        List<MedicalRecords> medicalRecordsList = new ArrayList<>();
        for (Persons person : persons) {
            MedicalRecords medicalRecords = medicalRecordsI
                    .getMedicalRecordName(person.getFirstName(),
                            person.getLastName());
            medicalRecordsList.add(medicalRecords);
        }
        return medicalRecordsList.stream()
                .filter(medicalRecords -> medicalRecords
                        .getAge() <= Majority.MAJORITY)
                .count();
    }
}
