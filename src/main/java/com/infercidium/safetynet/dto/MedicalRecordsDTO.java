package com.infercidium.safetynet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.infercidium.safetynet.model.Allergies;
import com.infercidium.safetynet.model.Medications;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * DTO used for the exchange between the user
 * and the server for the data of MedicalRecords.
 */
public class MedicalRecordsDTO {

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
     * Birthdate attribute.
     */
    @NotNull(message = "Birthdate cannot be null.")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthdate;

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
     * Birthdate getter.
     * @return birthdate attribute.
     */
    public LocalDate getBirthdate() {
        return birthdate;
    }

    /**
     * Birthdate setter.
     * @param birthdateS becomes the new birthdate attribute.
     */
    public void setBirthdate(final LocalDate birthdateS) {
        this.birthdate = birthdateS;
    }

    /**
     * Medications Set getter.
     * @return medications Set attribute.
     */
    public Set<Medications> getMedications() {
        return new HashSet<>(medications);
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
        return new HashSet<>(allergies);
    }

    /**
     * Allergies Set setter.
     * @param allergiesS becomes the new allergies Set attribute.
     */
    public void setAllergies(final Set<Allergies> allergiesS) {
        this.allergies = allergiesS;
    }
}
