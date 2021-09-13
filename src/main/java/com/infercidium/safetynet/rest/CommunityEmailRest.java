package com.infercidium.safetynet.rest;

import com.infercidium.safetynet.dto.PersonsDTO;
import com.infercidium.safetynet.service.CommunityEmailI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Contains URLs linked to CommunityEmail.
 */
@RestController
public class CommunityEmailRest {

    /**
     * Instantiation of LOGGER in order to inform in console.
     */
    private static final Logger LOGGER
            = LoggerFactory.getLogger(CommunityEmailRest.class);

    /**
     * Instantiation of communityEmailInterface.
     */
    private final CommunityEmailI communityEmailI;

    /**
     * Class constructor.
     * @param communityEmailIn this is communityEmailInterface.
     */
    public CommunityEmailRest(final CommunityEmailI communityEmailIn) {
        this.communityEmailI = communityEmailIn;
    }

    /**
     * This url allows you to have the list of emails
     * of the inhabitants of a city.
     * @param city : allows you to find the resource in the database.
     * @return List of personsDTO and 200 Ok if successful
     * or 404 not found if it does not exist.
     */
    @GetMapping(value = "/communityEmail")
    public List<PersonsDTO> getCommunityEmail(@RequestParam final String city) {
        List<PersonsDTO> personsDTO = communityEmailI.getPersonsEmail(city);
        LOGGER.info("List of emails from " + city + " residents found");
        return personsDTO;
    }
}
