package com.infercidium.safetynet.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

public class FirestationsDTO {

    /**
     * Address attribute.
     */
    @NotBlank(message = "Address cannot be null or empty.")
    private Set<String> address;

    /**
     * Station attribute.
     */
    @Min(value = 1, message = "The station cannot be null or empty.")
    private int station;

    public FirestationsDTO(FirestationsAddressDTO firestationsAddressDTO) {
        address = new HashSet<>();
        this.address.add(firestationsAddressDTO.getAddress());
        this.station = firestationsAddressDTO.getStation();
    }

    public FirestationsDTO() { }

    /**
     * Address getter.
     * @return address attribute.
     */
    public Set<String> getAddress() {
        return address;
    }

    /**
     * Address setter.
     * @param addressS becomes the new address attribute.
     */
    public void setAddress(final Set<String> addressS) {
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

    public void addAddress(final String address) {
        if (!this.address.contains(address)) {
            this.address.add(address);
        }
    }
}
