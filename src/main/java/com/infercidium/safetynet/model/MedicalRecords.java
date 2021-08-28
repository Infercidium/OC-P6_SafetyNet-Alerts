package com.infercidium.safetynet.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;


@Entity
public class MedicalRecords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthdate;

    @ManyToMany
    @JoinTable(name = "MedicalRecords_Medication",
    joinColumns = @JoinColumn(name = "MedicalRecords_id"),
    inverseJoinColumns = @JoinColumn(name = "Medications_id"))
    private Set<Medications> medications = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "MedicalRecords_Allergies",
            joinColumns = @JoinColumn(name = "MedicalRecords_id"),
            inverseJoinColumns = @JoinColumn(name = "Allergies_id"))
    private Set<Allergies> allergies = new HashSet<>();

    @OneToOne
    @JoinColumn(nullable = false)
    private Persons persons;

    public MedicalRecords() { }

    public MedicalRecords(final LocalDate birthdateC,
                          final Set<Medications> medicationsC,
                          final Set<Allergies> allergiesC,
                          final Persons personsC) {
        this.birthdate = birthdateC;
        this.medications = medicationsC;
        this.allergies = allergiesC;
        this.persons = personsC;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long idS) {
        this.id = idS;
    }

    public String getFirstName() {
        return persons.getFirstName();
    }

    public String getLastName() {
        return persons.getLastName();
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(final LocalDate birthdateS) {
        this.birthdate = birthdateS;
    }

    public int getAge() {
        return Period.between(birthdate, LocalDate.now()).getYears();
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

    public Persons getPersons() {
        return persons;
    }

    public void setPersons(final Persons personsS) {
        this.persons = personsS;
    }

    @Override
    public String toString() {
        return "MedicalRecords{"
                + " id = " + id
                + ", firstName = '" + persons.getFirstName() + '\''
                + ", lastName = '" + persons.getLastName() + '\''
                + ", birthdate = " + birthdate
                + ", medications = " + medications
                + ", allergies = " + allergies
                + ", person = " + persons.getId()
                + '}';
    }
}
