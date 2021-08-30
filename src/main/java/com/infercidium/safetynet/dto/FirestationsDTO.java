package com.infercidium.safetynet.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class FirestationsDTO {

    /**
     * Address attribute.
     */
    @NotBlank(message = "Address cannot be null or empty.")
    private String address;

    /**
     * Station attribute.
     */
    @Min(value = 1, message = "The station cannot be null or empty.")
    private int station;

    /**
     * Address getter.
     * @return address attribute.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Address setter.
     * @param addressS becomes the new address attribute.
     */
    public void setAddress(final String addressS) {
        this.address = addressS;
    }

    /**
     * Station getter.
     * @return station attribute.
     */
    public int getStation() {
        return station;
    }

    /**
     * Station setter.
     * @param stationS becomes the new station attribute.
     */
    public void setStation(final int stationS) {
        this.station = stationS;
    }
}
