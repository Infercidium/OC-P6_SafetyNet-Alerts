package com.infercidium.safetynet.service;

import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.model.Firestations;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FloodService implements FloodI {
    private final PersonsI personsI;
    private final FirestationsI firestationsI;
    private final FireAndFloodI fireAndFloodI;

    public FloodService(final PersonsI personsIn,
                        final FirestationsI firestationsIn,
                        final FireAndFloodI fireAndFloodIn) {
        this.personsI = personsIn;
        this.firestationsI = firestationsIn;
        this.fireAndFloodI = fireAndFloodIn;
    }

    @Override
    public List<MedicalRecords> getFloodMedicalRecords(final int station) {
        Firestations firestation
                = firestationsI.getFirestationsStation(station);
        List<Persons> personsList
                = personsI.addressSetToPersonsList(firestation.getAddress());
        return fireAndFloodI.personsListToMedicalRecordsList(personsList);
    }

    @Override
    public Map<String, List<PersonsAndMedicalRecordsDTO>> getFloodResult(
            final List<PersonsAndMedicalRecordsDTO>
                    personsAndMedicalRecordsDTOList) {
        return personsAndMedicalRecordsDTOList
                .stream().collect(Collectors.groupingBy(
                        PersonsAndMedicalRecordsDTO::getAddress));
    }
}
