package com.infercidium.safetynet.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

/**
 * DTO use for sending server data to the Firestation user.
 */
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

    /**
     * Adapter manufacturer.
     * @param firestationsAddressDTO : user-supplied element
     *                               to adapt to the server format.
     */
    public FirestationsDTO(
            final FirestationsAddressDTO firestationsAddressDTO) {
        address = new HashSet<>();
        this.address.add(firestationsAddressDTO.getAddress());
        this.station = firestationsAddressDTO.getStation();
    }

    /**
     * Basic builder.
     */
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

    /**
     * Adds an address to the existing Set.
     * Information: No need for now
     * but potentially useful for future modification.
     * @param addressAdded : the address to add.
     */
    public void addAddress(final String addressAdded) {
        this.address.add(addressAdded);
    }
}
