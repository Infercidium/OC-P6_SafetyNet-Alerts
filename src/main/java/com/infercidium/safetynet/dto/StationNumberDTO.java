package com.infercidium.safetynet.dto;

import javax.validation.constraints.NotBlank;

public class StationNumberDTO {

    /**
     * FirstName attribute.
     */
    @NotBlank(message = "The first name cannot be null or empty.")
    private String firstName;

    /**
     * LastName attribute.
     */
    @NotBlank(message = "The last name cannot be null or empty.")
    private String lastName;

    /**
     * Address attribute.
     */
    @NotBlank(message = "Address cannot be null or empty.")
    private String address;

    /**
     * Phone attribute.
     */
    private String phone;

    /**
     * FirstName getter.
     * @return firstName attribute.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * FirstName setter.
     * @param firstNameS becomes the new firstName attribute.
     */
    public void setFirstName(final String firstNameS) {
        this.firstName = firstNameS;
    }

    /**
     * LastName getter.
     * @return lastName attribute.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * LastName setter.
     * @param lastNameS becomes the new lastName attribute.
     */
    public void setLastName(final String lastNameS) {
        this.lastName = lastNameS;
    }

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
     * Phone getter.
     * @return phone attribute.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Phone setter.
     * @param phoneS becomes the new phone attribute.
     */
    public void setPhone(final String phoneS) {
        this.phone = phoneS;
    }
}
