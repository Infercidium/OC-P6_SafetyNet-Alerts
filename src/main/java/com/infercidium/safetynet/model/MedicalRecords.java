package com.infercidium.safetynet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
public class MedicalRecords {

    public MedicalRecords() { }

    public MedicalRecords(String firstName, String lastName, LocalDate birthdate, Set<Medications> medications, Set<Allergies> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.medications = medications;
        this.allergies = allergies;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String firstName;
    private String lastName;
    private LocalDate birthdate;

    @Column
    @ManyToMany
    @JoinTable(name = "Medicalrecords_Medications", joinColumns = {@JoinColumn(name="MedicalRecords_id")},
            inverseJoinColumns = @JoinColumn(name = "Medications_id"))
    private Set<Medications> medications = new HashSet<>();

    @Column
    @ManyToMany
    @JoinTable(name = "Medicalrecords_Allergies", joinColumns = {@JoinColumn(name="MedicalRecords_id")},
    inverseJoinColumns = @JoinColumn(name = "Allergies_id"))
    private Set<Allergies> allergies = new HashSet<>();

    public Long getId() { return id;}

    public void setId(Long id) { this.id = id;}

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Set<Medications> getMedications() { return medications; }

    public void setMedications(Set<Medications> medications) { this.medications = medications; }

    public Set<Allergies> getAllergies() { return allergies; }

    public void setAllergies(Set<Allergies> allergies) { this.allergies = allergies; }

    @Override
    public String toString() {
        return "MedicalRecords{"
                + "id='" + id + '\''
                + "firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + ", birthdate=" + birthdate + '\''
                + ", medications=" + medications + '\''
                + ", allergies=" + allergies + '\''
                + '}';
    }
}
