package com.infercidium.safetynet.dto;

import com.infercidium.safetynet.model.Allergies;
import com.infercidium.safetynet.model.Medications;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

public class PersonInfoDTO {
    @NotBlank(message = "The first name cannot be null or empty.")
    private String firstName;

    @NotBlank(message = "The last name cannot be null or empty.")
    private String lastName;

    @NotBlank(message = "Address cannot be null or empty.")
    private String address;

    private int age;

    @Email(message = "A standard email address format is expected.")
    private String email;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(final String addressS) {
        this.address = addressS;
    }

    public int getAge() {
        return age;
    }

    public void setAge(final int ageS) {
        this.age = ageS;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String emailS) {
        this.email = emailS;
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
