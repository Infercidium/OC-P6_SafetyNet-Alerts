package com.infercidium.safetynet.service;

import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.model.Firestations;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Flood Service develops public methods of interfaces, and private methods.
 */
@Service
public class FloodService implements FloodI {

    /**
     * Instantiation of personsInterface.
     */
    private final PersonsI personsI;

    /**
     * Instantiation of firestationsInterface.
     */
    private final FirestationsI firestationsI;

    /**
     * Instantiation of FloodInterface.
     */
    private final FireAndFloodI floodI;

    /**
     * Class constructor.
     * @param personsIn this is personsInterface.
     * @param firestationsIn this is firestationsInterface.
     * @param floodIn this is floodInterface.
     */
    public FloodService(final PersonsI personsIn,
                        final FirestationsI firestationsIn,
                        final FireAndFloodI floodIn) {
        this.personsI = personsIn;
        this.firestationsI = firestationsIn;
        this.floodI = floodIn;
    }

    /**
     * Allows to retrieve all the residents of a station.
     * @param station to use.
     * @return The list of all residents linked to the station.
     */
    @Override
    public List<MedicalRecords> getFloodMedicalRecords(final int station) {
        Firestations firestation
                = firestationsI.getFirestationsStation(station);
        List<Persons> personsList
                = personsI.addressSetToPersonsList(firestation.getAddress());
        return floodI.personsListToMedicalRecordsList(personsList);
    }

    /**
     * Allows you to sort residents by address.
     * @param personsAndMedicalRecordsDTOList : The list of all residents
     *                                        linked to the station.
     * @return A Map of residents sorted by address.
     */
    @Override
    public Map<String, List<PersonsAndMedicalRecordsDTO>> getFloodResult(
            final List<PersonsAndMedicalRecordsDTO>
                    personsAndMedicalRecordsDTOList) {
        return personsAndMedicalRecordsDTOList
                .stream().collect(Collectors.groupingBy(
                        PersonsAndMedicalRecordsDTO::getAddress));
    }
}
