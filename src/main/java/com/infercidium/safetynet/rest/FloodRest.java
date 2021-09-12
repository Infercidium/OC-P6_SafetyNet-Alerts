package com.infercidium.safetynet.rest;

import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.mapper.FireAndFloodMapper;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.service.FloodI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class FloodRest {
    private static final Logger LOGGER
            = LoggerFactory.getLogger(FloodRest.class);

    private final FloodI floodI;
    private final FireAndFloodMapper floodM;

    public FloodRest(final FloodI floodIn, final FireAndFloodMapper floodMa) {
        this.floodI = floodIn;
        this.floodM = floodMa;
    }

    /**
     * This url reviews a list of inhabitants
     * by address covered by the Firestation.
     * @param station : allows you to find the resource in the database.
     * @return a map and 200 Ok if successful
     * or 404 not found if it does not exist.
     */
    @GetMapping(value = "/flood/stations")
    public Map<String, List<PersonsAndMedicalRecordsDTO>> getFlood(
            @RequestParam final int station) {
        List<MedicalRecords> medicalRecordsList
                = floodI.getFloodMedicalRecords(station);
        List<PersonsAndMedicalRecordsDTO> personsAndMedicalRecordsDTOList
                = floodM.modelToDto(medicalRecordsList);
        Map<String, List<PersonsAndMedicalRecordsDTO>> result
                = floodI.getFloodResult(personsAndMedicalRecordsDTOList);
        LOGGER.info("List of inhabitants by address "
                + "served by station " + station + " found");
        return result;
    }
}
