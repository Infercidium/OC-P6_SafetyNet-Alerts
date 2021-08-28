package com.infercidium.safetynet.dto;

import javax.validation.constraints.NotBlank;

public class StationNumberDTO {
    @NotBlank(message = "The first name cannot be null or empty.")
    private String firstName;

    @NotBlank(message = "The last name cannot be null or empty.")
    private String lastName;

    @NotBlank(message = "Address cannot be null or empty.")
    private String address;

    private String phone;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstNameS) {
        this.firstName = firstNameS;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastNameS) {
        this.lastName = lastNameS;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String addressS) {
        this.address = addressS;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phoneS) {
        this.phone = phoneS;
    }
}
