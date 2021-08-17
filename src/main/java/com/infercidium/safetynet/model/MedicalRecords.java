package com.infercidium.safetynet.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;


@Entity
@JsonFilter("MedicalRecordFilter")
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"firstName", "lastName"})})
public class MedicalRecords {

    public MedicalRecords() { }

    public MedicalRecords(final String firstNameC, final String lastNameC,
                          final LocalDate birthdateC,
                          final Set<Medications> medicationsC,
                          final Set<Allergies> allergiesC) {
        this.firstName = firstNameC;
        this.lastName = lastNameC;
        this.birthdate = birthdateC;
        this.medications = medicationsC;
        this.allergies = allergiesC;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "firstName")
    @NotBlank(message = "The first name cannot be null or empty.")
    private String firstName;

    @Column(name = "lastName")
    @NotBlank(message = "The last name cannot be null or empty.")
    private String lastName;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthdate;

    @ManyToMany
    @JoinTable(name = "Medicalrecords_Medications",
            joinColumns = @JoinColumn(name = "MedicalRecords_id"),
            inverseJoinColumns = @JoinColumn(name = "Medications_id"))
    private Set<Medications> medications = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "Medicalrecords_Allergies",
            joinColumns = @JoinColumn(name = "MedicalRecords_id"),
            inverseJoinColumns = @JoinColumn(name = "Allergies_id"))
    private Set<Allergies> allergies = new HashSet<>();

    @OneToOne
    @JoinColumn(nullable = false)
    private Persons persons;

    public Long getId() {
        return id;
    }

    public void setId(final Long idS) {
        this.id = idS;
    }

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
                + "id =" + id + '\''
                + ", firstName ='" + firstName + '\''
                + ", lastName ='" + lastName + '\''
                + ", birthdate =" + birthdate + '\''
                + ", medications =" + medications + '\''
                + ", allergies =" + allergies + '\''
                + ", person =" + persons.getId() + '\''
                + '}';
    }
}
