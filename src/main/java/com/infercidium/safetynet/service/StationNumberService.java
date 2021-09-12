package com.infercidium.safetynet.service;

import com.infercidium.safetynet.dto.PersonsDTO;
import com.infercidium.safetynet.model.Firestations;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StationNumberService implements StationNumberI {

    private final MedicalRecordsI medicalRecordsI;
    private final PersonsI personsI;
    private final FirestationsI firestationsI;

    public StationNumberService(final MedicalRecordsI medicalRecordsIn,
                                final PersonsI personsIn,
                                final FirestationsI firestationsIn) {
        this.medicalRecordsI = medicalRecordsIn;
        this.personsI = personsIn;
        this.firestationsI = firestationsIn;
    }


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

    private long childCount(final List<Persons> persons) {
        List<MedicalRecords> medicalRecordsList = new ArrayList<>();
        for (Persons person : persons) {
            MedicalRecords medicalRecords = medicalRecordsI
                    .getMedicalRecordName(person.getFirstName(),
                            person.getLastName());
            medicalRecordsList.add(medicalRecords);
        }
        return medicalRecordsList.stream()
                .filter(medicalRecords -> medicalRecords.getAge() <= 18)
                .count();
    }
}
