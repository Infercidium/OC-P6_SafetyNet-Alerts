package com.infercidium.safetynet.rest;

import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.service.ChildAlertI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Contains URLs linked to ChildAlert.
 */
@RestController
public class ChildAlertRest {

    /**
     * Instantiation of LOGGER in order to inform in console.
     */
    private static final Logger LOGGER
            = LoggerFactory.getLogger(ChildAlertRest.class);

    /**
     * Instantiation of childAlertInterface.
     */
    private final ChildAlertI childAlertI;

    /**
     * Class constructor.
     * @param childAlertIn this is childAlertInterface.
     */
    public ChildAlertRest(final ChildAlertI childAlertIn) {
        this.childAlertI = childAlertIn;
    }

    /**
     * Provides the list of children living at the targeted address
     * or an empty list if no children live there.
     * @param address of the targeted residence.
     * @return Children List and 200 Ok if successful
     * or 404 not found if it does not exist.
     */
    @GetMapping(value = "/childAlert")
    public Map<String, List<PersonsAndMedicalRecordsDTO>> getChildAlert(
            @RequestParam final String address) {
        Map<String, List<PersonsAndMedicalRecordsDTO>> result
                = childAlertI.childAlert(address);
        LOGGER.info("List of children of address : " + address + ", found");
        return result;
    }
}
