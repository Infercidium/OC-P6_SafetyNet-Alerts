package com.infercidium.safetynet.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.infercidium.safetynet.model.Allergies;
import com.infercidium.safetynet.model.Medications;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PersonsAndMedicalRecordsDTO {

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
     * Phone attribute.
     */
    private String phone;

    /**
     * Age attribute.
     */
    private int age;

    /**
     * Medications Set attribute.
     */
    private Set<Medications> medications = new HashSet<>();

    /**
     * Allergies Set attribute.
     */
    private Set<Allergies> allergies = new HashSet<>();

    @JsonIgnore
    private String address;

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
     * Age getter.
     * @return age attribute.
     */
    public int getAge() {
        return age;
    }

    /**
     * Age setter.
     * @param ageS becomes the new age attribute.
     */
    public void setAge(final int ageS) {
        this.age = ageS;
    }

    /**
     * Medications Set getter.
     * @return medications Set attribute.
     */
    public Set<Medications> getMedications() {
        return medications;
    }

    /**
     * Medications Set setter.
     * @param medicationsS becomes the new medications Set attribute.
     */
    public void setMedications(final Set<Medications> medicationsS) {
        this.medications = medicationsS;
    }

    /**
     * Allergies Set getter.
     * @return allergies Set attribute.
     */
    public Set<Allergies> getAllergies() {
        return allergies;
    }

    /**
     * Allergies Set setter.
     * @param allergiesS becomes the new allergies Set attribute.
     */
    public void setAllergies(final Set<Allergies> allergiesS) {
        this.allergies = allergiesS;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String addressS) {
        this.address = addressS;
    }
}
