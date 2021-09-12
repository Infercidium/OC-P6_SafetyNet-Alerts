package com.infercidium.safetynet.service;

import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.Firestations;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class FireService implements FireI {
    private final PersonsI personsI;
    private final FirestationsI firestationsI;
    private final FireAndFloodI fireAndFloodI;

    public FireService(final PersonsI personsIn,
                       final FirestationsI firestationsIn,
                       final FireAndFloodI fireAndFloodIn) {
        this.personsI = personsIn;
        this.firestationsI = firestationsIn;
        this.fireAndFloodI = fireAndFloodIn;
    }
    @Override
    public List<MedicalRecords> getFireMedicalRecords(final String address) {
        Set<Address> addressSet = new HashSet<>();
        addressSet.add(new Address(address));
        List<Persons> personsList
                = personsI.addressSetToPersonsList(addressSet);
        return fireAndFloodI.personsListToMedicalRecordsList(personsList);
    }

    @Override
    public Map<String, Object> getFireResult(
            final String address,
            final List<PersonsAndMedicalRecordsDTO>
                    personsAndMedicalRecordsDTOList) {
        Map<String, Object> result = new HashMap<>();
        List<Firestations> firestationsList
                = firestationsI.getFirestationsAddress(address);
        List<Integer> stationNumber = new ArrayList<>();
        for (Firestations firestation : firestationsList) {
            stationNumber.add(firestation.getStation());
        }

        String residentString
                = personsAndMedicalRecordsDTOList
                .size() > 1 ? "Residents" : "Resident";
        String stationString
                = stationNumber.size() > 1 ? "Stations" : "Station";
        result.put(stationString, stationNumber);
        result.put(residentString, personsAndMedicalRecordsDTOList);
        return result;
    }
}
