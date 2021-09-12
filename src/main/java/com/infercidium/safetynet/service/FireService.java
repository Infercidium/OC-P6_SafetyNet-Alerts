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

/**
 * Fire Service develops public methods of interfaces, and private methods.
 */
@Service
public class FireService implements FireI {
    /**
     * Instantiation of personsInterface.
     */
    private final PersonsI personsI;

    /**
     * Instantiation of firestationsInterface.
     */
    private final FirestationsI firestationsI;

    /**
     * Instantiation of fireInterface.
     */
    private final FireAndFloodI fireI;

    /**
     * Class constructor.
     * @param personsIn this is personsInterface.
     * @param firestationsIn this is firestationsInterface.
     * @param fireIn this is fireInterface.
     */
    public FireService(final PersonsI personsIn,
                       final FirestationsI firestationsIn,
                       final FireAndFloodI fireIn) {
        this.personsI = personsIn;
        this.firestationsI = firestationsIn;
        this.fireI = fireIn;
    }

    /**
     * Allows you to obtain the list of Medicalrecords
     * of Persons living at the address.
     * @param address : String of address target.
     * @return list of Medicalrecords.
     */
    @Override
    public List<MedicalRecords> getFireMedicalRecords(final String address) {
        Set<Address> addressSet = new HashSet<>();
        addressSet.add(new Address(address));
        List<Persons> personsList
                = personsI.addressSetToPersonsList(addressSet);
        return fireI.personsListToMedicalRecordsList(personsList);
    }

    /**
     * The method of shaping Fire.
     * @param address : String of address target.
     * @param personsAndMedicalRecordsDTOList : residents List.
     * @return A Map containing the stations covering the address
     * and the residents living there.
     */
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
