package com.infercidium.safetynet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.infercidium.safetynet.model.Allergies;
import com.infercidium.safetynet.model.Medications;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class MedicalRecordsDTO {

    @NotBlank(message = "The first name cannot be null or empty.")
    private String firstName;

    @NotBlank(message = "The last name cannot be null or empty.")
    private String lastName;

    @NotNull(message = "Birthdate cannot be null.")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthdate;

    private Set<Medications> medications = new HashSet<>();

    private Set<Allergies> allergies = new HashSet<>();

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

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(final LocalDate birthdateS) {
        this.birthdate = birthdateS;
    }

    public Set<Medications> getMedications() {
        return medications;
    }

    public void setMedications(final Set<Medications> medicationsS) {
        this.medications = medicationsS;
    }

    public Set<Allergies> getAllergies() {
        return allergies;
    }

    public void setAllergies(final Set<Allergies> allergiesS) {
        this.allergies = allergiesS;
    }
}
