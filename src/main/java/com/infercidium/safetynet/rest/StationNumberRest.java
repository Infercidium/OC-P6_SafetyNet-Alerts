package com.infercidium.safetynet.rest;

import com.infercidium.safetynet.service.StationNumberI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StationNumberRest {
    private static final Logger LOGGER
            = LoggerFactory.getLogger(StationNumberRest.class);

    private final StationNumberI stationNumberI;

    public StationNumberRest(final StationNumberI stationNumberIn) {
        this.stationNumberI = stationNumberIn;
    }

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
