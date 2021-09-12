package com.infercidium.safetynet.rest;

import com.infercidium.safetynet.service.StationNumberI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Contains URLs linked to StationNumber.
 */
@RestController
public class StationNumberRest {

    /**
     * Instantiation of LOGGER in order to inform in console.
     */
    private static final Logger LOGGER
            = LoggerFactory.getLogger(StationNumberRest.class);

    /**
     * Instantiation of stationNumberInterface.
     */
    private final StationNumberI stationNumberI;

    /**
     * Class constructor.
     * @param stationNumberIn this is stationNumberInterface.
     */
    public StationNumberRest(final StationNumberI stationNumberIn) {
        this.stationNumberI = stationNumberIn;
    }

    /**
     * Find the list of residents
     * whose address is covered by Firestation.
     * @param stationNumber of the Firestation.
     * @return residents and 200 Ok if successful
     * or 404 not found if it does not exist.
     */
    @GetMapping(value = "/firestation")
    public Map<String, Object> getStationNumber(
            @RequestParam final int stationNumber) {
        Map<String, Object> result
                = stationNumberI.stationNumber(stationNumber);
        LOGGER.info("List of inhabitants covered by station "
                + stationNumber + " found");
        return result;
    }
}
