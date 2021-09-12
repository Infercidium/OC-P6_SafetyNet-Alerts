package com.infercidium.safetynet.rest;

import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.mapper.FireAndFloodMapper;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.service.FireI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Contains URLs linked to Fire.
 */
@RestController
public class FireRest {

    /**
     * Instantiation of LOGGER in order to inform in console.
     */
    private static final Logger LOGGER
            = LoggerFactory.getLogger(FireRest.class);

    /**
     * Instantiation of fireInterface.
     */
    private final FireI fireI;
    /**
     * Instantiation of fireMapper.
     */
    private final FireAndFloodMapper fireM;

    /**
     * Class constructor.
     * @param fireIn this is fireInterface.
     * @param fireMa this is fireMapper.
     */
    public FireRest(final FireI fireIn,
                    final FireAndFloodMapper fireMa) {
        this.fireI = fireIn;
        this.fireM = fireMa;
    }

    /**
     * This url asks for an address
     * and returns a map containing the station covering the address
     * and a list of people living at this address as well as their information.
     * @param address : allows you to find the resource in the database.
     * @return a map and 200 Ok if successful
     * or 404 not found if it does not exist.
     */
    @GetMapping(value = "/fire")
    public Map<String, Object> getFire(@RequestParam final String address) {
        List<MedicalRecords> medicalRecordsList
                = fireI.getFireMedicalRecords(address);
        List<PersonsAndMedicalRecordsDTO> personsAndMedicalRecordsDTOList
                = fireM.modelToDto(medicalRecordsList);
        Map<String, Object> result
                = fireI.getFireResult(address, personsAndMedicalRecordsDTOList);
        LOGGER.info("List of stations numbers and list of residents of "
                + address + " found");
        return result;
    }
}
