package com.infercidium.safetynet.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;


@Entity
@JsonIgnoreProperties(value = "name")
public class MedicalRecords {

    public MedicalRecords() { this.name = new Name(); }

    public MedicalRecords(final String firstname, final String lastname,
                          final LocalDate birthdateC,
                          final Set<Medications> medicationsSet,
                          final Set<Allergies> allergiesSet) {
        this.name = new Name(firstname, lastname);
        this.birthdate = birthdateC;
        this.medications = medicationsSet;
        this.allergies = allergiesSet;
    }

    @EmbeddedId
    @Column(unique = true)
    private Name name;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthdate;

    @ManyToMany
    @JoinTable(name = "Medicalrecords_Medications",
            joinColumns = {@JoinColumn(name = "MedicalRecords_firstName"),
                            @JoinColumn(name = "MedicalRecords_lastName")},
            inverseJoinColumns = @JoinColumn(name = "Medications_id"))
    private Set<Medications> medications = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "Medicalrecords_Allergies",
            joinColumns = {@JoinColumn(name = "MedicalRecords_firstName"),
                            @JoinColumn(name = "MedicalRecords_lastName")},
            inverseJoinColumns = @JoinColumn(name = "Allergies_id"))
    private Set<Allergies> allergies = new HashSet<>();

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getFirstName() {
        return name.getFirstName();
    }

    public void setFirstName(final String firstname) {
        this.name.setFirstName(firstname);
    }

    public String getLastName() {
        return name.getLastName();
    }

    public void setLastName(final String lastname) {
        this.name.setLastName(lastname);
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(final LocalDate birthdateC) {
        this.birthdate = birthdateC;
    }

    public int getAge() {return Period.between(birthdate, LocalDate.now()).getYears();}

    public Set<Medications> getMedications() {
        return medications;
    }

    public void setMedications(final Set<Medications> medicationsSet) {
        this.medications = medicationsSet;
    }

    public Set<Allergies> getAllergies() {
        return allergies;
    }

    public void setAllergies(final Set<Allergies> allergiesSet) {
        this.allergies = allergiesSet;
    }

    @Override
    public String toString() {
        return "MedicalRecords{"
                + "firstName='" + name.getFirstName() + '\''
                + ", lastName='" + name.getLastName() + '\''
                + ", birthdate=" + birthdate + '\''
                + ", medications=" + medications + '\''
                + ", allergies=" + allergies + '\''
                + '}';
    }
}
