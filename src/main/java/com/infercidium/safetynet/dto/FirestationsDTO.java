package com.infercidium.safetynet.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class FirestationsDTO {

    @NotBlank(message = "Address cannot be null or empty.")
    private String address;

    @Min(value = 1, message = "The station cannot be null or empty.")
    private int station;

    public String getAddress() {
        return address;
    }

    public void setAddress(final String addressS) {
        this.address = addressS;
    }

    public int getStation() {
        return station;
    }

    public void setStation(final int stationS) {
        this.station = stationS;
    }
}
