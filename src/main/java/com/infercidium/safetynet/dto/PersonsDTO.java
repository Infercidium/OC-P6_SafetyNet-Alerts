package com.infercidium.safetynet.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PersonsDTO {

    @NotBlank(message = "The first name cannot be null or empty.")
    private String firstName;

    @NotBlank(message = "The last name cannot be null or empty.")
    private String lastName;

    @NotBlank(message = "Address cannot be null or empty.")
    private String address;

    @NotBlank(message = "The city cannot be null or empty.")
    private String city;

    @Min(value = 1, message = "The zip must be a positive number.")
    private int zip;

    private String phone;

    @Email(message = "A standard email address format is expected.")
    private String email;

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

    public String getCity() {
        return city;
    }

    public void setCity(final String cityS) {
        this.city = cityS;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(final int zipS) {
        this.zip = zipS;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phoneS) {
        this.phone = phoneS;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String emailS) {
        this.email = emailS;
    }
}
