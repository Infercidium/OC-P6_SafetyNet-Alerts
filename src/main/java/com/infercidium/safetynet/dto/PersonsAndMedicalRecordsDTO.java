package com.infercidium.safetynet.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.infercidium.safetynet.model.Allergies;
import com.infercidium.safetynet.model.Medications;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PersonsAndMedicalRecordsDTO {

    @NotBlank(message = "The first name cannot be null or empty.")
    private String firstName;

    @NotBlank(message = "The last name cannot be null or empty.")
    private String lastName;

    private String phone;

    private int age;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phoneS) {
        this.phone = phoneS;
    }

    public int getAge() {
        return age;
    }

    public void setAge(final int ageS) {
        this.age = ageS;
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
