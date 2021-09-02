package com.infercidium.safetynet.dto;

import com.infercidium.safetynet.model.Allergies;
import com.infercidium.safetynet.model.Medications;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

public class PersonInfoDTO {

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
     * Age attribute.
     */
    private int age;

    /**
     * Email attribute.
     */
    @Email(message = "A standard email address format is expected.")
    private String email;

    /**
     * Medications Set attribute.
     */
    private Set<Medications> medications = new HashSet<>();

    /**
     * Allergies Set attribute.
     */
    private Set<Allergies> allergies = new HashSet<>();

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
     * FirstName getter.
     * @return firstName attribute.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * LastName setter.
     * @param lastNameS becomes the new lastNName attribute.
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
}
