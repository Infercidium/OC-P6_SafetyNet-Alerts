package com.infercidium.safetynet.service;

import java.util.Map;

/**
 * StationNumber interface to store public methods
 * and use them in different services.
 */
public interface StationNumberI {
    /**
     * Formats the list of residents covered
     * by a station as well as the adult and child count.
     * @param station number.
     * @return the final Map expected by / firestation.
     */
    Map<String, Object> stationNumber(int station);
}
