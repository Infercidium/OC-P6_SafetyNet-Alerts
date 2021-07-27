package com.infercidium.safetynet.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class MedicalRecords {

    private String firstName;
    private String lastName;
    private LocalDateTime birthdate;
    private Map<String, Integer> medications;
    private List<String> allergies;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDateTime birthdate) {
        this.birthdate = birthdate;
    }

    public Map<String, Integer> getMedications() {
        return medications;
    }

    public void setMedications(Map<String, Integer> medications) {
        this.medications = medications;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    @Override
    public String toString() {
        return "MedicalRecords{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthdate=" + birthdate + '\'' +
                ", medications=" + medications + '\'' +
                ", allergies=" + allergies + '\'' +
                '}';
    }
}
