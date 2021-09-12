package com.infercidium.safetynet.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * DTO used for the exchange between the user
 * and the server for the data of Persons.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PersonsDTO {

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
     * City attribute.
     */
    @NotBlank(message = "The city cannot be null or empty.")
    private String city;

    /**
     * Zip attribute.
     */
    @Min(value = 1, message = "The zip must be a positive number.")
    private int zip;

    /**
     * Phone attribute.
     */
    private String phone;

    /**
     * Email attribute.
     */
    @Email(message = "A standard email address format is expected.")
    private String email;

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
     * City getter.
     * @return city attribute.
     */
    public String getCity() {
        return city;
    }

    /**
     * City setter.
     * @param cityS becomes the new city attribute.
     */
    public void setCity(final String cityS) {
        this.city = cityS;
    }

    /**
     * Zip getter.
     * @return zip attribute.
     */
    public int getZip() {
        return zip;
    }

    /**
     * Zip setter.
     * @param zipS becomes the new zip attribute.
     */
    public void setZip(final int zipS) {
        this.zip = zipS;
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

    /**
     * Email getter.
     * @return email attribute.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Email setter.
     * @param emailS becomes the new email attribute.
     */
    public void setEmail(final String emailS) {
        this.email = emailS;
    }
}
