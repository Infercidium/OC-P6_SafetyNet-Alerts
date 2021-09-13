package com.infercidium.safetynet.rest;

import com.infercidium.safetynet.dto.PersonsDTO;
import com.infercidium.safetynet.service.PhoneAlertI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Contains URLs linked to PhoneAlert.
 */
@RestController
public class PhoneAlertRest {

    /**
     * Instantiation of LOGGER in order to inform in console.
     */
    private static final Logger LOGGER
            = LoggerFactory.getLogger(PhoneAlertRest.class);

    /**
     * Instantiation of phoneAlertInterface.
     */
    private final PhoneAlertI phoneAlertI;

    /**
     * Class constructor.
     * @param phoneAlertIn this is phoneAlertInterface.
     */
    public PhoneAlertRest(final PhoneAlertI phoneAlertIn) {
        this.phoneAlertI = phoneAlertIn;
    }

    /**
     * This url refers to the list of telephone numbers of residents
     * covered by the Firestation.
     * @param firestation : allows you to find the resource in the database.
     * @return a list and 200 Ok if successful
     * or 404 not found if it does not exist.
     */
    @GetMapping(value = "/phoneAlert")
    public List<PersonsDTO> getPhoneAlert(@RequestParam final int firestation) {
        List<PersonsDTO> result = phoneAlertI.getPersonsPhone(firestation);
        LOGGER.info("List of telephone numbers of residents "
                + "served by the station " + firestation + " found");
        return result;
    }
}
